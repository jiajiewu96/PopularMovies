package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.database.FavoritesViewModel;
import com.example.popularmovies.utils.Consts;
import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);



        findViews();
        if (getIntent() != null) {
            if (checkForIntentExtras()) {
                mMovie = getIntent().getParcelableExtra(Consts.MOVIE_EXTRA_KEY);
                setTitle(mMovie.getTitle());
                mTitleTextView.setText(mMovie.getTitle());
                mReleaseDateTextView.setText(setDate(mMovie.getReleaseDate()));
                mVoteAverageTextView.setText(String.format("%s/10", String.valueOf(mMovie.getVoteAverage()) ));
                mOverviewTextView.setText(mMovie.getOverview());
                Picasso.get().load(mMovie.getPosterPath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(mPosterImageView);
                if (checkForFavorited()) {
                    setFavoritedImage();
                } else {
                    setUnfavoritedImage();
                }
            }
        }

        FavoritesViewModel.Factory factory = new FavoritesViewModel.Factory(
                this.getApplication());

        mFavoritesViewModel = ViewModelProviders.of(this, factory)
                .get(FavoritesViewModel.class);


    }

    private void setUnfavoritedImage() {
        mFavoritedImageView.setImageResource(R.drawable.ic_favorite_unselected);
    }

    private void setFavoritedImage() {
        mFavoritedImageView.setImageResource(R.drawable.ic_favorite_selected);
    }

    private boolean checkForFavorited() {
        return mMovie.getFavorited() == 1;
    }

    public void saveToFavorites(View view){
        if(mMovie.getFavorited() != Consts.FAVORITED_VALUE_TRUE) {
            mMovie.setFavorited(Consts.FAVORITED_VALUE_TRUE);
            setFavoritedImage();
            mFavoritesViewModel.addFavorite(mMovie.getId());
        } else{
            mMovie.setFavorited(Consts.FAVORITED_VALUE_FALSE);
            setUnfavoritedImage();
            mFavoritesViewModel.removeFavorite(mMovie.getId());
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

    private void findViews() {
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mVoteAverageTextView = (TextView) findViewById(R.id.tv_rating);
        mPosterImageView = (ImageView) findViewById(R.id.iv_detail_poster);
        mOverviewTextView = (TextView) findViewById(R.id.tv_overview);
        mFavoritedImageView = (ImageView) findViewById(R.id.iv_favorite_button);
    }
}
