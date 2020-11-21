package com.example.project4148.entities;

import java.util.ArrayList;

public class GuideAbs {
    public String name,rating,key;
    ArrayList<String> areas;

    public GuideAbs() {
    }

    public GuideAbs(String name, String rating, String key, ArrayList<String> areas) {
        this.name = name;
        this.rating = rating;
        this.key = key;
        this.areas = areas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<String> areas) {
        this.areas = areas;
    }
}
