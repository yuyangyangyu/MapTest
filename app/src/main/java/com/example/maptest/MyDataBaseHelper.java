package com.example.maptest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    //建立数据库
    public static final String Plan="create table Book("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"uid text,"
            +"latitude text,"
            +"longtitude text)";
    // 建立第二张数据表
    public static final String Plan_1="create table Category("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"uid text,"
            +"latitude text,"
            +"longtitude text)";



    //
    private Context mcontext;

    public MyDataBaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Plan);
        db.execSQL(Plan_1);
        Toast.makeText(mcontext,"cteate successful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);

    }
}
