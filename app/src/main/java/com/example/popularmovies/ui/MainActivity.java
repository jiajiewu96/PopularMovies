package com.example.popularmovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.popularmovies.BaseApp;
import com.example.popularmovies.ui.adapters.MoviePosterAdapter;
import com.example.popularmovies.R;
import com.example.popularmovies.database.viewModels.FavoriteListViewModel;
import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.utils.Consts;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterClickerHandler, AdapterView.OnItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Spinner mSpinner;

    private MoviePosterAdapter mMoviePosterAdapter;

    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private static String mSortString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);

        mMoviePosterAdapter = new MoviePosterAdapter(this);
        mRecyclerView.setAdapter(mMoviePosterAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.sort_array, R.layout.movie_spinner_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
        //default sort param

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(Consts.SPINNER_KEY)){
                Log.d(TAG, "onCreate: setting spinner selection" + savedInstanceState.getInt(Consts.SPINNER_KEY));
                mSpinner.setSelection(savedInstanceState.getInt(Consts.SPINNER_KEY, 0));
            }
        }else{
            mSortString = Consts.POPULAR_PARAM;
            loadMoviesFromApi();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(Consts.SPINNER_KEY)){
                Log.d(TAG, "onRestoreInstanceState: setting spinner selection" + savedInstanceState.getInt(Consts.SPINNER_KEY));
                mSpinner.setSelection(savedInstanceState.getInt(Consts.SPINNER_KEY));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: Writing to save instance state for spinner");
        outState.putInt(Consts.SPINNER_KEY, mSpinner.getSelectedItemPosition());
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Consts.MOVIE_EXTRA_KEY, movie);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int itemPos = adapterView.getSelectedItemPosition();
        switch (itemPos){
            case 0:
                mSortString = Consts.POPULAR_PARAM;
                loadMoviesFromApi();
                break;
            case 1:
                mSortString = Consts.TOP_RATED_PARAM;
                loadMoviesFromApi();
                break;
            case 2:
                loadFavoritesFromDB();
                break;
            default:
                mSortString = Consts.POPULAR_PARAM;
                loadMoviesFromApi();
                break;
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void findViews() {
        mSpinner = (Spinner) findViewById(R.id.sort_spinner);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_posters);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    private void loadMoviesFromApi() {
        showLoading();
        Application application = getApplication();
        MovieRepository movieRepository = ((BaseApp) application).getRepository();
        Call<MovieResponse> responseCall = movieRepository.getMoviesFromAPI(mSortString);

        responseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(!response.isSuccessful()){
                    closeOnError(response.message());
                    return;
                }
                List<Movie> movies = response.body().getMovies();
                mMoviePosterAdapter.setMoviePosterStrings(movies);
                showMoviePosters();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                closeOnError(t.toString());
            }
        });
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showMoviePosters() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void loadFavoritesFromDB() {
        FavoriteListViewModel viewModel = ViewModelProviders.of(this).get(FavoriteListViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> favorites) {
                mMoviePosterAdapter.setMoviePosterStrings(favorites);
            }
        });
    }

    private void closeOnError(String errorMessage) {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(errorMessage);
    }
}
