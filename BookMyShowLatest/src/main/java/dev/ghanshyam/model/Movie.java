package dev.ghanshyam.model;

import lombok.Getter;

@Getter
public class Movie {
    String movieName;

    public Movie(String movieName) {
        this.movieName = movieName;
    }
}
