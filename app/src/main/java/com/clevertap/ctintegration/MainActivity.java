package com.clevertap.ctintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity {

    CleverTapAPI cleverTapInstance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        CleverTapAPI.createNotificationChannel(this,"BRTesting","Xiaomi","Testing",4,true);
        cleverTapInstance = CleverTapAPI.getDefaultInstance(this);
        cleverTapInstance.getCleverTapID();

        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Email", "cttesting2@clevertap.com");
        cleverTapInstance.onUserLogin(profileUpdate);

        String xiaomiToken = MiPushClient.getRegId(this);
        Log.d("Main","Xiaomi token - " + xiaomiToken);
        if(xiaomiToken != null) {
            cleverTapInstance.pushXiaomiRegistrationId(xiaomiToken, true);
        }

        Branch.getInstance().initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                Log.d("Branch",referringParams.toString());
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String xiaomiToken = MiPushClient.getRegId(this);
        Log.d("Main","Xiaomi token - " + xiaomiToken);
    }
}
