package com.example.a2101658224_ba26_project.otp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SMSHelper {
    private Context context;
    private SmsManager smsManager;

    public SMSHelper(Context context) {
        this.context = context;
    }

    public void useSMS() {
        smsManager = SmsManager.getDefault();
        int permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    public void sendSMS(String phoneNumber, String message) {
        useSMS();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
