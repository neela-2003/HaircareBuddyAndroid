package com.saveetha.haircarebuddy.api;

public class UserProfileResponse {
    private boolean status;
    private String message;
    private Data data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public Data getData() { return data; }

    public static class Data {
        private String Name;
        private String Age;
        private String Gender;
        private String Number;

        public String getName() { return Name; }
        public String getAge() { return Age; }
        public String getGender() { return Gender; }
        public String getNumber() { return Number; }
    }
}
