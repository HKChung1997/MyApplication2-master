package com.example.chung.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Teacher_select_hwQR_code extends AppCompatActivity {
    private String selected;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select_hw_qr_code);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        final Spinner spinner = (Spinner) findViewById(R.id.spSpinner);
        final String[] Class = {"2B Chinese Copybook 2017-02-07", "2B English Worksheet x2 2017-02-07", "1A Mathematics Excise Book 2017-02-06", "1A Genreal Studies Worksheet 2017-02-17"};
        ArrayAdapter<String> ClassList = new ArrayAdapter<>(Teacher_select_hwQR_code.this,
                android.R.layout.simple_spinner_dropdown_item,
                Class);
        spinner.setAdapter(ClassList);
        Button btnSelect = (Button)findViewById(R.id.btSelect);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher_select_hwQR_code.this, QR_Code.class);
                Teacher_select_hwQR_code.this.startActivity(intent);

            }
        });
    }
}