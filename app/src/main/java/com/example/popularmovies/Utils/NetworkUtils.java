package com.example.popularmovies.Utils;

import com.example.popularmovies.RetroFitInterfaces.MovieDBService;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_MOVIE_URL =
            "https://api.themoviedb.org/3/movie/";

    private static final String BASE_IMAGE_URL =
            "https://image.tmdb.org/t/p/";

    private static final String IMAGE_SIZE = "w185";
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final String REQUEST_METHOD = "GET";
    private static final String API_KEY_PARAM = "api_key";

    private static Retrofit buildRetrofitUrl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }



    public static Call<MovieResponse> loadMovieData(String sortParam){
        Retrofit retrofit = buildRetrofitUrl();
        MovieDBService movieDBService = retrofit.create(MovieDBService.class);
        return movieDBService.getMovieResponse(sortParam, Consts.API_KEY);
    }


//    public static URL buildMovieUrl(String sortParam) {
//        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
//                .appendPath(sortParam)
//                .appendQueryParameter(API_KEY_PARAM, Consts.API_KEY)
//                .build();
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }

    static String buildImageUrlString(String imagePath) {
        return BASE_IMAGE_URL + IMAGE_SIZE +
                imagePath;
    }

//    public static String getResponseFromUrl(URL url) throws IOException {
//        String jsonResponse = null;
//        if (url == null) {
//            return jsonResponse;
//        }
//        HttpsURLConnection urlConnection = null;
//        InputStream in = null;
//        try {
//            urlConnection = (HttpsURLConnection) url.openConnection();
//            urlConnection.setReadTimeout(READ_TIMEOUT);
//            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
//            urlConnection.setRequestMethod(REQUEST_METHOD);
//            urlConnection.connect();
//            Log.d(TAG, "URL RESPONSE CODE: " + urlConnection.getResponseCode());
//            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                in = urlConnection.getInputStream();
//                jsonResponse = readFromStream(in);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d(TAG, "Unable to connect" + e.toString());
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (in != null) {
//                in.close();
//            }
//        }
//        return jsonResponse;
//    }
//
//    private static String readFromStream(InputStream inputStream) throws IOException {
//        StringBuilder output = new StringBuilder();
//        if (inputStream != null) {
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
//            BufferedReader reader = new BufferedReader(inputStreamReader);
//            String line = reader.readLine();
//            while (line != null) {
//                output.append(line);
//                line = reader.readLine();
//            }
//        }
//        return output.toString();
//    }
}
