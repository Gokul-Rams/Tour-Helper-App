package com.example.project4148.entities;

public class Destination {
    String title,latLong,description,total_rating;
    Long individuals_rated;

    public Destination() {
    }

    public Destination(String title, String latLong, Long individuals_rated, String description, String total_rating) {
        this.title = title;
        this.latLong = latLong;
        this.individuals_rated = individuals_rated;
        this.description = description;
        this.total_rating = total_rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public Long getIndividuals_rated() {
        return individuals_rated;
    }

    public void setIndividuals_rated(Long individuals_rated) {
        this.individuals_rated = individuals_rated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(String total_rating) {
        this.total_rating = total_rating;
    }
}
