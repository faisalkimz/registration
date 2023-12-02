package com.example.attendenceproject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendenceproject.Models.EmployeeModel;
import com.example.attendenceproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText employeeFullName, employeePassword, employeePhone, employeeDesignation;
    private Button btnSelect, btnUpload, btnSaveEmployee;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri saveUri;

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        employeeFullName = findViewById(R.id.et_employee_full_name);
        employeePhone = findViewById(R.id.et_employee_phone);
        employeeDesignation = findViewById(R.id.et_designation);
        employeePassword = findViewById(R.id.et_password);
        btnSelect = findViewById(R.id.btn_image_select);
        btnUpload = findViewById(R.id.btn_image_upload);
        btnSaveEmployee = findViewById(R.id.btn_save_employee);

        btnSelect.setOnClickListener(view -> selectImage());
        btnUpload.setOnClickListener(view -> uploadImage());

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            saveUri = data.getData();
            btnSelect.setText("Selected!");
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        String name = employeeFullName.getText().toString();
        String phone = employeePhone.getText().toString();
        String password = employeePassword.getText().toString();
        String designation = employeeDesignation.getText().toString();

        if (saveUri != null) {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/" + imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        mDialog.dismiss();
                        btnUpload.setText("Uploaded");
                        imageFolder.getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    EmployeeModel employeeModel = new EmployeeModel(name, password, designation, phone, uri.toString());
                                    saveEmployeeData(employeeModel);
                                });
                    })
                    .addOnFailureListener(e -> {
                        mDialog.dismiss();
                        Toast.makeText(AddEmployeeActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveEmployeeData(EmployeeModel employeeModel) {
        db.collection("employees")
                .document(employeeModel.getPhone())
                .set(employeeModel)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddEmployeeActivity.this, "Employee added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(AddEmployeeActivity.this, "Error adding employee: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}