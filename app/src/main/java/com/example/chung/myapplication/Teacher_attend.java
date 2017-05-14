package com.example.chung.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Teacher_attend extends AppCompatActivity {
    private String selected;
    private String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attend);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        final String[] Class = {"-class-","1A","1B","1C","1D","2A","2B","2C","2D","3A","3B","3C","3D","4A","4B","4C","4D","5A","5B","5C","5D","6A","6B","6C","6D"};
        ArrayAdapter<String> ClassList = new ArrayAdapter<>(Teacher_attend.this,
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
        btnSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int day = datePicker.getDayOfMonth();
                final int month = datePicker.getMonth()+1;
                final int year = datePicker.getYear();
                int monthLength = String.valueOf(month).length();
                int dayLength = String.valueOf(day).length();
                if (monthLength < 2 && dayLength < 2) {
                    date = year+"-"+"0"+month+"-"+"0"+day;
                }else if (monthLength < 2){
                    date = year+"-"+"0"+month+"-"+day;
                }else if (dayLength < 2){
                    date = year+"-"+month+"-"+"0"+day;
                }else {
                    date = year+"-"+month+"-"+day;
                }
                String error = new String("-class-");
                if (selected.equals(error)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select a class", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    Intent intent = new Intent(Teacher_attend.this, Teacher_select_class_attend.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("selected", selected);
                    intent.putExtra("Date", date);
                    Teacher_attend.this.startActivity(intent);
                }
            }
        });

    }
}