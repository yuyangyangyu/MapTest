package com.example.maptest.Search;

public class PliceList {
    private String Name;
    private String Distance;
    private String mark_1;
    private String mark_2;
    private String url;
    public PliceList(String Name,String Distance,String url){
        this.Name=Name;
        this.Distance=Distance;
        this.url=url;
    }
    public String getName() {
        return Name;
    }
    public String getDistance() {
        return Distance;
    }

    public String getUrl() {
        return url;
    }
}
