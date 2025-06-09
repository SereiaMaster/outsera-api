package com.outsera.movie.repository;

import com.outsera.movie.data.SummaryProducerDTO;
import com.outsera.movie.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinnerRepository extends JpaRepository<Winner, Long> {

    // A logica aqui é numerar as vitorias de cada produtor, para posteriormente conseguir vincular a anterior com a posterior
    String PRODUCERS_RANKED_BY_WINS = """
            SELECT
                producer_id,
                win_year,
                ROW_NUMBER() OVER (PARTITION BY producer_id ORDER BY win_year) AS rn
              FROM winner
            """;

    // Aqui de fato é feito isso, vinculando o rank + 1 com rank, ou seja  1 = 2, 2 = 3 e assim por diante.
    String CONSECUTIVE_WINS_BY_PRODUCER = """
            SELECT 
                w1.producer_id, 
                w2.win_year - w1.win_year AS intervalYears,
                w1.win_year AS previousWin, 
                w2.win_year AS followingWin
            FROM ranked w1
                JOIN ranked w2 ON w1.producer_id = w2.producer_id AND w1.rn + 1 = w2.rn
            """;

    // Depois disso fica facil, aqui é para pegar o MAX desses rankeamentos
    @Query(value = """
            WITH ranked AS (
            """ + PRODUCERS_RANKED_BY_WINS + """
            ),
            consecutive AS (
            """ + CONSECUTIVE_WINS_BY_PRODUCER + """
            ),
            max_interval AS (
                SELECT MAX(intervalYears) AS max_val FROM consecutive
            )
            SELECT * FROM consecutive
            WHERE intervalYears = (SELECT max_val FROM max_interval)
        """, nativeQuery = true)
    List<SummaryProducerDTO> findMaxSummaryProducerIntervals();

    // E aqui é para pegar o MIN desses rankeamentos
    @Query(value = """
            WITH ranked AS (
            """ + PRODUCERS_RANKED_BY_WINS + """
            ),
            consecutive AS (
            """ + CONSECUTIVE_WINS_BY_PRODUCER + """
            ),
            min_interval AS (
              SELECT MIN(intervalYears) AS min_val FROM consecutive
            )
            SELECT * FROM consecutive
            WHERE intervalYears = (SELECT min_val FROM min_interval)
        """, nativeQuery = true)
    List<SummaryProducerDTO> findMinSummaryProducerIntervals();

}
