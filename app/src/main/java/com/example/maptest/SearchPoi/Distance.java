package com.example.maptest.SearchPoi;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

public class Distance {
    private LatLng vr1;
    private LatLng vr2;
    private Double longth;
    public Distance(LatLng vr1,LatLng vr2){
        this.vr1=vr1;
        this.vr2=vr2;
    }
    public double Long(){
        longth=DistanceUtil.getDistance(vr1,vr2);
        return longth;
    }
}
