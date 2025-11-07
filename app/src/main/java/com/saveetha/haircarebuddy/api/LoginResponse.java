package com.saveetha.haircarebuddy.api;

public class LoginResponse {
    private boolean status;
    private String message;
    private User user;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
