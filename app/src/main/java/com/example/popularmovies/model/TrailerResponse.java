package com.example.popularmovies.model;

import java.util.ArrayList;
import java.util.List;

public class TrailerResponse {
    private List<Trailer> results;

    public TrailerResponse() {
        results = new ArrayList<>();
    }

    public List<Trailer> getResults() {
        return results;
    }
}
