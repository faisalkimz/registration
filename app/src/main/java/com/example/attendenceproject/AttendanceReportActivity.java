package com.example.attendenceproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.attendenceproject.Adapters.EmployeeAdapter;
import com.example.attendenceproject.Models.EmployeeModel2;

import java.util.ArrayList;

public class AttendanceReportActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<EmployeeModel2> employeeList;
    RecyclerView recyclerViewEmployee;
    EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        employeeList();
        setRecyclerView();
    }

    public void employeeList() {
        employeeList = new ArrayList<>();
        employeeList.add(new EmployeeModel2("Mbabali Faisal", "android developer", R.drawable.ic_person_outline));
        employeeList.add(new EmployeeModel2("Egwau Junior", " developer", R.drawable.ic_person_outline));
        employeeList.add(new EmployeeModel2("Laala Tendo", "php developer", R.drawable.ic_person_outline));
        employeeList.add(new EmployeeModel2("Richard", "full stack  developer", R.drawable.ic_person_outline));

    }

    public void setRecyclerView() {
        recyclerViewEmployee = findViewById(R.id.recyclerview_employee);
        recyclerViewEmployee.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerViewEmployee.setLayoutManager(mLayoutManager);
        recyclerViewEmployee.setAdapter(employeeAdapter);

    }
}