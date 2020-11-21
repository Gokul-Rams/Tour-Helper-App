package com.example.project4148.entities;

public class DestinationAbs {
    String title,zone;
    Double total_rating;

    public DestinationAbs() {
    }

    public DestinationAbs(String title, String zone, Double total_rating) {
        this.title = title;
        this.zone = zone;
        this.total_rating = total_rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Double getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(Double total_rating) {
        this.total_rating = total_rating;
    }
}
