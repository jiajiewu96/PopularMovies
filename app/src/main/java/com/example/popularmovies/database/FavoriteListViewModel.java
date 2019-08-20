package com.example.popularmovies.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.BaseApp;
import com.example.popularmovies.model.Movie;

import java.util.List;

public class FavoriteListViewModel extends AndroidViewModel {
    private static MovieRepository mMovieRepository;

    private static LiveData<List<Movie>> mFavorites;

    public FavoriteListViewModel(@NonNull Application application) {
        super(application);
        mMovieRepository = ((BaseApp) application).getRepository();
        mFavorites = mMovieRepository.getFavoritesFromDB();
    }

    public LiveData<List<Movie>> getFavorites(){
        return mFavorites;
    }


}
