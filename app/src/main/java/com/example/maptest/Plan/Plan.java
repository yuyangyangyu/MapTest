package com.example.maptest.Plan;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
    private void init(final List<String> list){
        mydata=new MyDataBaseHelper(this,"plan.db",null,2);
        final SQLiteDatabase plan_name=mydata.getWritableDatabase();
        final Cursor cursor=plan_name.query("Category",null,null,null,null,null,null);
        //将数据库数据加载到内存中
        if (cursor.moveToFirst()){
            do {
                String name=cursor.getString(cursor.getColumnIndex("name"));
                list.add(name);
            }while (cursor.moveToNext());
            cursor.close();


            RecyclerView recyclerView=findViewById(R.id.plan);
            LinearLayoutManager manager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            final MyAdapter adapter=new MyAdapter(list);
            adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int i) {
                   //加载数据的URL
                    Cursor cursor1=plan_name.query("Category",new String[]{"name","uid"},"name=?",new String[]{list.get(i)},
                            null,null,null);
                    cursor1.moveToFirst();
                    String uid=cursor1.getString(cursor1.getColumnIndex("uid"));
                    //Log.v("a",uid);
                    //跳转浏览器详情页
                    Intent web_plan=new Intent(Plan.this, com.example.maptest.Plan.web_plan.class);
                    web_plan.putExtra("uid",uid);
                    startActivity(web_plan);
                    cursor1.close();//关闭
                }
            });
            adapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
                @Override
                public void onLongClick(View view,  final int i) {
                    Toast.makeText(view.getContext(),"成功",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                    final String delete =list.get(i);
                    builder.setTitle("warning");
                    builder.setMessage("是否将此计划从行程中删除?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override//对计划的更改
                        public void onClick(DialogInterface dialog, int which) {
                            plan_name.delete("Category","name=?",new String[]{delete});
                            list.remove(i);
                            adapter.notifyItemRemoved(i);
                        }
                    });
                    builder.show();
                }
            });
            recyclerView.setAdapter(adapter);
        }
        else {
            empty.setVisibility(View.VISIBLE);
        }
    }
}
