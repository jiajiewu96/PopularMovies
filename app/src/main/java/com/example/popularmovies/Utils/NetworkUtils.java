package com.example.popularmovies.Utils;

import com.example.popularmovies.RetroFitInterfaces.MovieDBService;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_MOVIE_URL =
            "https://api.themoviedb.org/3/movie/";


    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final String REQUEST_METHOD = "GET";
    private static final String API_KEY_PARAM = "api_key";

    private static Retrofit buildRetrofitUrl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }



    public static Call<MovieResponse> loadMovieData(String sortParam){
        Retrofit retrofit = buildRetrofitUrl();
        MovieDBService movieDBService = retrofit.create(MovieDBService.class);
        return movieDBService.getMovieResponse(sortParam, Consts.API_KEY);
    }

}
