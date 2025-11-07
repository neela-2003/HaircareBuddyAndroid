// ReviewResponse.java
package com.saveetha.haircarebuddy.api;

import com.saveetha.haircarebuddy.ReviewModel;

import java.util.List;

public class ReviewResponse {
    private boolean status;
    private String message;
    private List<ReviewModel> data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ReviewModel> getData() {
        return data;
    }
}
