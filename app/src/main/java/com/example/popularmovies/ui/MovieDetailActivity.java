package com.example.popularmovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.AppExecutors;
import com.example.popularmovies.BaseApp;
import com.example.popularmovies.R;
import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.database.viewModels.FavoritesViewModel;
import com.example.popularmovies.model.ReviewResponse;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.model.TrailerResponse;
import com.example.popularmovies.ui.adapters.ReviewAdapter;
import com.example.popularmovies.ui.adapters.TrailerAdapter;
import com.example.popularmovies.utils.Consts;
import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerClickHandler {

    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mVoteAverageTextView;
    private ImageView mPosterImageView;
    private TextView mOverviewTextView;
    private ImageView mFavoritedImageView;

    private static final String INPUT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String OUTPUT_DATE_PATTERN = "MMMM dd, yyyy";

    private Movie mMovie;
    private FavoritesViewModel mFavoritesViewModel;

    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mTrailerRecycler;
    private TextView mNoTrailersTextView;

    private ReviewAdapter mReviewAdapter;
    private RecyclerView mReviewRecycler;
    private TextView mNoReviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        findViews();

        FavoritesViewModel.Factory factory = new FavoritesViewModel.Factory(
                this.getApplication());

        mFavoritesViewModel = ViewModelProviders.of(this, factory)
                .get(FavoritesViewModel.class);

        if (getIntent() != null) {
            if (checkForIntentExtras()) {
                mMovie = getIntent().getParcelableExtra(Consts.MOVIE_EXTRA_KEY);

                setMovieDetails();
                checkForMovieInFavoriteDB();

                setupTrailerRecycler();
                loadTrailersFromAPI();

                setupReviewRecycler();
                loadReviewsFromAPI();
            }
        }
    }



    private void findViews() {
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mVoteAverageTextView = (TextView) findViewById(R.id.tv_rating);
        mPosterImageView = (ImageView) findViewById(R.id.iv_detail_poster);
        mOverviewTextView = (TextView) findViewById(R.id.tv_overview);
        mFavoritedImageView = (ImageView) findViewById(R.id.iv_favorite_button);

        mNoTrailersTextView = (TextView) findViewById(R.id.tv_no_trailers);
        mTrailerRecycler = (RecyclerView) findViewById(R.id.recyclerview_trailer);

        mNoReviewTextView = (TextView) findViewById(R.id.tv_no_review);
        mReviewRecycler = (RecyclerView) findViewById(R.id.recyclerview_review);
    }

    private void setupTrailerRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mTrailerRecycler.setLayoutManager(linearLayoutManager);

        mTrailerAdapter = new TrailerAdapter(this);
        mTrailerRecycler.setAdapter(mTrailerAdapter);
    }

    private void loadTrailersFromAPI() {
        showTrailers();
        MovieRepository movieRepository = ((BaseApp) getApplication()).getRepository();
        Call<TrailerResponse> responseCall = movieRepository.getTrailersForId(Integer.toString(mMovie.getId()));

        responseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if(!response.isSuccessful()){
                    hideTrailers();
                    return;
                }
                List<Trailer> trailers = null;
                if (response.body() != null) {
                    trailers = response.body().getResults();
                    mTrailerAdapter.setTrailerData(trailers);
                }

            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                hideTrailers();
            }
        });

    }

    private void setupReviewRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mReviewRecycler.setLayoutManager(linearLayoutManager);

        mReviewAdapter = new ReviewAdapter();
        mReviewRecycler.setAdapter(mReviewAdapter);
    }

    private void loadReviewsFromAPI() {
        showReviews();
        MovieRepository movieRepository = ((BaseApp) getApplication()).getRepository();
        Call<ReviewResponse> responseCall = movieRepository.getReviewForId(Integer.toString(mMovie.getId()));

        responseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(!response.isSuccessful()){
                    hideReviews();
                    return;
                }

                if (response.body() != null) {
                    mReviewAdapter.setReviews(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                hideReviews();
            }
        });
    }


    private void setMovieDetails() {
        setTitle(mMovie.getTitle());
        mTitleTextView.setText(mMovie.getTitle());
        mReleaseDateTextView.setText(setDate(mMovie.getReleaseDate()));
        mVoteAverageTextView.setText(String.format("%s/10", String.valueOf(mMovie.getVoteAverage())));
        mOverviewTextView.setText(mMovie.getOverview());

        Picasso.get().load(mMovie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(mPosterImageView);
    }

    private void showTrailers() {
        mNoTrailersTextView.setVisibility(View.INVISIBLE);
        mTrailerRecycler.setVisibility(View.VISIBLE);
    }

    private void hideTrailers() {
        mNoTrailersTextView.setVisibility(View.INVISIBLE);
        mTrailerRecycler.setVisibility(View.VISIBLE);
    }

    private void showReviews(){
        mNoReviewTextView.setVisibility(View.INVISIBLE);
        mReviewRecycler.setVisibility(View.VISIBLE);
    }

    private void hideReviews(){
        mReviewRecycler.setVisibility(View.INVISIBLE);
        mNoReviewTextView.setVisibility(View.VISIBLE);
    }

    private void checkForMovieInFavoriteDB() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mFavoritesViewModel.checkMovie(mMovie)) {

                    mMovie.setFavorited(Consts.FAVORITED_VALUE_TRUE);
                    setFavoriteSelectedImage();
                } else {

                    mMovie.setFavorited(Consts.FAVORITED_VALUE_FALSE);
                    setFavoriteUnselectedImage();
                }
            }
        });

    }

    public void favoriteOnClick(View view) {
        if (mMovie.getFavorited() != Consts.FAVORITED_VALUE_TRUE) {
            mMovie.setFavorited(Consts.FAVORITED_VALUE_TRUE);
            setFavoriteSelectedImage();
            AppExecutors.getInstance().diskIO().execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            mFavoritesViewModel.addFavorite(mMovie);
                        }
                    }
            );
        } else {
            mMovie.setFavorited(Consts.FAVORITED_VALUE_FALSE);
            setFavoriteUnselectedImage();
            AppExecutors.getInstance().diskIO().execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            mFavoritesViewModel.removeFavorite(mMovie);
                        }
                    }
            );
        }
    }

    private String setDate(@NonNull String unformattedString) {
        String releaseDate = "";

        try {
            SimpleDateFormat unformattedSdf = new SimpleDateFormat(INPUT_DATE_PATTERN, Locale.ENGLISH);
            Date date = unformattedSdf.parse(unformattedString);

            SimpleDateFormat formattedDate = new SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.getDefault());
            releaseDate = formattedDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return releaseDate;
    }

    private boolean checkForIntentExtras() {
        return getIntent().hasExtra(Consts.MOVIE_EXTRA_KEY);
    }

    private void setFavoriteUnselectedImage() {
        mFavoritedImageView.setImageResource(R.drawable.ic_favorite_unselected);
    }

    private void setFavoriteSelectedImage() {
        mFavoritedImageView.setImageResource(R.drawable.ic_favorite_selected);
    }

    @Override
    public void onClick(String trailerId) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube" + trailerId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v="+trailerId));
        try{
            startActivity(appIntent);
        }catch (ActivityNotFoundException e){
            startActivity(webIntent);
        }
    }
}
