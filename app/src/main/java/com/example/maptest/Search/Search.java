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
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.maptest.GetLocation;
import com.example.maptest.Map.MyLocationListener;
import com.example.maptest.MyDataBaseHelper;
import com.example.maptest.R;
import com.example.maptest.SearchPoi.Distance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Search extends AppCompatActivity {

    public LocationClient mlocationclient = null;
    //自身位置的确认
    private GetLocation myListener = new GetLocation();

    private EditText city;//城市
    private Button bt_2;
    private PoiSearch poiSearch;
    private List<PliceList> mlist=new ArrayList<>();
    private MyAdapter adapter;
    private MapView mapView=null;

    String Data=null;
    //数据库测试
    private MyDataBaseHelper daHelper;//该数据库为搜索结果服务，在搜索结束时应该释放
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //最开始监听位置信息
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
                    final String city_name =city.getText().toString();
                    SendMessage(city_name);
                    while (Data==null);
                    parse(Data);
                    RecyclerView recyclerView = findViewById(R.id.result);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Search.this);
                    recyclerView.setLayoutManager(layoutManager);
                    MyAdapter adapter = new MyAdapter(Search.this, mlist);
                    recyclerView.setAdapter(adapter);

                }
            }
        });
    }
    //创建POI检索监听

    private void SendMessage(final  String city_name_1){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client =new OkHttpClient();
                    Request request=new Request.Builder()
                            .url(String.format("https://api.map.baidu.com/place/v2/search?query=景点&region=%s&scope=2&output=json&ak=6aIUo3QkSTNxVTyC6ukIO5nXDSiYuLWD", city_name_1))
                            .build();
                    Response response=client.newCall(request).execute();
                    Data=response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parse(String data){
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonObject1=jsonObject.getJSONArray("results");
            Log.v("sss", String.valueOf(jsonObject1.length()));
            LatLng var2 =new LatLng(GetLocation.latitude,GetLocation.longtitude);
            mlist.clear();
            SQLiteDatabase db = daHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //临时数据库，删除数据并重置id
            db.delete("Book",null,null);
            db.execSQL("update sqlite_sequence set seq=0 where name='Book'");



            for (int i=0;i<jsonObject1.length();i++){
                JSONObject jsonObject2 =jsonObject1.getJSONObject(i);
                Log.v("sss", String.valueOf(jsonObject2.getString("name")));
                LatLng var1 =new LatLng((double)jsonObject2.getJSONObject("location").get("lat"),(double)jsonObject2.getJSONObject("location").get("lng"));
                String url= String.valueOf(jsonObject2.getJSONObject("detail_info").get("detail_url"));
                Distance distance=new Distance(var1,var2);
                PliceList pliceList =new PliceList(jsonObject2.getString("name"),String.format("%.2f", distance.Long()/1000),
                        url);

                //向数据库添加数据
                contentValues.put("name",jsonObject2.getString("name"));
                contentValues.put("uid",jsonObject2.getString("uid"));
                //新增对经纬度的获取

                contentValues.put("latitude", jsonObject2.getJSONObject("location").getString("lat"));
                contentValues.put("longtitude", jsonObject2.getJSONObject("location").getString("lng"));

                //添加网址
                contentValues.put("url",url);




                db.insert("Book", null, contentValues);
                contentValues.clear();



                mlist.add(pliceList);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
