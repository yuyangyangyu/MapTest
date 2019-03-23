package com.example.maptest.Search;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.maptest.MyDataBaseHelper;
import com.example.maptest.R;

public class Web extends AppCompatActivity {
    private MyDataBaseHelper data_name;//数据库
    private PoiSearch search_details; //搜索详情
    private WebView details;
    private String url;//详情网址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("详情");
        actionBar.show();
        details=findViewById(R.id.details);
        Intent intent=getIntent();
        String adress =intent.getStringExtra("adress");//获取名称
        //查询数据库是否存在
        data_name=new MyDataBaseHelper(this,"plan.db",null,1);
        SQLiteDatabase DB=data_name.getWritableDatabase();
        Cursor cursor=DB.query("Book",new String[]{"name","uid"},"name=?",new String[]{adress},
                null,null,null);
        cursor.moveToFirst();
        String uid=cursor.getString(cursor.getColumnIndex("uid"));
        cursor.close();
        search_details=PoiSearch.newInstance();
        search_details.setOnGetPoiSearchResultListener(listener_details);
        search_details.searchPoiDetail(new PoiDetailSearchOption().poiUids(uid));
    }
    OnGetPoiSearchResultListener listener_details=new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            //创建浏览器
            url=poiDetailSearchResult.getPoiDetailInfoList().get(0).detailUrl;
            //未知情况
            WebSettings settings =details.getSettings();
            settings.setDomStorageEnabled(true);//开启DOM
            //必须这样写 ，不然不现实页面出来
            details.getSettings().setJavaScriptEnabled(true);
            details.setWebViewClient(new WebViewClient());
            details.loadUrl(url);
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
}
