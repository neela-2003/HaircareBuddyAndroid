package com.saveetha.haircarebuddy.api;


public class HairQuizResponse {
    private boolean status;
    private String message;
    private String ai_response; // must match JSON key

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getAi_response() {
        return ai_response;
    }
}

