package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.popularmovies.utils.Consts;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "favorites")
public class Movie implements Parcelable {
    @PrimaryKey
    private int id;

    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String rawPosterPath;

    @SerializedName("vote_average")
    private float voteAverage;

    private String overview;


    private boolean favorited;

    @Ignore
    public Movie() {

    }

    public Movie(int id, String title, String releaseDate, String rawPosterPath, float voteAverage, String overview){
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rawPosterPath = rawPosterPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    @Ignore
    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        releaseDate = in.readString();
        rawPosterPath = in.readString();
        voteAverage = in.readFloat();
        overview = in.readString();
        favorited = in.readInt() == 1;
    }

    @Ignore
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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRawPosterPath(){
        return rawPosterPath;
    }
    @Ignore
    public String getPosterPath() {
        return buildImageUrlString(rawPosterPath);
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }


    public void setFavorited(boolean favorited){
        this.favorited = favorited;
    }


    public boolean isFavorited() {
        return favorited;
    }
    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(rawPosterPath);
        parcel.writeFloat(voteAverage);
        parcel.writeString(overview);
        parcel.writeInt(favorited? 1 : 0);
    }

    @Ignore
    private static String buildImageUrlString(String imagePath) {
        return Consts.BASE_IMAGE_URL + Consts.IMAGE_SIZE +
                imagePath;
    }


}
