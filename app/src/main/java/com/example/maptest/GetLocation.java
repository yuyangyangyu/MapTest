package com.example.maptest;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
public class GetLocation extends BDAbstractLocationListener {
    public static double latitude;
    public static double longtitude;

    /**
     * 获取地址位置信息
     * @param location
     */
    @Override
    public void onReceiveLocation(BDLocation location) {
        latitude=location.getLatitude();
        longtitude= location.getLongitude();

    }
}
