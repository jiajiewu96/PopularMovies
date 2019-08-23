package com.example.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static FavoritesDatabase sInstance;
    private static final String DATABASE_NAME = "favorites_database";

    public static FavoritesDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoritesDatabase.class, FavoritesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FavoritesDao favoritesDao();

}
