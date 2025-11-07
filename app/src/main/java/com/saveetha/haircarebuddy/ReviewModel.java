package com.saveetha.haircarebuddy;

public class ReviewModel {
    private String reviewerName;
    private float rating;
    private String feedback;

    public ReviewModel(String reviewerName, float rating, String feedback) {
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.feedback = feedback;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public float getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }
}
