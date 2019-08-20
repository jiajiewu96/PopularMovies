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
    private final FavoritesDatabase mDatabase;


    private MovieRepository(final FavoritesDatabase movieDatabase){
        mDatabase = movieDatabase;
    }

    public synchronized static MovieRepository getInstance(FavoritesDatabase database){
        if(sInstance == null){
            synchronized (LOCK){
                if(sInstance == null) {
                    sInstance = new MovieRepository(database);
                }
            }
        }
        return sInstance;
    }

    public Call<MovieResponse> getMoviesFromAPI(String sortParams){
        return NetworkUtils.loadMovieData(sortParams);
    }

    public LiveData<List<Movie>> getFavoritesFromDB(){
        return mDatabase.favoritesDao().loadAllFavorites();
    }

    public void addFavoriteToFavoriteDatabase(int id){
        mDatabase.favoritesDao().insertFavorite(id);
    }

    public void deleteFavoriteFromFavoriteDatabase(int id){
        mDatabase.favoritesDao().deleteFavorite(id);
    }

}
