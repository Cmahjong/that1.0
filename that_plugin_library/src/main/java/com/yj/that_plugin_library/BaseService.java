package com.yj.that_plugin_library;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * desc:
 * time: 2019/8/1
 *
 * @author 银进
 */
public class BaseService extends Service implements ISelfService{
    Service yj;

    @Override
    public void setProxyService(Service service) {
        yj = service;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }
}
