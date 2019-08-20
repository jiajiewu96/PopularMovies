package com.example.popularmovies.database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.BaseApp;
import com.example.popularmovies.model.Movie;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    private static MovieRepository mMovieRepository;

    public FavoritesViewModel(@NonNull Application application, MovieRepository movieRepository) {

        mMovieRepository = movieRepository;
    }

    public void addFavorite(int id){
        mMovieRepository.addFavoriteToFavoriteDatabase(id);
    }

    public void removeFavorite(int id){
        mMovieRepository.deleteFavoriteFromFavoriteDatabase(id);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application mApplication;

        private final MovieRepository mRepository;

        public Factory(@NonNull Application application){
            mApplication = application;
            mRepository = ((BaseApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavoritesViewModel(mApplication, mRepository);
        }
    }
}
