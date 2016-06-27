package com.alex.mvpapp;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hasee on 2016/6/22.
 */
public class App extends Application {
    private static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Logger.init();
        LeakCanary.install(this);
    }

    public static App getApp() {
        return app;
    }
}
