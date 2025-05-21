package com.example.etravelguideproject;

public class Place {
    public String name, location, imageUrl, category;
    public double latitude, longitude;

    public Place() {} // For Firebase

    public Place(String name, String location, String imageUrl, double latitude, double longitude, String category) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }
}

