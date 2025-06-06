package com.outsera.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "movie_producer")
public class MovieProducer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long movieProducerId;

    String name;

    @ManyToMany(mappedBy = "producers")
    List<Movie> movies = new ArrayList<>();

}
