package com.outsera.movie.service;

import com.outsera.movie.entity.*;
import com.outsera.movie.enums.StatusEnum;
import com.outsera.movie.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ImportService {

    private static final Logger log = LoggerFactory.getLogger(ImportService.class);
    public static final String COLUMN_CHAR = ";";
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;
    private final StudioRepository studioRepository;
    private final MovieProducerRepository movieProducerRepository;
    private final WinnerRepository winnerRepository;

    private static final String REGEX_SPLIT_NAMES = "(?i)\\s*,\\s*|\\s+and\\s+|and(?=\\s+[A-Z])";

    public ImportService(
            CategoryRepository categoryRepository,
            MovieRepository movieRepository,
            MovieProducerRepository movieProducerRepository,
            WinnerRepository winnerRepository,
            StudioRepository studioRepository
    ){
        this.categoryRepository = categoryRepository;
        this.movieProducerRepository = movieProducerRepository;
        this.movieRepository = movieRepository;
        this.winnerRepository = winnerRepository;
        this.studioRepository = studioRepository;
    };

    public void importMovieCsvFromResource(String resourcePath) throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            Category category = createCategory(); // cria categoria da premiação, no caso Golden Raspberry Awards

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // pula o cabeçalho
                    continue;
                }

                String[] fields = line.split(COLUMN_CHAR, 5);
                if (fields.length > 0) {
                    Movie movie = processMovies(fields);
                    processWinners(fields, movie, category);
                }
            }
        }
    }

    private Category createCategory() {
        Category category = new Category();
        category.setName("Golden Raspberry Awards");
        category.setStatus(StatusEnum.ACTIVE);
        return categoryRepository.save(category);
    }

    private Movie processMovies(String[] fields) {
        Movie movie = new Movie();

        movie.setName(fields[1].trim());
        movie.setReleaseYear(Long.valueOf(fields[0]));
        movie.setStatus(StatusEnum.ACTIVE);

        processStudios(fields, movie);
        processProducers(fields, movie);

        return movieRepository.save(movie);

    }

    private void processStudios(String[] fields, Movie movie) {
        if(Objects.nonNull(fields[2])){

            String[] studios = fields[2].split(REGEX_SPLIT_NAMES);

            List<Studio> persisted = new ArrayList<>();
            List<Studio> notPersisted = new ArrayList<>();

            Set<String> studiosNames = Arrays.stream(studios).map(String::trim).filter(s -> !s.isBlank()).collect(Collectors.toSet());
            Map<String, Studio> studiosMap = searchStudiosbyNames(studiosNames);

            studiosNames.forEach(s -> {

                if(studiosMap.containsKey(s)){
                    Studio p = studiosMap.get(s);
                    persisted.add(p);
                } else {
                    Studio newStudio = new Studio();
                    newStudio.setName(s.trim());
                    newStudio.setStatus(StatusEnum.ACTIVE);
                    notPersisted.add(newStudio);
                }
            });

            studioRepository.saveAll(notPersisted);
            movie.getStudios().addAll(persisted);
            movie.getStudios().addAll(notPersisted);

        }
    }

    private void processProducers(String[] fields, Movie movie) {
        if(Objects.nonNull(fields[3])){

            String[] producers = fields[3].split(REGEX_SPLIT_NAMES);

            List<MovieProducer> persisted = new ArrayList<>();
            List<MovieProducer> notPersisted = new ArrayList<>();

            Set<String> producerNames = Arrays.stream(producers).map(String::trim).filter(s -> !s.isBlank()).collect(Collectors.toSet());
            Map<String, MovieProducer> producerMaps = searchProducersbyNames(producerNames);

            producerNames.forEach(s -> {

                if(producerMaps.containsKey(s)){
                    MovieProducer p = producerMaps.get(s);
                    persisted.add(p);
                } else {
                    MovieProducer newProducer = new MovieProducer();
                    newProducer.setName(s.trim());
                    newProducer.setStatus(StatusEnum.ACTIVE);
                    notPersisted.add(newProducer);
                }
            });

            movieProducerRepository.saveAll(notPersisted);
            movie.getProducers().addAll(persisted);
            movie.getProducers().addAll(notPersisted);

        }
    }

    private void processWinners(String[] fields, Movie movie, Category category) {
        if(Objects.nonNull(fields[4]) && ("yes").equals(fields[4])){

            List<Winner> winners = movie.getProducers().stream().map(movieProducer -> {

                Winner winner = new Winner();
                winner.setCategory(category);
                winner.setMovie(movie);
                winner.setWinYear(movie.getReleaseYear());
                winner.setProducers(movieProducer);
                winner.setStatus(StatusEnum.ACTIVE);

                return winner;
            }).toList();

            winnerRepository.saveAll(winners);
        }
    }

    private Map<String, Studio> searchStudiosbyNames(Set<String> nomes) {
        return studioRepository.findAllByNameIn(nomes).stream()
                .collect(Collectors.toMap(Studio::getName, Function.identity()));
    }

    private Map<String, MovieProducer> searchProducersbyNames(Set<String> nomes) {
        return movieProducerRepository.findAllByNameIn(nomes).stream()
                .collect(Collectors.toMap(MovieProducer::getName, Function.identity()));
    }

}
