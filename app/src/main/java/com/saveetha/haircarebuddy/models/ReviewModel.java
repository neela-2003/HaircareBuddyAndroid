package com.saveetha.haircarebuddy.models;

public class ReviewModel {
    private String email;
    private String view;
    private int rating;
    private String created_at;

    public String getReviewerName() {
        return email;
    }

    public String getFeedback() {
        return view;
    }

    public int getRating() {
        return rating;
    }

    public String getCreatedAt() {
        return created_at;
    }
}
