package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.Utils.Consts;
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

    private static final String INPUT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String OUTPUT_DATE_PATTERN = "MMMM dd, yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        findViews();
        if (getIntent() != null) {
            if (checkForIntentExtras()) {
                Movie movie = getIntent().getParcelableExtra(Consts.MOVIE_EXTRA_KEY);

                mTitleTextView.setText(movie.getTitle());
                mReleaseDateTextView.setText(setDate(movie.getReleaseDate()));
                mVoteAverageTextView.setText(String.format("%s/10", String.valueOf(movie.getVoteAverage()) ));
                mOverviewTextView.setText(movie.getOverview());
                Picasso.get().load(movie.getPosterPath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(mPosterImageView);
            }
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
    }
}
