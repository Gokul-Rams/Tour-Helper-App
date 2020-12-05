package com.example.project4148;

import android.app.Application;

import com.example.project4148.entities.CurrentUser;
import com.example.project4148.entities.foramThread;

import java.util.ArrayList;

public class Applicationclass extends Application {
    public static CurrentUser currentappuser;
    /*flag frag on screen
    1-homefrag
    2-destination frag
    3-destination queue frag
    4-guide frag
    5-friendsconnect
    6-chatlist*/
    public static int flagfragonhomescreen = 1;
    public static ArrayList<foramThread> threadlist = new ArrayList<>();
    Applicationclass(){
        flagfragonhomescreen = 1;
        threadlist = new ArrayList<>();
    }
}
