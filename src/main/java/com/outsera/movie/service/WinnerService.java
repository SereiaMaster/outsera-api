package com.outsera.movie.service;

import com.outsera.movie.data.SummaryProducerDTO;
import com.outsera.movie.data.WinnerIntervalDTO;
import com.outsera.movie.data.WinnerWrapperDTO;
import com.outsera.movie.entity.MovieProducer;
import com.outsera.movie.repository.MovieProducerRepository;
import com.outsera.movie.repository.WinnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WinnerService {

    private final WinnerRepository winnerRepository;
    private final MovieProducerRepository movieProducerRepository;

    public WinnerService(
            WinnerRepository winnerRepository,
            MovieProducerRepository movieProducerRepository
    ){
        this.winnerRepository = winnerRepository;
        this.movieProducerRepository = movieProducerRepository;
    }

    public WinnerWrapperDTO findMinMaxIntervalWinners(){

        WinnerWrapperDTO out = new WinnerWrapperDTO();

        List<SummaryProducerDTO> summaryMinProducerIntervals = winnerRepository.findMinSummaryProducerIntervals();
        List<SummaryProducerDTO> summaryMaxProducerIntervals = winnerRepository.findMaxSummaryProducerIntervals();

        out.setMax(summaryMaxProducerIntervals.stream().map( bigger -> {
                Optional<MovieProducer> biggerProducer = movieProducerRepository.findById(bigger.getMovieProducerId());
                return WinnerIntervalDTO.builder()
                        .interval(bigger.getInterval())
                        .followingWin(bigger.getFollowingWin())
                        .previousWin(bigger.getPreviousWin())
                        .producer(biggerProducer.get().getName())
                        .build();
                }).toList()
        );

        out.setMin(summaryMinProducerIntervals.stream().map( smaller -> {
                Optional<MovieProducer> smallProducer = movieProducerRepository.findById(smaller.getMovieProducerId());
                    return WinnerIntervalDTO.builder()
                            .interval(smaller.getInterval())
                            .followingWin(smaller.getFollowingWin())
                            .previousWin(smaller.getPreviousWin())
                            .producer(smallProducer.get().getName())
                            .build();
                }).toList()
        );

        return out;
    }

    public List<WinnerIntervalDTO> findMaxIntervalWinners(){

        List<SummaryProducerDTO> summaryMaxProducerIntervals = winnerRepository.findMaxSummaryProducerIntervals();

        return summaryMaxProducerIntervals.stream().map( bigger -> {
            Optional<MovieProducer> biggerProducer = movieProducerRepository.findById(bigger.getMovieProducerId());
            return WinnerIntervalDTO.builder()
                    .interval(bigger.getInterval())
                    .followingWin(bigger.getFollowingWin())
                    .previousWin(bigger.getPreviousWin())
                    .producer(biggerProducer.get().getName())
                    .build();
        }).toList();
    }

    public List<WinnerIntervalDTO> findMinIntervalWinners(){

        List<SummaryProducerDTO> summaryMinProducerIntervals = winnerRepository.findMinSummaryProducerIntervals();

        return summaryMinProducerIntervals.stream().map( smaller -> {
            Optional<MovieProducer> smallProducer = movieProducerRepository.findById(smaller.getMovieProducerId());
            return WinnerIntervalDTO.builder()
                    .interval(smaller.getInterval())
                    .followingWin(smaller.getFollowingWin())
                    .previousWin(smaller.getPreviousWin())
                    .producer(smallProducer.get().getName())
                    .build();
        }).toList();
    }

}
