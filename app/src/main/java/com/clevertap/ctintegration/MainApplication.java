package com.clevertap.ctintegration;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener;
import com.clevertap.pushtemplates.TemplateRenderer;
import com.clevertap.pushtemplates.Utils;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class MainApplication extends Application implements CTPushAmpListener {

    public static final String APP_ID = "2882303761518655186";
    public static final String APP_KEY = "5191834634520";

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPushAmpPayloadReceived(Bundle extras) {

        NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);
        if (info.fromCleverTap) {
            if (Utils.isForPushTemplates(extras)) {
                TemplateRenderer.createNotification(getApplicationContext(), extras);
                //TemplateRenderer.createNotification(context, extras, config);
            } else {
                CleverTapAPI.createNotification(getApplicationContext(), extras);
            }
        }

    }
}
