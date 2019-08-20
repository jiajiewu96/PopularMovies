package com.example.popularmovies.database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.model.Movie;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private static MovieRepository mMovieRepository;

    private static LiveData<List<Movie>> mFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        mMovieRepository = MovieRepository.getInstance();
        mFavorites = mMovieRepository.getFavoritesFromDB(this.getApplication());
    }

    public static LiveData<List<Movie>> getFavorites(){
        return mFavorites;
    }

    public void addFavorite(Movie movie){
        mMovieRepository.addFavoriteToFavoriteDatabase(movie, this.getApplication());
    }

    public void removeFavorite(Movie movie){
        mMovieRepository.deleteFavoriteFromFavoriteDatabase(movie, this.getApplication());
    }
}
