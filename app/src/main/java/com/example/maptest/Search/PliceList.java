package com.example.maptest.Search;

public class PliceList {
    private String Name;
    private String Distance;
    private String mark_1;
    private String mark_2;
    public PliceList(String Name,String Distance){
        this.Name=Name;
        this.Distance=Distance;
    }
    public String getName() {
        return Name;
    }
    public String getDistance() {
        return Distance;
    }
}
