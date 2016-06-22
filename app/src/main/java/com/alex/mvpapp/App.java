package com.alex.mvpapp;

import android.app.Application;

import com.orhanobut.logger.Logger;

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
    }

    public static App getApp() {
        return app;
    }
}
