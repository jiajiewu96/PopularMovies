package com.example.popularmovies.utils;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.model.TrailerResponse;
import com.example.popularmovies.retrofitInterfaces.MovieDBService;
import com.example.popularmovies.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static final String BASE_MOVIE_URL =
            "https://api.themoviedb.org/3/movie/";

    private static Retrofit buildRetrofitUrl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Call<MovieResponse> loadMovieData(String sortParam) {
        Retrofit retrofit = buildRetrofitUrl();
        MovieDBService movieDBService = retrofit.create(MovieDBService.class);
        return movieDBService.getMovieResponse(sortParam, BuildConfig.POPULAR_MOVIES_API_KEY);
    }

    public static Call<TrailerResponse> loadTrailerData(String id) {
        Retrofit retrofit = buildRetrofitUrl();
        MovieDBService movieDBService = retrofit.create(MovieDBService.class);

        return movieDBService.getTrailerResponse(id, BuildConfig.POPULAR_MOVIES_API_KEY);
    }

}
