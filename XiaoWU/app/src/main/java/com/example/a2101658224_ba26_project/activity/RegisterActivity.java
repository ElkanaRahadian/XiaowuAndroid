package com.example.a2101658224_ba26_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.database.user.UserDatabase;
import com.example.a2101658224_ba26_project.model.User;
import com.example.a2101658224_ba26_project.otp.OtpHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText UsernameETRegisterActivity, PhoneNumberETRegisterActivity, EmailETRegisterActivity,
            PasswordETRegisterActivity, ConfirmPasswordETRegisterActivity;
    CheckBox CheckBoxRegisterActivity;
    Button ButtonRegisterRegisterActivity;
    TextView LoginTVRegisterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UsernameETRegisterActivity = findViewById(R.id.usernameETRegisterActivity);
        PhoneNumberETRegisterActivity = findViewById(R.id.phoneNumberETRegisterActivity);
        EmailETRegisterActivity = findViewById(R.id.emailETRegisterActivity);
        PasswordETRegisterActivity = findViewById(R.id.passwordETRegisterActivity);
        ConfirmPasswordETRegisterActivity = findViewById(R.id.confirmPasswordETRegisterActivity);
        CheckBoxRegisterActivity = findViewById(R.id.checkBoxRegisterActivity);
        ButtonRegisterRegisterActivity = findViewById(R.id.buttonRegisterRegisterActivity);
        LoginTVRegisterActivity = findViewById(R.id.loginTVRegisterActivity);
        ButtonRegisterRegisterActivity.setOnClickListener(this);
        LoginTVRegisterActivity.setOnClickListener(this);
    }

    private void validateButtonRegisterRegisterActivity() {
        String usernamePenampung = UsernameETRegisterActivity.getText().toString();
        String phoneNumberPenampung = PhoneNumberETRegisterActivity.getText().toString();
        String emailPenampung = EmailETRegisterActivity.getText().toString();
        String passwordPenampung = PasswordETRegisterActivity.getText().toString();
        String confirmPasswordPenampung = ConfirmPasswordETRegisterActivity.getText().toString();
        if (usernamePenampung.isEmpty()) {
            UsernameETRegisterActivity.setError("username must be filled");
        } else if (usernamePenampung.length() < 5 || usernamePenampung.length() > 20) {
            UsernameETRegisterActivity.setError("username must be between 5 to 20 characters");
        } else if (phoneNumberPenampung.isEmpty()) {
            PhoneNumberETRegisterActivity.setError("phone number must be filled");
        } else if ((!phoneNumberPenampung.startsWith("0") && !phoneNumberPenampung.startsWith("+62"))) {
            PhoneNumberETRegisterActivity.setError("phone number must start with “0” or “+62”");
        } else if (phoneNumberPenampung.length() < 11 || phoneNumberPenampung.length() > 14) {
            PhoneNumberETRegisterActivity.setError("phone number length between 11 to 14 digits”");
        } else if (emailPenampung.isEmpty()) {
            EmailETRegisterActivity.setError("email must be filled");
        } else if (!emailPenampung.endsWith(".com") && !emailPenampung.endsWith(".co.id")) {
            EmailETRegisterActivity.setError("email must ends with “.com” or “.co.id");
        } else if (emailPenampung.contains("@.") || emailPenampung.contains(".@") ||
                emailPenampung.indexOf("@") + 1 == emailPenampung.indexOf(".")) {
            EmailETRegisterActivity.setError("dot cannot be placed next to @ in the email");
        } else if (emailPenampung.indexOf("@") != emailPenampung.lastIndexOf("@")) {
            EmailETRegisterActivity.setError("email that there can be only one @");
        } else if (passwordPenampung.isEmpty()) {
            PasswordETRegisterActivity.setError("password must be filled");
        } else if (passwordPenampung.length() < 8 || passwordPenampung.length() > 24) {
            PasswordETRegisterActivity.setError("password length must be between 8 – 24 characters");
        } else if (confirmPasswordPenampung.isEmpty()) {
            ConfirmPasswordETRegisterActivity.setError("confirm password must be filled");
        } else if (!passwordPenampung.equals(confirmPasswordPenampung)) {
            ConfirmPasswordETRegisterActivity.setError("confirm password must be the same as the password field");
        } else if (!CheckBoxRegisterActivity.isChecked()) {
            CheckBoxRegisterActivity.setError("checkbox must be checked");
        } else {
            String otp = OtpHelper.generateOtp();
            User newUser = new User(usernamePenampung, phoneNumberPenampung, emailPenampung,
                    passwordPenampung, otp, false);
            long newUserId = new UserDatabase(this).insertUser(newUser);
            if (newUserId != -1) {
                OtpHelper.sendOtp(otp, this, phoneNumberPenampung);
                Intent intent = new Intent(this, OtpActivity.class);
                intent.putExtra("userId", newUserId);
                intent.putExtra("phoneNumber", phoneNumberPenampung);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Please input the OTP that's been sent via SMS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User already registered. Use a different username, email, " +
                        "phone number.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateLoginTVRegisterActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View viewRegisterActivity) {
        if (viewRegisterActivity.getId() == R.id.buttonRegisterRegisterActivity) {
            validateButtonRegisterRegisterActivity();
        } else if (viewRegisterActivity.getId() == R.id.loginTVRegisterActivity) {
            validateLoginTVRegisterActivity();
        }
    }
}