package com.example.attendenceproject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize your views
        email = findViewById(R.id.admin_email_signin);
        password = findViewById(R.id.admin_password_signin);
        btnSignIn = findViewById(R.id.btn_sign_in);

        // Button click listener
        btnSignIn.setOnClickListener(view -> {
            buttonAdminSignIn(email.getText().toString(), password.getText().toString());
        });
    }

    private void startAdminMainActivity() {
        Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void buttonAdminSignIn(String Email, String Password) {
        // Hardcoded test credentials
        String testEmail = "m@m.com";
        String testPassword = "12345678";

        if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Email.equals(testEmail) && Password.equals(testPassword)) {
            // Test credentials are correct
            final ProgressDialog mDialog = new ProgressDialog(AdminLoginActivity.this);
            mDialog.setMessage("Please wait...");
            mDialog.show();

            // Simulate a delay to show the progress dialog (you can remove this in a real scenario)
            email.postDelayed(() -> {
                mDialog.dismiss();
                // Credentials are correct, proceed to AdminMainActivity
                startAdminMainActivity();
            }, 2000);
        } else {
            // Show error message for incorrect test credentials
            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }
}