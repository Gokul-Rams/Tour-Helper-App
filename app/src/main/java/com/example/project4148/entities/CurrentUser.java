package com.example.project4148.entities;

public class CurrentUser {
    public String name,email;
    public Long phno;
    public Boolean isguide;

    public CurrentUser(String name, String email, Long phno, Boolean isguide) {
        this.name = name;
        this.email = email;
        this.phno = phno;
        this.isguide = isguide;
    }


    public CurrentUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhno() {
        return phno;
    }

    public void setPhno(Long phno) {
        this.phno = phno;
    }

    public Boolean getIsguide() {
        return isguide;
    }

    public void setIsguide(Boolean isguide) {
        this.isguide = isguide;
    }
}