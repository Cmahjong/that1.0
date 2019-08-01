package com.yj.that_plugin_library;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * desc:
 * time: 2019/7/31
 *
 * @author 银进
 */
public interface ISelfService {
    public void setProxyService(Service service);
    public IBinder onBind(Intent intent);
    public void onCreate();
    public int onStartCommand(Intent intent, int flags, int startId);
    public void onDestroy();
    public boolean onUnbind(Intent intent);

}
