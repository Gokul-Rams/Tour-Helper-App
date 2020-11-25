package com.example.project4148.entities;

public class DestinationAbs {
    String title,zone,latLong;
    Double total_rating;
    public boolean isselected;

    public DestinationAbs() {
    }

    public DestinationAbs(String title, String zone, Double total_rating,String latLong) {
        this.title = title;
        this.zone = zone;
        this.total_rating = total_rating;
        this.latLong = latLong;
        this.isselected = false;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
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
