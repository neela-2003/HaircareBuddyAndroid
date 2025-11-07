package com.saveetha.haircarebuddy.api;

import java.util.List;

public class UserListResponse {
    public boolean status;
    public String message;
    public List<UserData> users;

    public static class UserData {
        public String Name;
        public String Age;
        public String Gender;
        public String Number;
        public String Email;
    }
}
