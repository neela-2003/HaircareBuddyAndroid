package com.saveetha.haircarebuddy;

public class PendingItem {
    String name, time, date;
    int imageRes;

    public PendingItem(String name, String time, String date,int imageRes) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.imageRes = imageRes;
    }

    public String getName() { return name; }
    public String getTime() { return time; }
    public String getDate() { return date; }

    public int getImageRes() {
        return imageRes;
    }
}
