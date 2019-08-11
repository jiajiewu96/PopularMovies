package com.example.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private static final String TAG = MoviePosterAdapter.class.getSimpleName();

    private List<Movie> mMovies;
    private Context mContext;
    private final MoviePosterClickerHandler mClickerHandler;

    public interface MoviePosterClickerHandler{
        void onClick(String title, String releaseDate, String posterPath, int voteAverage, String plotSynopses);
    }

    public void setMoviePosterStrings(List<Movie> Movies){
        this.mMovies = Movies;
        notifyDataSetChanged();
    }

    public MoviePosterAdapter(Context context, MoviePosterClickerHandler clickerHandler){
        mClickerHandler = clickerHandler;
        mMovies = new ArrayList<>();
        mContext = context;
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
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.posterImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterImageView;
        public MoviePosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String title = mMovies.get(adapterPosition).getTitle();
            String releaseDate = mMovies.get(adapterPosition).getReleaseDate();
            String posterPath = mMovies.get(adapterPosition).getPosterPath();
            int rating = mMovies.get(adapterPosition).getVoteAverage();
            String overview = mMovies.get(adapterPosition).getOverview();
            mClickerHandler.onClick(title, releaseDate, posterPath, rating, overview);
        }
    }
}
