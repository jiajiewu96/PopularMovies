package com.example.popularmovies.model;

public class Movie {
    private String title;
    private String releaseDate;
    private String posterPath;
    private int voteAverage;
    private String plotSynopses;

    public Movie() {

    }

    public Movie(String title, String releaseDate, String posterPath, int voteAverage, String plotSynopses) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopses = plotSynopses;
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

    public String getPlotSynopses() {
        return plotSynopses;
    }
}
