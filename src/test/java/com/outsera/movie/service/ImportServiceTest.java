package com.outsera.movie.service;


import com.outsera.movie.entity.*;
import com.outsera.movie.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ImportServiceTest {

    @Autowired
    private ImportService importService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private MovieProducerRepository producerRepository;

    @Autowired
    private WinnerRepository winnerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        winnerRepository.deleteAll();
        studioRepository.deleteAll();
        producerRepository.deleteAll();
        movieRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void testImportCsv_createsEntitiesCorrectly() throws IOException {

        importService.importMovieCsvFromResource("Movielist.csv");

        List<Movie> movies = movieRepository.findAll();
        List<Studio> studios = studioRepository.findAll();
        List<MovieProducer> producers = producerRepository.findAll();
        List<Winner> winners = winnerRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        assertThat(categories)
                .hasSize(1)
                .extracting(Category::getName)
                .contains("Golden Raspberry Awards");

        assertThat(movies).isNotEmpty();
        assertThat(studios).isNotEmpty();
        assertThat(producers).isNotEmpty();
        assertThat(winners).isNotEmpty();

        Movie movie = movies.get(0);
        assertThat(movie.getStudios()).isNotEmpty();
        assertThat(movie.getProducers()).isNotEmpty();
        assertThat(winners.get(0).getMovie()).isEqualTo(movie);
        assertThat(winners.get(0).getCategory().getName()).isEqualTo("Golden Raspberry Awards");
    }

}
