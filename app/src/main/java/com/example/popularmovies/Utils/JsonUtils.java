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

    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_RELEASE_DATE = "release_date";
    private static final String JSON_KEY_POSTER = "poster_path";
    private static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
    private static final String JSON_KEY_OVERVIEW = "overview";

    private static final String FALL_BACK_STRING = "N/A";

    private static final int FALL_BACK_INT = 0;

    public static List<Movie> getMovies(String json){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        try {
            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray(JSON_ARRAY_RESULTS);
            List<Movie> movies = new ArrayList<>();
            for(int i =0; i< results.length(); i++){
                JSONObject currentMovie = results.getJSONObject(i);
                String title = currentMovie.optString(JSON_KEY_TITLE, FALL_BACK_STRING);
                String date = currentMovie.optString(JSON_KEY_RELEASE_DATE, FALL_BACK_STRING);
                int rating = currentMovie.optInt(JSON_KEY_VOTE_AVERAGE, FALL_BACK_INT);
                String posterPath = NetworkUtils.buildImageUrlString(currentMovie.optString(JSON_KEY_POSTER, FALL_BACK_STRING));
                String overview = currentMovie.optString(JSON_KEY_OVERVIEW, FALL_BACK_STRING);

                movies.add(new Movie(title, date, posterPath, rating, overview));
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
