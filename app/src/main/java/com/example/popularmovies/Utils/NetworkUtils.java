package com.example.popularmovies.Utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/";

    private static final String BASE_IMAGE_URL =
            "http://image.tmdb.org/t/p/";

    private static String POPULAR_PARAM = "popular";
    private static String TOP_RATED_PARAM = "top_rated";
    private static String API_KEY_PARAM = "api_key";

    public static URL buildMovieUrl(String sortParam){
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(POPULAR_PARAM)
                .appendQueryParameter(API_KEY_PARAM, Consts.API_KEY)
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
}
