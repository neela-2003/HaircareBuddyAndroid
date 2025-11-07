package com.saveetha.haircarebuddy;

public class UserModel {
    private String Name;
    private int Age;
    private String Gender;
    private String Number;
    private String Email;

    public String getNumber() {
        return Number;
    }

    public String getEmail() {
        return Email;
    }

    private int imageResId = R.drawable.female; // Default image

    public UserModel(String name, int age, String gender, String number, String email, int imageResId) {
        Name = name;
        Age = age;
        Gender = gender;
        Number = number;
        Email = email;
        this.imageResId = imageResId;
    }

    public UserModel(String name, int age, String gender, String number, int imageResId) {
        this.Name = name;
        this.Age = age;
        this.Gender = gender;
        this.Number = number;
        this.imageResId = imageResId;
    }

    public String getName() { return Name; }
    public int getAge() { return Age; }
    public String getGender() { return Gender; }
    public String getMobile() { return Number; }
    public int getImageResId() {
        return Gender.equalsIgnoreCase("Male") ? R.drawable.male : R.drawable.female;
    }
}
