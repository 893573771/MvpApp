package com.alex.app.ui;

import android.app.Application;

import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;

import github.alex.helper.CrashHandler;

/**
 * Created by hasee on 2016/6/22.
 */
public class App extends Application {
    private static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        CrashHandler crashHandler = new CrashHandler(this,"mvp模式/cash.java" );
        crashHandler.setOnCrashListener(new MyOnCrashListener());
        LeakCanary.install(this);
    }

    public static App getApp() {
        return app;
    }
    private final class MyOnCrashListener implements CrashHandler.OnCrashListener {
        @Override
        public void onCrash(String error) {
            KLog.e(error);
        }

    }
}
