package com.example.maptest.Plan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.maptest.R;

public class web_plan extends AppCompatActivity {
    private WebView webView;
    private PoiSearch detaile;//声明搜素实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_plan);
        webView=findViewById(R.id.web_plan);
        Intent plan_web=getIntent();
        String uid=plan_web.getStringExtra("uid");
        detaile=PoiSearch.newInstance();//创建搜索实例
    }
}
