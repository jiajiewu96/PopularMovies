package com.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> mTrailers;

    public void setTrailerData(List<Trailer> trailers){
        mTrailers = trailers;
        notifyDataSetChanged();
    }

    public TrailerAdapter(){
        mTrailers = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        ImageView trailerThumbnail;
        TextView trailerName;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerThumbnail = itemView.findViewById(R.id.iv_trailer_thumb);
            trailerName = itemView.findViewById(R.id.tv_trailer_name);
        }
    }
}
