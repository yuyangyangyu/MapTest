package com.example.maptest.Plan;

import android.content.Intent;
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

        detaile.setOnGetPoiSearchResultListener(listener);
        detaile.searchPoiDetail(new PoiDetailSearchOption().poiUids(uid));
    }
    OnGetPoiSearchResultListener listener=new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            String url=poiDetailSearchResult.getPoiDetailInfoList().get(0).detailUrl;
            Log.v("aaa",url);
            WebSettings settings =webView.getSettings();
            settings.setDomStorageEnabled(true);//开启DOM
            //必须这样写 ，不然不显示页面出来
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
}
