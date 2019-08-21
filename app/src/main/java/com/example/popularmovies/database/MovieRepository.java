package com.example.popularmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;

public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();

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
        Log.d(TAG, "REPOSITORY.API_RETRIEVAL: getMoviesFromAPI");
        return NetworkUtils.loadMovieData(sortParams);
    }

    public LiveData<List<Movie>> getFavoritesFromDB(){
        Log.d(TAG, "REPOSITORY.DATABASE_RETRIEVAL: getFavoritesFromDB");
        return mDatabase.favoritesDao().loadAllFavorites();
    }

    public void addFavoriteToFavoriteDatabase(Movie movie){
        Log.d(TAG, "REPOSITORY.DATABASE_INSERT: addFavoriteToFavoriteDatabase");
        mDatabase.favoritesDao().insertFavorite(movie);
    }

    public void deleteFavoriteFromFavoriteDatabase(Movie movie){
        Log.d(TAG, "REPOSITORY.DATABASE_DELETE: deleteFavoriteFromFavoriteDatabase");
        mDatabase.favoritesDao().deleteFavorite(movie);
    }

    public boolean checkIfMovieInDatabase(Movie movie){
        return mDatabase.favoritesDao().checkForMovie(movie.getId()) != null;
    }

}
