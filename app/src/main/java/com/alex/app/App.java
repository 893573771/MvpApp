package com.alex.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.squareup.leakcanary.LeakCanary;

import github.alex.AlexTools;
import github.alex.callback.ForegroundCallbacks;
import github.alex.callback.SimpleActivityLifecycleCallbacks;
import github.alex.helper.CrashHandler;
import github.alex.util.AppUtil;
import github.alex.util.LogUtil;
import github.alex.util.font.FontUtil;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AlexTools.init(app);
        /*捕获崩溃信息*/
        new CrashHandler(this, new MyOnCrashListener());
        FontUtil.initFormAssets(this, "font/simkai.ttf");
        /*管理 Activity 任务栈*/
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
        /*判断是否从后台进入到前台来*/
        ForegroundCallbacks callback = new ForegroundCallbacks();
        callback.addListener(new MyAppStatusListener());
        registerActivityLifecycleCallbacks(callback);
        /*观察 内存泄漏*/
        LeakCanary.install(this);
    }

    public static App getApp() {
        return app;
    }

    /**
     * 当后台程序已经终止资源还匮乏时会调用这个方法
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.e("onLowMemory");
    }

    /**
     * 当终止应用程序对象时调用，不保证一定被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.e("onTerminate");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (TRIM_MEMORY_UI_HIDDEN == level) {
            LogUtil.e("进程杀死 UI 不见了");
        } else {
            LogUtil.e("onTrimMemory level = " + level);
        }
    }

    private final class MyOnCrashListener implements CrashHandler.OnCrashListener {
        @Override
        public void onCrash(String error) {
            LogUtil.e("#崩溃信息#\n" + error);
        }
    }

    private final class MyActivityLifecycleCallbacks extends SimpleActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            super.onActivityCreated(activity, savedInstanceState);
            AppUtil.addActivity(activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtil.w("即将移除 " + activity.getClass().getSimpleName());
            AppUtil.removeActivity(activity);
        }
    }

    private final class MyAppStatusListener implements ForegroundCallbacks.AppStatusListener {
        @Override
        public void onBecameForeground() {
            LogUtil.w("回到前台");
        }

        @Override
        public void onBecameBackground() {
            LogUtil.w("进入后台");
        }
    }
}
