package com.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CommentViewHolder> {

    private List<Review> mReviews;

    public void setReviews(List<Review> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }

    public ReviewAdapter(){
        mReviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.author.setText(mReviews.get(position).getAuthor());
        holder.content.setText(mReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView content;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_review_content);
            author = itemView.findViewById(R.id.tv_review_author);
        }
    }
}
