package com.example.maptest.Map;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import com.baidu.mapapi.map.BaiduMap;


public class Sensor implements SensorEventListener {
    private BaiduMap baiduMap;
    private Context context;
    private SensorManager sensorManager;
    private android.hardware.Sensor sensor;
    private float lastX;

    public Sensor(Context context ,BaiduMap baiduMap) {
        this.context=context;
        this.baiduMap=baiduMap;
    }
    public void start(){
        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager!=null){
            sensor=sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ORIENTATION);
        }
        if (sensor!=null){
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop()
    {
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
    public void setOnOrientationListener(OnOrientationListener mOnOrientationListener)
     {
         this.mOnOrientationListener = mOnOrientationListener;
     }

     public interface OnOrientationListener
     {
         void onOrientationChanged(float x);
     }

}
