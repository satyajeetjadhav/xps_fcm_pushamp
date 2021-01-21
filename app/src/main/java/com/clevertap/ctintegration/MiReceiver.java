package com.clevertap.ctintegration;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.pushtemplates.TemplateRenderer;
import com.clevertap.pushtemplates.Utils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.json.JSONException;

import java.util.List;

import static com.clevertap.android.sdk.Utils.stringToBundle;

public class MiReceiver extends PushMessageReceiver {
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.v("MiReceiver",
                "onReceivePassThroughMessage is called. " + message.toString());


        Log.e("MiReceiver","Content = "+message.getContent());

        try {
            String ctData = message.getContent();
            Bundle extras = stringToBundle(ctData);

            CleverTapAPI.processPushNotification(context,extras);

            if (Utils.isForPushTemplates(extras)) {
                TemplateRenderer.createNotification(context, extras);
                //TemplateRenderer.createNotification(context, extras, config);
            } else {
                CleverTapAPI.createNotification(context, extras);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
        public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String mRegId = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                Log.d("MiReceiver","Xiaomi token - " + mRegId);
                CleverTapAPI.getDefaultInstance(context).pushXiaomiRegistrationId(mRegId,true);
            }
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String mRegId = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                Log.d("MiReceiver","Xiaomi token - " + mRegId);
                CleverTapAPI.getDefaultInstance(context).pushXiaomiRegistrationId(mRegId,true);
            }
        }
    }

}
