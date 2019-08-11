package com.example.popularmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private List<String> posterPaths;

    public void setMoviePosterStrings(List<String> posterPaths){
        this.posterPaths = posterPaths;
        notifyDataSetChanged();
    }

    public MoviePosterAdapter(){

    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_item, parent, false);
        return new MoviePosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return posterPaths.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder{
        ImageView posterImageView;
        public MoviePosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.movie_poster);
        }
    }
}
