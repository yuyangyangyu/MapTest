package com.example.maptest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maptest.Login.sign_in;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText AccountEdit;
    private EditText PasswoldEdit;
    private Button button;
    private CheckBox checkBox;
    //关于权限的动态申请
    private String[] permissions=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.INTERNET,

    };
    private List<String> mpermissions =new ArrayList<>();//判断哪些权限没有得到授权，并放在其中
    private final int mrequestcode=100;//权限请求码
    //权限判断和申请
    private void initperssion(){
        mpermissions.clear();
        for (int i=0;i<permissions.length;i++){
            if (ContextCompat.checkSelfPermission(this,permissions[i])!=
                    PackageManager.PERMISSION_GRANTED){
                mpermissions.add(permissions[i]);
            }
        }
        if (mpermissions.size()>0){
            Log.v("sss","2222222");
            ActivityCompat.requestPermissions(this,permissions,mrequestcode);
        }
        else{
            //初始化
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("登陆");
        actionBar.show();
        initperssion();//动态加载权限
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        AccountEdit=findViewById(R.id.user);
        PasswoldEdit=findViewById(R.id.password);
        checkBox=findViewById(R.id.remenber);
        button=findViewById(R.id.sign_in);
        boolean isRemenber=pref.getBoolean("remenber",false);
        if (isRemenber){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            AccountEdit.setText(account);
            PasswoldEdit.setText(password);
            checkBox.setChecked(true);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accunt=AccountEdit.getText().toString();
                String password=PasswoldEdit.getText().toString();
                if (accunt.equals("1813233580")&&password.equals("123456")) {
                    editor = pref.edit();
                    if (checkBox.isChecked()) {
                        editor.putBoolean("remenber", true);
                        editor.putString("account", accunt);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, sign_in.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this,"falied!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过

        if (mrequestcode==requestCode){
            for (int i=0;i<grantResults.length;i++){
                Log.v("sss", String.valueOf(grantResults[i]));
                if (grantResults[i]==-1){
                    hasPermissionDismiss=true;
                    break;
                }
            }
        }
        if (hasPermissionDismiss){//如果有没有被允许的权限
            showPermissionDialog();
        }else {
            //权限已经都通过了，可以将程序继续打开了

        }

    }

    AlertDialog mPermissionDialog;
    String mPackName = "com.example.maptest";
    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.
                                    ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            finish();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }
    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }
}

