package com.example.a2101658224_ba26_project.otp;

import android.content.Context;
import android.util.Log;

import java.util.Random;

public class OtpHelper {
    public static String generateOtp() {
        Random random = new Random();
        String otp = "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
        return otp;
    }

    public static void sendOtp(String otp, Context context, String phoneNumber) {
        Log.e("OtpHelper", "The OTP number is " + otp);
        SMSHelper smsHelper = new SMSHelper(context);
        smsHelper.sendSMS(phoneNumber, otp);
    }
}
