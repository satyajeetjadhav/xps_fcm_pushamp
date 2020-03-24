package com.clevertap.ctintegration;

import android.app.Application;

import com.clevertap.android.sdk.ActivityLifecycleCallback;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
    }
}
