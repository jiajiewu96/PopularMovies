package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    private String key;
    private String name;
    @SerializedName("site")
    private String siteName;

    public Trailer(){

    }

    public String getSiteName() {
        return siteName;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
