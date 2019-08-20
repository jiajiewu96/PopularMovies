package com.example.popularmovies;

import android.app.Application;

import com.example.popularmovies.database.FavoritesDatabase;
import com.example.popularmovies.database.MovieRepository;


public class BaseApp extends Application {

    public FavoritesDatabase getDatabase(){
        return FavoritesDatabase.getInstance(this);
    }

    public MovieRepository getRepository(){
        return MovieRepository.getInstance(getDatabase());
    }
}
