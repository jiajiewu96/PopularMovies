package com.example.popularmovies.workmanagers;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.popularmovies.BaseApp;
import com.example.popularmovies.R;
import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;
import com.example.popularmovies.utils.Consts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIWork extends Worker {

    private static final String TAG = APIWork.class.getSimpleName();
    private final Context mContext;

    public APIWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Application application = (Application) mContext.getApplicationContext();
        final MovieRepository movieRepository = ((BaseApp) application).getRepository();
        Log.d(TAG, movieRepository.toString());
        Call<MovieResponse> responseCall = movieRepository.getMoviesFromAPI(getInputData().getString(Consts.WORK_PARAM_KEY));
        movieRepository.deleteAll();
        responseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    Result.failure();
                    return;
                }
                Log.d(TAG, "Successful Call");
                ArrayList<Movie> movies;
                if (response.body() != null) {
                    movies = (ArrayList<Movie>) response.body().getMovies();
                    for(Movie movie: movies){
                        movieRepository.addFavoriteToFavoriteDatabase(movie);
                    }
                    Log.d(TAG, "Movies Successfully retrieved");

                } else {
                    closeOnError(mContext.getString(R.string.io_error));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "Unsuccessful Call");
            }
        });
        return Result.success();
    }

    private Result closeOnError(String message) {
        return Result.failure();
    }


}
