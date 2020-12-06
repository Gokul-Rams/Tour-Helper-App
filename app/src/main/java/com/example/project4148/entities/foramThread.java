package com.example.project4148.entities;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class foramThread {
    String title;
    String destination;
    String poster;
    String description;
    public ArrayList<String> commends = new ArrayList<>();

    public foramThread(String title, String destination, String poster, String description) {
        this.title = title;
        this.destination = destination;
        this.poster = poster;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
