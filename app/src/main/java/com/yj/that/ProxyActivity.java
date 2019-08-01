package com.yj.that;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.yj.that_plugin_library.BasePluginActivity;
import com.yj.that_plugin_library.ISelfActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProxyActivity extends BaseHostActivity {
    private static final String TAG = "ProxyActivity";
    public static final String EXTRA_LAUNCHER_CLASS_NAME = "EXTRA_LAUNCHER_CLASS_NAME";
    //要启动的className
    private String launcherClassName;
    //启动的apk里面的activity
    private ISelfActivity targetActivity;
    //存放apk第三方反射出来的生命周期函数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcherClassName = getIntent().getStringExtra(EXTRA_LAUNCHER_CLASS_NAME);
        if (launcherClassName == null) {
            launcherClassName = "com.yj.that_plugin.MainActivity";
        }
        loadClassLoader();
        try {
            loadResource();
            launchTargetActivity();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void launchTargetActivity() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //执行apk的OnCreate（Bundle savedInstanceState）方法
        Class<?> aClass = classLoader.loadClass(launcherClassName);
        targetActivity = (ISelfActivity) aClass.newInstance();
        Method setProxy = BasePluginActivity.class.getDeclaredMethod("setProxy", new Class[]{AppCompatActivity.class});
        setProxy.setAccessible(true);
        setProxy.invoke(targetActivity, this);
        //最底层的源码就是protected void onCreate(@Nullable Bundle savedInstanceState)，我们不知道第二个参数是什么，所以打算用反射来执行此方法
        //执行插件Activity的onCreate方法
        Bundle bundle = new Bundle();
        targetActivity.onCreate(bundle);
        //由于所有的生命周期函数都是不能直接使用的，那我们直接用反射来获取此方法，我们不可能每次执行生命周期的时候都反射这个方法，所以呢，我们用一个集合来存着

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult resultCode=" + resultCode);
//        Method onActivityResult = mActivityLifeCircleMethods.get("onActivityResult");
//        if (onActivityResult != null) {
//            try {
//                onActivityResult.invoke(targetActivity, new Object[] { requestCode, resultCode, data });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        targetActivity.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        targetActivity.onStart();
//        Method onStart = mActivityLifeCircleMethods.get("onStart");
//        if (onStart != null) {
//            try {
//                onStart.invoke(targetActivity, new Object[] {});
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        targetActivity.onRestart();
//        Method onRestart = mActivityLifeCircleMethods.get("onRestart");
//        if (onRestart != null) {
//            try {
//                onRestart.invoke(targetActivity, new Object[] { });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        targetActivity.onResume();
//        Method onResume = mActivityLifeCircleMethods.get("onResume");
//        if (onResume != null) {
//            try {
//                onResume.invoke(targetActivity, new Object[] { });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    protected void onPause() {
//        Method onPause = mActivityLifeCircleMethods.get("onPause");
//        if (onPause != null) {
//            try {
//                onPause.invoke(targetActivity, new Object[] { });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        super.onPause();
        targetActivity.onPause();
    }

    @Override
    protected void onStop() {
//        Method onStop = mActivityLifeCircleMethods.get("onStop");
//        if (onStop != null) {
//            try {
//                onStop.invoke(targetActivity, new Object[] { });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        super.onStop();
        targetActivity.onStop();
    }

    @Override
    protected void onDestroy() {
//        Method onDestroy = mActivityLifeCircleMethods.get("onDestroy");
//        if (onDestroy != null) {
//            try {
//                onDestroy.invoke(targetActivity, new Object[] { });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        super.onDestroy();
        targetActivity.onDestroy();
    }

}
