package com.example.popularmovies.Utils;

import android.text.TextUtils;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

//    private static final String JSON_ARRAY_RESULTS = "results";
//
//    private static final String JSON_KEY_TITLE = "title";
//    private static final String JSON_KEY_RELEASE_DATE = "release_date";
//    private static final String JSON_KEY_POSTER = "poster_path";
//    private static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
//    private static final String JSON_KEY_OVERVIEW = "overview";
//
//    private static final String FALL_BACK_STRING = "N/A";
//
//    private static final int FALL_BACK_INT = 0;

    public static List<Movie> getMovies(String json){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        Gson gson = new GsonBuilder().create();
        MovieResponse movieResponse = gson.fromJson(json, MovieResponse.class);
        return movieResponse.mMovies;
    }
}
