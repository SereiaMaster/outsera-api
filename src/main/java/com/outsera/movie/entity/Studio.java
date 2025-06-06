package com.outsera.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "studio")
public class Studio extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long studioId;

    String name;

    @ManyToMany(mappedBy = "studios")
    List<Movie> movies = new ArrayList<>();

}
