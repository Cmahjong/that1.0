package com.yj.that_plugin;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.yj.that_plugin_library.BaseService;

/**
 * desc:
 * time: 2019/8/1
 *
 * @author 银进
 */
public class MainService extends BaseService {
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        Log.e("MainService", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MainService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("MainService", "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MainService", "onUnbind");
        return super.onUnbind(intent);
    }
}
