package com.example.maptest.Map;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;

public class MyLocationListener extends BDAbstractLocationListener implements SensorEventListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Context context;
    private SensorManager sensorManager;
    private android.hardware.Sensor sensor;
    private float lastX;

    private double x;
    private double y;

    public MyLocationListener(MapView mapView,Context context){
        this.mMapView=mapView;
        this.mBaiduMap=mapView.getMap();
        this.context=context;
    }
    @Override
    public void onReceiveLocation(BDLocation location) {
       if (location==null||mMapView==null){
           return;
       }
      MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(lastX)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
       x=location.getLatitude();
       y=location.getLongitude();
        mBaiduMap.setMyLocationData(locData);
    }


///方向传感器
    public void start(){
        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager!=null){
            sensor=sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ORIENTATION);
        }
        if (sensor!=null){
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
        }

    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == android.hardware.Sensor.TYPE_ORIENTATION)
        {
            float x = event.values[SensorManager.DATA_X];

            if (Math.abs(x - lastX) > 1.0)
            {
                if (mOnOrientationListener != null)
                {
                    mOnOrientationListener.onOrientationChanged(x);
                }
            }

            lastX = x;

        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }
    private OnOrientationListener mOnOrientationListener;
    public void setOnOrientationListener(
            OnOrientationListener mOnOrientationListener) {
         this.mOnOrientationListener = mOnOrientationListener;
     }

     public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }

}



