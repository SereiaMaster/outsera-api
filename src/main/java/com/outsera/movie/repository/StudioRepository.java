package com.outsera.movie.repository;

import com.outsera.movie.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    List<Studio> findAllByNameIn(Iterable<String> names);

}
