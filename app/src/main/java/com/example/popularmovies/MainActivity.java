package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.Utils.JsonUtils;
import com.example.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
            List<Movie> imagePaths;
            try {
                response = NetworkUtils.getResponseFromUrl(fetchMovieUrl);
                imagePaths = JsonUtils.getMovies(response);
                return imagePaths;
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
                return null;

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
