package com.yj.that;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.yj.that_plugin_library.ISelfService;
import dalvik.system.DexClassLoader;

/**
 * desc:
 * time: 2019/8/1
 *
 * @author 银进
 */
public class ProxyBindService extends Service {
    ISelfService selfService;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        String dexPath = getFileStreamPath("that.apk").getPath();
        DexClassLoader classLoader = new DexClassLoader(dexPath, getDir("dex", Context.MODE_PRIVATE).getPath(), null, getClassLoader());
        try {
            selfService = (ISelfService) classLoader.loadClass("com.yj.that_plugin.MainService").newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        selfService.setProxyService(this);
        selfService.onCreate();
        selfService.onBind(intent);
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return selfService.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        super.onUnbind(intent);
        return selfService.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onCreate();
        selfService.onDestroy();
    }
}
