package com.example.popularmovies.model;

public class Movie {
    private String title;
    private String releaseDate;
    private String posterPath;
    private int voteAverage;
    private String overview;

    public Movie() {

    }

    public Movie(String title, String releaseDate, String posterPath, int voteAverage, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }
}
