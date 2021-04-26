package com.example.popularmovies.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<Movie>> loadAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Movie movie);

    @Delete
    void deleteFavorite(Movie movie);

    @Query("DELETE FROM favorites")
    void deleteAll();

    @Query("SELECT * FROM favorites WHERE id = :queryId")
    Movie checkForMovie(int queryId);
}
