package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    int page;

    @SerializedName("total_results")
    int totalResults;

    @SerializedName("total_pages")
    int total_pages;

    @SerializedName("results")
    private List<Movie> mMovies;

    public MovieResponse(){
        mMovies = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return mMovies;
    }
}
