package com.example.a2101658224_ba26_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.otp.SMSHelper;
import com.example.a2101658224_ba26_project.database.user.UserDatabase;
import com.example.a2101658224_ba26_project.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText UsernameETMainActivity, PasswordETMainActivity;
    private Button ButtonLoginMainActivity;
    private TextView RegisterTVMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameETMainActivity = findViewById(R.id.usernameETMainActivity);
        PasswordETMainActivity = findViewById(R.id.passwordETMainActivity);
        ButtonLoginMainActivity = findViewById(R.id.buttonLoginMainActivity);
        RegisterTVMainActivity = findViewById(R.id.registerTVMainActivity);
        ButtonLoginMainActivity.setOnClickListener(this);
        RegisterTVMainActivity.setOnClickListener(this);
        SMSHelper smsHelper = new SMSHelper(this);
        smsHelper.useSMS();
    }

    private void validateInputButtonLogin() {
        String usernamePenampung = UsernameETMainActivity.getText().toString();
        String passwordPenampung = PasswordETMainActivity.getText().toString();
        if (usernamePenampung.equals("")) {
            UsernameETMainActivity.setError("username must be filled");
        } else if (passwordPenampung.equals("")) {
            PasswordETMainActivity.setError("password must be filled");
        } else {
            User user = new UserDatabase(this).findUser(usernamePenampung, passwordPenampung);
            if (user != null) {
                if (user.isVerified()) {
                    Log.e("MOMOMO", String.valueOf(user.getUserId()));
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(this, "Your Account is not Verified", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "username and password must be registered before", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateRegisterTVMainActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View viewMainActivity) {
        if (viewMainActivity.getId() == R.id.buttonLoginMainActivity) {
            validateInputButtonLogin();
        } else if (viewMainActivity.getId() == R.id.registerTVMainActivity) {
            validateRegisterTVMainActivity();
        }
    }

}