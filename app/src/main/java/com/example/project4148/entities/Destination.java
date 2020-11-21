package com.example.project4148.entities;

public class Destination {
    public String name,zone,rating;

    public Destination() {
    }

    public Destination(String name, String zone, String rating) {
        this.name = name;
        this.zone = zone;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
