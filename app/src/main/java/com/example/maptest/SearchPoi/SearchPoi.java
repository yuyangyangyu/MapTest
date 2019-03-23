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
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.maptest.R;


public class SearchPoi extends AppCompatActivity {
    private EditText name;
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
        name=findViewById(R.id.name);
        search=findViewById(R.id.search_1);
        Poimap=findViewById(R.id.Poimap);
        webView=findViewById(R.id.context);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpoisearch.setOnGetPoiSearchResultListener(listener_1);
                mpoisearch.searchPoiDetail(new PoiDetailSearchOption().poiUids("480b24d065a865e89cec2b18"));
                Log.v("q","成功");

            }
        });
    }
    OnGetPoiSearchResultListener listener_1=new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            info=poiResult.getAllPoi().get(0);

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            Log.v("a", String.valueOf(poiDetailSearchResult.status));
            webView.getSettings().setJavaScriptEnabled(true);
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);//开启DOM
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("" +
                    "http://api.map.baidu.com/place/detail?uid=9396496511a7b84079b55c1e&output=html&source=placeapi_v2");
            Log.v("aa", String.valueOf(poiDetailSearchResult.getPoiDetailInfoList().get(0).detailUrl));


        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
}
