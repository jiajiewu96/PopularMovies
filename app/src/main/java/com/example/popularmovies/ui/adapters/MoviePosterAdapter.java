package com.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private ArrayList<Movie> mMovies;
    private final MoviePosterClickerHandler mClickerHandler;


    public interface MoviePosterClickerHandler{
        void onClick(Movie movie);
    }

    public void setMoviePosterStrings(ArrayList<Movie> Movies){
        this.mMovies = Movies;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies(){
        return mMovies;
    }


    public MoviePosterAdapter(MoviePosterClickerHandler clickerHandler){
        mClickerHandler = clickerHandler;
        mMovies = new ArrayList<>();
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_item, parent, false);
        return new MoviePosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
        String posterPath = mMovies.get(position).getPosterPath();
        Picasso.get().load(posterPath)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.posterImageView);
        holder.posterTextView.setText(mMovies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterImageView;
        TextView posterTextView;
        MoviePosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.movie_poster_iv);
            posterTextView = itemView.findViewById(R.id.movie_poster_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickerHandler.onClick(mMovies.get(adapterPosition));
        }
    }
}
