package com.example.popularmovies.RetroFitInterfaces;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {
    @GET("{search_param}")
    Call<MovieResponse> getMovieResponse(@Path("search_param") String searchParam, @Query("api_key") String apiKey);
}
