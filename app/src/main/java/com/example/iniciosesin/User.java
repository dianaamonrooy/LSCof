package com.example.iniciosesin;

public class User {
    private String Id;
    private String email;
    private String date;
    private String location;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User(String id, String email, String date,String location) {
        this.setId(id);
        this.setEmail(email);
        this.setDate(date);
        this.setLocation(location);
    }
}
