package com.clevertap.ctintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    CleverTapAPI cleverTapInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        CleverTapAPI.createNotificationChannel(this,"BRTesting","Xiaomi","Testing",4,true);
        cleverTapInstance = CleverTapAPI.getDefaultInstance(this);

        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Email", "cttesting1@clevertap.com");
        cleverTapInstance.onUserLogin(profileUpdate);

    }
}
