package com.alex.app.ui;

import android.app.Application;

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
        LeakCanary.install(this);
    }

    public static App getApp() {
        return app;
    }
}
