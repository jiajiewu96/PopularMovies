package com.example.popularmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;
import com.example.popularmovies.model.TrailerResponse;
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
        return NetworkUtils.loadMovieData(sortParams);
    }

    public Call<TrailerResponse> getTrailersForId(String movieID){
        return NetworkUtils.loadTrailerData(movieID);
    }

    public LiveData<List<Movie>> getFavoritesFromDB(){
        return mDatabase.favoritesDao().loadAllFavorites();
    }

    public void addFavoriteToFavoriteDatabase(Movie movie){
        mDatabase.favoritesDao().insertFavorite(movie);
    }

    public void deleteFavoriteFromFavoriteDatabase(Movie movie){
        mDatabase.favoritesDao().deleteFavorite(movie);
    }

    public boolean checkIfMovieInDatabase(Movie movie){
        return mDatabase.favoritesDao().checkForMovie(movie.getId()) != null;
    }

}
