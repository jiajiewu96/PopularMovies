package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class TrailerResponse {

    private String key;
    private String name;
    @SerializedName("site")
    private String siteName;

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
