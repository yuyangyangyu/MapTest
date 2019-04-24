package com.example.maptest.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.maptest.AboutMe.AboutMe;
import com.example.maptest.Map.MapActivity;
import com.example.maptest.R;
import com.example.maptest.SearchPoi.SearchPoi;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;


//添加按键映射到各个界面（后期需改为主页面）
public class sign_in extends AppCompatActivity implements View.OnClickListener{
    TextView Location;//定位
    TextView Search_1;//周边搜素
    TextView Search_2;//具体地点搜索
    TextView Plan;//计划
    TextView About;//关于我

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activtity_sign);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("主页");
        actionBar.show();


        Location=findViewById(R.id.one);

        Search_1=findViewById(R.id.two);

        Search_2=findViewById(R.id.three);

        Plan=findViewById(R.id.four);

        About=findViewById(R.id.five);
        Location.setOnClickListener(this);
        Search_1.setOnClickListener(this);
        Search_2.setOnClickListener(this);
        Plan.setOnClickListener(this);
        About.setOnClickListener(this);

        SimpleDraweeView draweeView_1 =findViewById(R.id.DL);
        Uri uri = Uri.parse("https://b-ssl.duitang.com/uploads/item/201610/27/20161027112919_2LBMr.jpeg");
        draweeView_1.setImageURI(uri);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                Intent intent=new Intent(sign_in.this, MapActivity.class);
                startActivity(intent);
                break;

            case R.id.two:
                Intent intent1=new Intent(sign_in.this, com.example.maptest.Search.Search.class);
                startActivity(intent1);
                break;
            case R.id.three:
                Intent intent2=new Intent(sign_in.this, SearchPoi.class);
                startActivity(intent2);
                break;
            case R.id.four:
                Intent intent3=new Intent(sign_in.this, com.example.maptest.Plan.Plan.class);
                startActivity(intent3);
                break;
            case R.id.five:
                Intent intent4=new Intent(sign_in.this, AboutMe.class);
                startActivity(intent4);
                break;

        }
    }
}
