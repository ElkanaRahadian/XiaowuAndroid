package com.example.a2101658224_ba26_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.database.user.UserDatabase;
import com.example.a2101658224_ba26_project.otp.OtpHelper;

public class OtpActivity extends AppCompatActivity {

    private EditText inputOtp;
    private TextView resendOtp;
    private Button submitOtp;
    private int userId = -1;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        userId = (int) getIntent().getLongExtra("userId", -1);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        inputOtp = findViewById(R.id.editTextOtp);
        resendOtp = findViewById(R.id.resendOtp);
        submitOtp = findViewById(R.id.submitOtp);

        resendOtp.setOnClickListener(view -> {
            String otp = OtpHelper.generateOtp();
            boolean isSuccess = new UserDatabase(this).updateOtp(userId, otp);
            if (isSuccess) {
                OtpHelper.sendOtp(otp, this, phoneNumber);
            }
        });

        submitOtp.setOnClickListener(view -> {
            UserDatabase userDatabase = new UserDatabase(this);
            String otp = userDatabase.getOtpForUser(userId);
            if (otp != null && otp.equals(inputOtp.getText().toString())) {
                boolean isSuccess = userDatabase.updateIsVerifyForUser(userId, true);
                if (isSuccess) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            } else if (otp.isEmpty()) {
                resendOtp.setError("otp number must be filled");
            } else {
                Toast.makeText(this, "Incorrect OTP. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
}