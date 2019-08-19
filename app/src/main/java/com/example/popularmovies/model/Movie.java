package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.popularmovies.Utils.Consts;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String rawPosterPath;

    @SerializedName("vote_average")
    private float voteAverage;

    private String overview;

    public Movie() {

    }

    public Movie(String title, String releaseDate, String posterPath, float voteAverage, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.rawPosterPath = posterPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        rawPosterPath = in.readString();
        voteAverage = in.readFloat();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return buildImageUrlString(rawPosterPath);
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(rawPosterPath);
        parcel.writeFloat(voteAverage);
        parcel.writeString(overview);
    }

    private static String buildImageUrlString(String imagePath) {
        return Consts.BASE_IMAGE_URL + Consts.IMAGE_SIZE +
                imagePath;
    }
}
