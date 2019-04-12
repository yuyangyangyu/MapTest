package com.example.maptest.Search;

import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.maptest.GetLocation;
import com.example.maptest.Map.MyLocationListener;
import com.example.maptest.MyDataBaseHelper;
import com.example.maptest.R;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    public LocationClient mlocationclient = null;
    //自身位置的确认
    private GetLocation myListener = new GetLocation();

    private EditText city;//城市
    private Button bt_2;
    private PoiSearch poiSearch;
    private List<String> mlist=new ArrayList<>();
    private MyAdapter adapter;
    private MapView mapView=null;
    //数据库测试
    private MyDataBaseHelper daHelper;//该数据库为搜索结果服务，在搜索结束时应该释放
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mlocationclient=new LocationClient(getApplicationContext());
        mlocationclient.registerLocationListener(myListener);
        LocationClientOption option =new LocationClientOption();
        option.setScanSpan(0);
        mlocationclient.setLocOption(option);
        mlocationclient.start();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("周边搜索");
        actionBar.show();
        bt_2=findViewById(R.id.bt);
        city=findViewById(R.id.city);
        mapView=findViewById(R.id.map);
        //数据库测试//////////////
        daHelper=new MyDataBaseHelper(this,"plan.db",null,2);
        daHelper.getWritableDatabase();
        poiSearch=PoiSearch.newInstance();//创建搜索实例
        bt_2.setOnClickListener(new View.OnClickListener() {
            //创建搜索条件
            @Override
            public void onClick(View v) {
                if (city.getText().toString().equals("")){
                    Toast.makeText(Search.this,"未输入",Toast.LENGTH_SHORT).show();
                }
                else {
                    poiSearch.setOnGetPoiSearchResultListener(listener);
                    poiSearch.searchInCity(new PoiCitySearchOption()
                            .pageCapacity(30)
                            .city(city.getText().toString()) //必填
                            .keyword("旅游景点") //必填
                            .pageNum(0));
                    mapView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    //创建POI检索监听
    OnGetPoiSearchResultListener listener=new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {

            //显示地点的经纬
            mlist.clear();
            SQLiteDatabase db = daHelper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            Log.v("sss", String.valueOf(poiResult.getAllPoi()));
            //临时数据库，删除数据并重置id
            db.delete("Book", null, null);
            db.execSQL("update sqlite_sequence set seq=0 where name='Book'");
            //删除数据库中的数据
            ///////
            for (int i=0;i<20;i++){
                mlist.add(String.valueOf(poiResult.getAllPoi().get(i).name));
                //向数据库添加数据
                contentValues.put("name",String.valueOf(poiResult.getAllPoi().get(i).name));
                contentValues.put("uid",String.valueOf(poiResult.getAllPoi().get(i).uid));
                //新增对经纬度的获取

                contentValues.put("latitude",poiResult.getAllPoi().get(i).getLocation().latitude);
                contentValues.put("longtitude",poiResult.getAllPoi().get(i).getLocation().longitude);




                db.insert("Book",null,contentValues);
                contentValues.clear();

            }
            Log.v("sss", String.valueOf(mlist));
            RecyclerView recyclerView=findViewById(R.id.result);
            LinearLayoutManager layoutManager=new LinearLayoutManager(Search.this);

            recyclerView.setLayoutManager(layoutManager);
            MyAdapter adapter=new MyAdapter(Search.this,mlist);
            recyclerView.setAdapter(adapter);

            //建筑物覆盖
            if (poiResult.error== SearchResult.ERRORNO.NO_ERROR){
                mapView.getMap().clear();
                PoiOverlay poiOverlay=new PoiOverlay(mapView.getMap());
                poiOverlay.setData(poiResult);
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
            }


        }
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override//详细数据返回
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

}
