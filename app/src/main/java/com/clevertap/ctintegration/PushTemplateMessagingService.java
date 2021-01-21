package com.clevertap.ctintegration;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.clevertap.pushtemplates.TemplateRenderer;
import com.clevertap.pushtemplates.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class PushTemplateMessagingService extends FirebaseMessagingService {

    Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            context = getApplicationContext();
            if (remoteMessage.getData().size() > 0) {
                Bundle extras = new Bundle();

                for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);


                if (info.fromCleverTap) {
                    CleverTapAPI.processPushNotification(context,extras);
                    if (Utils.isForPushTemplates(extras)) {
                        TemplateRenderer.createNotification(context, extras);
                        //TemplateRenderer.createNotification(context, extras, config);
                    } else {
                        CleverTapAPI.createNotification(context, extras);
                    }
                }
            }
        } catch (Throwable throwable) {
        }
    }
    @Override
    public void onNewToken(@NonNull final String s) {
        //no-op
    }
}