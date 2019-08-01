package com.yj.that_plugin_library;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;



public class BasePluginActivity extends AppCompatActivity implements ISelfActivity {
    protected AppCompatActivity yj;

    @Override
    public void setProxy(AppCompatActivity activity) {
        yj = activity;
    }

    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        return yj.getDelegate().findViewById(id);
    }


    @Override
    public void onStart() {
    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    public void setContentView(View view) {
        yj.setContentView(view);
    }

    public void setContentView(int layoutResID)  {
        yj.setContentView(layoutResID);
    }

    public void startActivity(Intent intent) {
        yj.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        yj.startActivityForResult(intent, requestCode);
    }

    public Resources getResources() {
        return yj.getResources();
    }
}
