package com.example.popularmovies;

import android.app.Application;
import android.content.Context;

import com.example.popularmovies.database.FavoritesDatabase;
import com.example.popularmovies.database.MovieRepository;

import java.lang.ref.WeakReference;


public class BaseApp extends Application {

    private static WeakReference<Context> mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<Context>(this);
    }

    public FavoritesDatabase getDatabase(){
        return FavoritesDatabase.getInstance(this);
    }

    public MovieRepository getRepository(){
        return MovieRepository.getInstance(getDatabase());
    }

    public static Context getAppContext(){
        return mContext.get();
    }
}
