package com.saveetha.haircarebuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final List<ReviewModel> reviewList;

    public ReviewAdapter(List<ReviewModel> reviewList) {
        this.reviewList = reviewList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewerName, tvFeedback;
        TextView ratingBarItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvReviewerName = itemView.findViewById(R.id.tvEmail);
            tvFeedback = itemView.findViewById(R.id.tvReview);
            ratingBarItem = itemView.findViewById(R.id.tvRating);
        }
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewModel review = reviewList.get(position);
        holder.tvReviewerName.setText(review.getReviewerName());
        holder.tvFeedback.setText(review.getFeedback());
        holder.ratingBarItem.setText(String.valueOf(review.getRating()));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
