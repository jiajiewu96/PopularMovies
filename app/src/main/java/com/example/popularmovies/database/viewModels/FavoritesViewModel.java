package com.example.popularmovies.database.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.BaseApp;
import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.model.Movie;

public class FavoritesViewModel extends ViewModel {

    private static MovieRepository mMovieRepository;

    public FavoritesViewModel(@NonNull Application application, MovieRepository movieRepository) {

        mMovieRepository = movieRepository;
    }

    public void addFavorite(Movie movie){
        mMovieRepository.addFavoriteToFavoriteDatabase(movie);
    }

    public void removeFavorite(Movie movie){
        mMovieRepository.deleteFavoriteFromFavoriteDatabase(movie);
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
