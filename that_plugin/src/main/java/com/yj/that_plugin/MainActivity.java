package com.yj.that_plugin;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.yj.that_plugin_library.BasePluginActivity;


public class MainActivity extends BasePluginActivity {

    public void onCreate(Bundle savedInstanceState) {
        //为什么要使用yj也就是宿主的app呢，因为资源整合是整合在宿主里面的，所以要用他的
        super.setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(yj,"你好",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setProxy(AppCompatActivity activity) {
        Log.e("activity_main1", "setProxy");
        super.setProxy(activity);
    }

    @Override
    public void onStart() {
        Log.e("activity_main1", "onStart");
        super.onStart();
    }

    @Override
    public void onRestart() {
        Log.e("activity_main1", "onRestart");
        super.onRestart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("activity_main1", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        Log.e("activity_main1", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("activity_main1", "onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e("activity_main1", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void setContentView(View view) {
        Log.e("activity_main1", "setContentView");
        super.setContentView(view);
    }



    @Override
    public void startActivity(Intent intent) {
        Log.e("activity_main1", "startActivity");
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Log.e("activity_main1", "startActivityForResult");
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public Resources getResources() {
        Log.e("activity_main1", "getResources");
        return super.getResources();
    }

    @Override
    public void onStop() {
        Log.e("activity_main1", "onStop");
        super.onStop();
    }
}
