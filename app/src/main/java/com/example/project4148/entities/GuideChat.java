package com.example.project4148.entities;

public class GuideChat {
    String mes,senderid,receiverid,timestamp;

    public GuideChat() {
    }

    public GuideChat(String mes, String senderid, String receiverid, String timestamp) {
        this.mes = mes;
        this.senderid = senderid;
        this.receiverid = receiverid;
        this.timestamp = timestamp;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
