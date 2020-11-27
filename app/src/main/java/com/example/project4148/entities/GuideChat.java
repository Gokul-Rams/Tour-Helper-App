package com.example.project4148.entities;

public class GuideChat {
    String mes;
    boolean byme;

    public GuideChat(String mes, boolean byme) {
        this.mes = mes;
        this.byme = byme;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public boolean isByme() {
        return byme;
    }

    public void setByme(boolean byme) {
        this.byme = byme;
    }
}
