package com.example.attendenceproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendenceproject.Constants.Constants;
import com.example.attendenceproject.Models.EmployeeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeLoginActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeLoginActivity";

    Button btnSignIn;
    EditText email, password;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        email = findViewById(R.id.employee_email_signin);
        password = findViewById(R.id.employee_password_signin);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonEmployeeSignIn(email.getText().toString());
            }
        });
    }

    private void buttonEmployeeSignIn(String email) {
        final ProgressDialog mDialog = new ProgressDialog(EmployeeLoginActivity.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        users.orderByChild("userEmail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDialog.dismiss();
                if (dataSnapshot.exists()) {
                    // Employee exists in the database
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        EmployeeModel employee = userSnapshot.getValue(EmployeeModel.class);
                        Constants.employeeModel = employee;

                        // Your additional logic can be added here if needed

                        // Proceed to the next page
                        navigateToEmployeeMainActivity();
                        return; // Exit the loop after finding the first match
                    }
                } else {
                    // Employee does not exist
                    Toast.makeText(EmployeeLoginActivity.this, "User not exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialog.dismiss();
                Log.w(TAG, "Error reading database: " + databaseError.toException());
            }
        });
    }

    private void navigateToEmployeeMainActivity() {
        Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeMainActivity.class);
        startActivity(intent);
        finish();
    }
}

