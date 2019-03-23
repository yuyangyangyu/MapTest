package com.example.maptest.SearchPoi;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.maptest.R;


public class SearchPoi extends AppCompatActivity {
    private EditText name;
    private EditText name_1;
    private Button search;
    private MapView Poimap;
    private PoiInfo info;
    WebView webView;//浏览器
    //创建搜索实例
    PoiSearch mpoisearch=PoiSearch.newInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_poi);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("地点搜索");
        actionBar.show();
        name=findViewById(R.id.name);//c城市
        search=findViewById(R.id.search_1);
        webView=findViewById(R.id.context);
        name_1=findViewById(R.id.name_1);//
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpoisearch.setOnGetPoiSearchResultListener(listener_1);
                mpoisearch.searchInCity(new PoiCitySearchOption()
                        .pageCapacity(30)
                        .city(name.getText().toString()) //必填
                        .keyword(name_1.getText().toString()) //必填
                        .pageNum(0));
            }
        });
    }
    OnGetPoiSearchResultListener listener_1=new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            info=poiResult.getAllPoi().get(0);
            String url=info.getUid();
            mpoisearch.searchPoiDetail(new PoiDetailSearchOption().poiUids(url));

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            webView.getSettings().setJavaScriptEnabled(true);
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);//开启DOM
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(poiDetailSearchResult.getPoiDetailInfoList().get(0).detailUrl);
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
}
