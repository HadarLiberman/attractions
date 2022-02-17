package com.example.attractionsapp;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context context;

    public static Context getContext() {
        return context;
    }

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

}
