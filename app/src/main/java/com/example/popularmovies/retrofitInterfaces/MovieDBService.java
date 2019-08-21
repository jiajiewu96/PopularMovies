package com.example.popularmovies.retrofitInterfaces;

import com.example.popularmovies.model.CommentResponse;
import com.example.popularmovies.model.MovieResponse;
import com.example.popularmovies.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {
    @GET("{search_param}")
    Call<MovieResponse> getMovieResponse(@Path("search_param") String searchParam, @Query("api_key") String apiKey);
    @GET("{id}/videos")
    Call<TrailerResponse> getTrailerResponse(@Path("id")String movieId, @Query("api_key") String apiKey);
    @GET("{id}/reviews")
    Call<CommentResponse> getCommentResponse(@Path("id")String movieId, @Query("api_key") String apiKey);
}
