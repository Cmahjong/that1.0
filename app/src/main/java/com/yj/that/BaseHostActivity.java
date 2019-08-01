package com.yj.that;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import dalvik.system.DexClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * desc:这个类主要做资源合并工作
 * time: 2019/7/30
 *
 * @author 银进
 */
public class BaseHostActivity extends AppCompatActivity {
    //第三方apk的ClassLoader
    protected DexClassLoader classLoader;
    //第三方的dexPath
    protected String dexPath;
    //创建的assetManager
    private AssetManager assetManager;
    private Resources resources;
    private Resources.Theme theme;

    /***
     * 获取第三方apk的ClassLoader
     */
    protected void loadClassLoader() {
        dexPath = getFileStreamPath("that.apk").getPath();
        classLoader = new DexClassLoader(dexPath, getDir("dex", Context.MODE_PRIVATE).getPath(), null, getClassLoader());
    }

    /**
     * 整合资源
     *
     */
    protected void loadResource() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //创建AssertManager
        assetManager =getAssets();
        //获取里面的方法（加入资源的作用）
        Method addAssertPath = assetManager.getClass().getDeclaredMethod("addAssetPath", new Class[]{String.class});
        //执行此方法
        addAssertPath.setAccessible(true);
        addAssertPath.invoke(assetManager, dexPath);
        addAssertPath.invoke(assetManager, getPackageCodePath());
        //创建Resources
        resources = new Resources(assetManager, getResources().getDisplayMetrics(), getResources().getConfiguration());
        //获取Theme
        theme = resources.newTheme();
        theme.setTo(getTheme());
    }

    @Override
    public Resources getResources() {
        if (resources == null) {
            return super.getResources();
        }
        return resources;
    }

    @Override
    public AssetManager getAssets() {
        if (assetManager == null) {
            return super.getAssets();
        }
        return assetManager;
    }

    @Override
    public Resources.Theme getTheme() {
        if (theme == null) {
            return super.getTheme();
        }
        return theme;
    }
}
