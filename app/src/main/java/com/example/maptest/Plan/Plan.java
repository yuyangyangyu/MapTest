package com.example.maptest.Plan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptest.MyDataBaseHelper;
import com.example.maptest.R;

import java.util.ArrayList;
import java.util.List;

public class Plan extends AppCompatActivity {
    private TextView empty;
    private MyDataBaseHelper mydata;
    private List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("规划");
        actionBar.show();
        empty=findViewById(R.id.empty);
        init(list);//获取数据
    }
    private void init(List<String> list){
        mydata=new MyDataBaseHelper(this,"plan.db",null,2);
        SQLiteDatabase plan_name=mydata.getWritableDatabase();
        Cursor cursor=plan_name.query("Category",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String name=cursor.getString(cursor.getColumnIndex("name"));
                list.add(name);
                //Log.v("A",name);
            }while (cursor.moveToNext());
            RecyclerView recyclerView=findViewById(R.id.plan);
            LinearLayoutManager manager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            MyAdapter adapter=new MyAdapter(list);
            adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int i) {
                    //Toast.makeText(view.getContext(),"ssss",Toast.LENGTH_SHORT).show();
                }
            });
            adapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
                @Override
                public void onLongClick(View view, int i) {
                    Toast.makeText(view.getContext(),"ssss",Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setAdapter(adapter);
        }
        else {
            empty.setVisibility(View.VISIBLE);
        }
    }
}
