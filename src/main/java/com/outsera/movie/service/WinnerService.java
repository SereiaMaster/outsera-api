package com.outsera.movie.service;

import com.outsera.movie.data.SummaryProducerDTO;
import com.outsera.movie.data.WinnerIntervalDTO;
import com.outsera.movie.data.WinnerWrapper;
import com.outsera.movie.entity.MovieProducer;
import com.outsera.movie.repository.MovieProducerRepository;
import com.outsera.movie.repository.WinnerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public WinnerWrapper findMinMaxIntervalWinners(){

        WinnerWrapper out = new WinnerWrapper();

        List<SummaryProducerDTO> summaryProducerIntervals = winnerRepository.findSummaryProducerIntervals();

        Optional<SummaryProducerDTO> smallInterval = summaryProducerIntervals.stream().min(Comparator.comparingLong(SummaryProducerDTO::getInterval));
        Optional<SummaryProducerDTO> biggerInterval = summaryProducerIntervals.stream().max(Comparator.comparingLong(SummaryProducerDTO::getInterval));

        if(biggerInterval.isPresent()) {

            SummaryProducerDTO bigger = biggerInterval.get();
            SummaryProducerDTO smaller = smallInterval.get();

            Optional<MovieProducer> smallProducer = movieProducerRepository.findById(smaller.getMovieProducerId());
            Optional<MovieProducer> biggerProducer = movieProducerRepository.findById(bigger.getMovieProducerId());

            out.setMax(List.of(
                WinnerIntervalDTO.builder()
                        .interval(bigger.getInterval())
                        .followingWin(bigger.getFollowingWin())
                        .previousWin(bigger.getPreviousWin())
                        .producer(biggerProducer.get().getName())
                        .build()
            ));

            out.setMin(List.of(
                    WinnerIntervalDTO.builder()
                            .interval(smaller.getInterval())
                            .followingWin(smaller.getFollowingWin())
                            .previousWin(smaller.getPreviousWin())
                            .producer(smallProducer.get().getName())
                            .build()
            ));
        }


        return out;
    }

}
