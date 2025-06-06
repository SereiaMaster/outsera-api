package com.outsera.movie.repository;

import com.outsera.movie.data.SummaryProducerDTO;
import com.outsera.movie.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinnerRepository extends JpaRepository<Winner, Long> {

    @Query(value = """
        SELECT
            w1.producer_id AS movieProducerId,
            (w2.win_year - w1.win_year) AS intervalYears,
            w1.win_year AS previousWin,
            w2.win_year AS followingWin
        FROM winner w1
        JOIN winner w2 ON w1.producer_id = w2.producer_id AND w2.win_year > w1.win_year
        ORDER BY intervalYears
        """, nativeQuery = true)
    List<SummaryProducerDTO> findSummaryProducerIntervals();

}
