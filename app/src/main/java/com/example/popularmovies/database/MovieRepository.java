package com.example.popularmovies.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;

public class MovieRepository {

    private static final Object LOCK = new Object();
    private static MovieRepository sInstance;
    private boolean mInitialized = false;

    private MovieRepository(){

    }

    public synchronized static MovieRepository getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new MovieRepository();
            }
        }
        return sInstance;
    }

    public Call<MovieResponse> getMoviesFromAPI(String sortParams){
        return NetworkUtils.loadMovieData(sortParams);
    }

    public static FavoritesDatabase getFavoritesDatabaseInstance(Context context){
        return FavoritesDatabase.getInstance(context);
    }

}
