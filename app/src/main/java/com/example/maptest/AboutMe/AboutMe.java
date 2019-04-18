package com.example.maptest.AboutMe;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptest.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import static android.widget.Toast.*;

public class AboutMe extends AppCompatActivity implements View.OnClickListener {
    private TextView git;//github跳转
    private TextView wb;//weibo跳转

    final static int COUNTS=5;
    final static long DURATION =1000;
    long[] mHit =new long[COUNTS];

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.http:

        }
    }
    @Override//添加头像图标
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_about_me);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("关于");
        actionBar.show();

        git=findViewById(R.id.http);
        wb=findViewById(R.id.weibo);

        git.setOnClickListener(this);
        wb.setOnClickListener(this);


        SimpleDraweeView draweeView =findViewById(R.id.drawee_img);
        Uri uri = Uri.parse("res://drawable/"+R.drawable.touxiang);
        draweeView.setImageURI(uri);
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               continuousClick(COUNTS,DURATION);
            }
        });


        //github图标

        SimpleDraweeView draweeView1=findViewById(R.id.icon);
        Uri uri1=Uri.parse("res://drawable/"+R.drawable.github);
        draweeView1.setImageURI(uri1);

        draweeView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri https=Uri.parse("https://github.com/yuyangyangyu");
                Intent intent=new Intent(Intent.ACTION_VIEW,https);
                startActivity(intent);
            }
        });

        //微博图标
        SimpleDraweeView draweeView2=findViewById(R.id.icon_1);
        Uri uri2=Uri.parse("res://drawable/"+R.drawable.weibo);
        draweeView2.setImageURI(uri2);

        draweeView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri https_1=Uri.parse("https://weibo.com/u/5015819282");
                Intent intent1=new Intent(Intent.ACTION_VIEW,https_1);
                startActivity(intent1);
            }
        });

    }
    private void continuousClick(int count, long time) {
        //每次点击时，数组向前移动一位
        System.arraycopy(mHit, 1, mHit, 0, mHit.length - 1);
        //为数组最后一位赋值
        mHit[mHit.length - 1] = SystemClock.uptimeMillis();
        if (mHit[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHit = new long[COUNTS];//重新初始化数组
            Toast.makeText(this, "滚呐", Toast.LENGTH_SHORT).show();
        }

    }
}
