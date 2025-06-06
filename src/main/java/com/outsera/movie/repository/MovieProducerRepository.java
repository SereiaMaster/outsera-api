package com.outsera.movie.repository;

import com.outsera.movie.entity.MovieProducer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieProducerRepository extends JpaRepository<MovieProducer, Long> {

    List<MovieProducer> findAllByNameIn(Iterable<String> names);

}
