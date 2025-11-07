package com.saveetha.haircarebuddy;

public class AcceptedItem {
    private String name;
    private String time;
    private String date;
    private int imageResId;

    public AcceptedItem(String name, String time, String date, int imageResId) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getTime() { return time; }
    public String getDate() { return date; }
    public int getImageResId() { return imageResId; }
}
