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
        String URL =intent.getStringExtra("url");//获取详细网址
        //未知情况
        WebSettings settings =details.getSettings();
        settings.setDomStorageEnabled(true);//开启DOM
        //必须这样写 ，不然不现实页面出来
        details.getSettings().setJavaScriptEnabled(true);
        details.setWebViewClient(new WebViewClient());
        details.loadUrl(URL);
    }

}
