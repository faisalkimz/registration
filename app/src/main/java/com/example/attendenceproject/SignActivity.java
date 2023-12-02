package com.example.attendenceproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignActivity extends AppCompatActivity {

    private EditText fullname, email, phonenumber, password;
    private Button signup;
    private RadioGroup radioGroup;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        fullname = findViewById(R.id.fullnames);
        email = findViewById(R.id.enteremail);
        phonenumber = findViewById(R.id.phoneno);
        password = findViewById(R.id.etpass);
        radioGroup = findViewById(R.id.userTypeRadioGroup);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signup = findViewById(R.id.btnsignin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        final String uname = fullname.getText().toString().trim();
        final String uemail = email.getText().toString().trim();
        final String upno = phonenumber.getText().toString().trim();
        final String upass = password.getText().toString().trim();

        if (uname.isEmpty() || uemail.isEmpty() || upno.isEmpty() || upass.isEmpty()) {
            // Handle validation errors
            return;
        }

        mAuth.createUserWithEmailAndPassword(uemail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Set user type in Firestore
                    boolean isAdmin = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().equals("Admin");
                    addUserDataToFirestore(uname, uemail, upno, upass, isAdmin);

                    Toast.makeText(SignActivity.this, "Registration successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SignActivity.this, "Failed to Authenticate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addUserDataToFirestore(String uname, String uemail, String upno, String upass, boolean isAdmin) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("UserName", uname);
        userData.put("UserEmail", uemail);
        userData.put("UserMobile", upno);
        userData.put("Password", upass);
        userData.put("isAdmin", isAdmin);

        firestore.collection("Users").add(userData);
    }
}