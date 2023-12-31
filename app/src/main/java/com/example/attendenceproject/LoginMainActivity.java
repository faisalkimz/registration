package com.example.attendenceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView admin, staff, tryaddbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        admin = findViewById(R.id.admin_login);
        admin.setOnClickListener(this);
        staff = findViewById(R.id.staff_login);
        staff.setOnClickListener(this);
        tryaddbtn= findViewById(R.id.trythebutton);
        tryaddbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == admin) {
            Intent intent = new Intent(LoginMainActivity.this, AdminLoginActivity.class);
            startActivity(intent);

        } else if (view == staff) {
            Intent intent = new Intent(LoginMainActivity.this, EmployeeLoginActivity.class);
            startActivity(intent);

        } else if (view == tryaddbtn) {
            Intent intent = new Intent(LoginMainActivity.this, SignActivity.class);
            startActivity(intent);

        }
    }
}