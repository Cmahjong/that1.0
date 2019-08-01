package com.yj.that_plugin_library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * desc:
 * time: 2019/7/31
 *
 * @author 银进
 */
public interface ISelfActivity {
    public void onStart();
    public void onRestart();
    public void onActivityResult(int requestCode, int resultCode, Intent data);
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onCreate(Bundle savedInstanceState);
    public void setProxy(AppCompatActivity proxyActivity);

    public <T extends View> T findViewById(@IdRes int id);
}
