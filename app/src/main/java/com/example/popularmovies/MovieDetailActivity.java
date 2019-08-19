package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.Utils.Consts;
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
                mTitleTextView.setText(getIntent().getStringExtra(Consts.TITLE_EXTRA_KEY));
                mReleaseDateTextView.setText(setDate(getIntent().getStringExtra(Consts.RELEASE_DATE_EXTRA_KEY)));
                mVoteAverageTextView.setText(String.format("%s/10", String.valueOf(getIntent().getDoubleExtra(Consts.VOTE_AVERAGE_EXTRA_KEY, 0.0))));
                mOverviewTextView.setText(getIntent().getStringExtra(Consts.OVERVIEW_EXTRA_KEY));
                Picasso.get().load(getIntent().getStringExtra(Consts.POSTER_PATH_EXTRA_KEY))
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
        return getIntent().hasExtra(Consts.TITLE_EXTRA_KEY) &&
                getIntent().hasExtra(Consts.POSTER_PATH_EXTRA_KEY) &&
                getIntent().hasExtra(Consts.OVERVIEW_EXTRA_KEY) &&
                getIntent().hasExtra(Consts.VOTE_AVERAGE_EXTRA_KEY) &&
                getIntent().hasExtra(Consts.RELEASE_DATE_EXTRA_KEY);
    }

    private void findViews() {
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mVoteAverageTextView = (TextView) findViewById(R.id.tv_rating);
        mPosterImageView = (ImageView) findViewById(R.id.iv_detail_poster);
        mOverviewTextView = (TextView) findViewById(R.id.tv_overview);
    }
}
