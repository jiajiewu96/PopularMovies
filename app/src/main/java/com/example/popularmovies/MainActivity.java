package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.Utils.Consts;
import com.example.popularmovies.Utils.JsonUtils;
import com.example.popularmovies.Utils.NetworkUtils;
import com.example.popularmovies.model.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterClickerHandler{
    //public key that can be accessed from multiple activities


    private MoviePosterAdapter mMoviePosterAdapter;

    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private static final int GRID_SPAN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_posters);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager = new GridLayoutManager(this, GRID_SPAN);
        mRecyclerView.setLayoutManager(layoutManager);

        mMoviePosterAdapter = new MoviePosterAdapter(this, this);
        mRecyclerView.setAdapter(mMoviePosterAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        showMoviePosters();


        new FetchMovieTask().execute();
    }

    private void showMoviePosters() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String title, String releaseDate, String posterPath, int voteAverage, String overview) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Consts.TITLE_EXTRA_KEY, title);
        intent.putExtra(Consts.RELEASE_DATE_EXTRA_KEY, releaseDate);
        intent.putExtra(Consts.POSTER_PATH_EXTRA_KEY, posterPath);
        intent.putExtra(Consts.VOTE_AVERAGE_EXTRA_KEY, voteAverage);
        intent.putExtra(Consts.OVERVIEW_EXTRA_KEY, overview);
        startActivity(intent);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {

            String search = strings[0];
            URL fetchMovieUrl = NetworkUtils.buildMovieUrl(search);
            String response;
            List<Movie> movies;
            try {
                response = NetworkUtils.getResponseFromUrl(fetchMovieUrl);
                movies = JsonUtils.getMovies(response);
                return movies;
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(movies != null){
                showMoviePosters();
                mMoviePosterAdapter.setMoviePosterStrings(movies);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            closeOnError();
        }
    }

    private void closeOnError() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(R.string.io_error);
    }
}
