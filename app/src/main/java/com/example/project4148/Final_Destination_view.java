package com.example.project4148;

public class Final_Destination_view {
    private String no;
    private String Destination;
    public Final_Destination_view(String listno,String Destination){
        no=listno;
        this.Destination=Destination;
    }
    public String getNo(){
        return no;
    }
    public String getDestination(){
        return Destination;
    }
}
