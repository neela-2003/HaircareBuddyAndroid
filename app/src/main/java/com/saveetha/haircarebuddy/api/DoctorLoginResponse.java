package com.saveetha.haircarebuddy.api;


public class DoctorLoginResponse {
    private Boolean status;
    private String message;
    private Doctor user;  // Your own User class

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Doctor getUser() {
        return user;
    }
}
