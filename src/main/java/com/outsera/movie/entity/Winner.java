package com.outsera.movie.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "winner")
public class Winner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long winnerId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    Movie movie;

    @ManyToOne
    @JoinColumn(name = "producer_id", nullable = false)
    MovieProducer producers;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    Long winYear;

}
