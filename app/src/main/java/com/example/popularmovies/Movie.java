package com.example.popularmovies;

public class Movie {
    private String title;
    private String releaseDate;
    private String posterPath;
    private int voteAverage;
    private String plotSynopsys;

    public Movie() {

    }

    public Movie(String title, String releaseDate, String posterPath, int voteAverage, String plotSynopsys) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsys = plotSynopsys;
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

    public String getPlotSynopsys() {
        return plotSynopsys;
    }
}
