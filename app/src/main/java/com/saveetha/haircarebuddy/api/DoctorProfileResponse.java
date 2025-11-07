package com.saveetha.haircarebuddy.api;

public class DoctorProfileResponse {
    public boolean status;
    public String message;
    public Data data;

    public static class Data {
        public String name;
        public String doctorId;
        public String experience;
    }
}
