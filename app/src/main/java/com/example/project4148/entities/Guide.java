package com.example.project4148.entities;

import java.util.ArrayList;

public class Guide {
    String description,type,name,rating;
    ArrayList<String> lang,areas;

    public Guide() {
    }

    public Guide(String description, String type, String name, String rating, ArrayList<String> lang, ArrayList<String> areas) {
        this.description = description;
        this.type = type;
        this.name = name;
        this.rating = rating;
        this.lang = lang;
        this.areas = areas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getLang() {
        return lang;
    }

    public void setLang(ArrayList<String> lang) {
        this.lang = lang;
    }

    public ArrayList<String> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<String> areas) {
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
}
