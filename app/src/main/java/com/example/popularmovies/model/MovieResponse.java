package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    public List<Movie> mMovies;

    public MovieResponse(){
        mMovies = new ArrayList<>();
    }
}
