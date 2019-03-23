package com.example.maptest.Map;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.example.maptest.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private Context context;
    private float mCurrentX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_map);//在此方法之前不能再添加
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("定位");
        actionBar.show();
        mapView=findViewById(R.id.bmapView);

        SimpleDraweeView draweeView =findViewById(R.id.T);
        Uri uri = Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553152662564&di=de374a0a7ccefd299733ea9ff339e88a&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fd371255bc403f9113296f39f5c0640823f3943ad46c16-nCKVRM_fw658");
        draweeView.setImageURI(uri);
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.zoom(18.0f);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                mLocationClient.start();

            }
        });
        //自定义图标
        this.context=this;
        final BitmapDescriptor customMarker= BitmapDescriptorFactory.fromResource(R.drawable.mark);//自定义图标路径
        mBaiduMap=mapView.getMap();
        mapView.showZoomControls(false);//不显示缩放按键控件
        mBaiduMap.setMyLocationEnabled(true);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(16.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mLocationClient=new LocationClient(getApplicationContext());
        LocationClientOption option=new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setIsNeedAddress(true);//需要地址信息
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener(mapView,context);

        myLocationListener.setOnOrientationListener(new MyLocationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX=x;
            }
        });
       //设置地图状态
        MyLocationConfiguration configuration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,
                true,customMarker);
        mBaiduMap.setMyLocationConfiguration(configuration);
        mLocationClient.registerLocationListener(myLocationListener);
        myLocationListener.start();//跟随方向
//事件的触发
        mLocationClient.start();
        BaiduMap.OnMapStatusChangeListener listener1=new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
                if (i==REASON_GESTURE){
                    Log.v("aaa","S");
                    mLocationClient.stop();
                }

            }
            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

            }
        };
        mBaiduMap.setOnMapStatusChangeListener(listener1);

    }
    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }
}



