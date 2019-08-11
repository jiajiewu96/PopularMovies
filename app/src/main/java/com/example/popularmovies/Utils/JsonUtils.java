package com.example.popularmovies.Utils;

import android.text.TextUtils;

import com.example.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_ARRAY_RESULTS = "results";
    private static final String JSON_KEY_POSTER = "poster_path";

    public static List<Movie> getMovies(String json){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        try {
            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");
            List<Movie> movies = new ArrayList<>();
            for(int i =0; i< results.length(); i++){
                JSONObject currentMovie = results.getJSONObject(i);
                String posterPath = currentMovie.getString("poster_path");
                movies.add(new Movie());
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
