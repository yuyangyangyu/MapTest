package com.example.maptest.Search;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptest.MyDataBaseHelper;
import com.example.maptest.R;

import java.util.List;
//增加点击事件
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> mlist;
    private Context context;
    private MyDataBaseHelper Data;//读取
    static class ViewHolder extends RecyclerView.ViewHolder{
        View click;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            click=itemView;
            textView=itemView.findViewById(R.id.item_theme);
        }
    }

    public MyAdapter(Context context,List<String> mlist) {
        this.mlist=mlist;
        this.context=context;
    }

    @NonNull
    @Override//添加点击事件   跳转浏览器
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                String M=mlist.get(position);
                Toast.makeText(v.getContext(),"you click"+M,Toast.LENGTH_SHORT).show();
                Intent intent_web = new Intent(v.getContext(),Web.class);
                intent_web.putExtra("adress",M);
                context.startActivity(intent_web);

            }
        });
        /**
         * 长按添加事件
         */
        holder.click.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                int position=holder.getAdapterPosition();
                final String M=mlist.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setMessage("是否将该地点加入行程中?");
                builder.setCancelable(true);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Data = new MyDataBaseHelper(v.getContext(),"plan.db",null,2);
                        SQLiteDatabase DB=Data.getWritableDatabase();
                        Cursor cursor=DB.query("Book",new String[]{"name","uid","latitude","longtitude"},"name=?",new String[]{M},
                                null,null,null);
                        cursor.moveToFirst();
                        String uid=cursor.getString(cursor.getColumnIndex("uid"));


                        String latitude=cursor.getString(cursor.getColumnIndex("latitude"));
                        String longtitude=cursor.getColumnName(cursor.getColumnIndex("longtitude"));

                        Log.v("sss",latitude);


                        cursor.close();
                        //Log.v("s",uid);
                        ContentValues contentValues=new ContentValues();
                       // 向数据库添加数据
                        contentValues.put("name",M);
                        contentValues.put("uid",uid);


                        contentValues.put("latitude",latitude);
                        contentValues.put("longtitude",longtitude);

                        DB.insert("Category",null,contentValues);
                        contentValues.clear();
                    }
                });






                builder.show();
                //Toast.makeText(v.getContext(),"长按"+M,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {
        String name=mlist.get(i);
        viewHolder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
