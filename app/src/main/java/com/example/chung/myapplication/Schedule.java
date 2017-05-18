package com.example.chung.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;

import com.squareup.picasso.Picasso;


public class Schedule extends AppCompatActivity {
    private String selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        final ImageView imgView = (ImageView) findViewById(R.id.ImageView01);
        Button btnSelect = (Button)findViewById(R.id.btnSelect);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final String[] Class = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> ClassList = new ArrayAdapter<>(Schedule.this,
                android.R.layout.simple_spinner_dropdown_item,
                Class);
        spinner.setAdapter(ClassList);
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
                                             Picasso.with(getApplicationContext())
                                                     .load("https://lenchan139.org/myWorks/fyp/web/handbook/upload/schedule_" + selected)
                                                     .resize(2000, 2000)
                                                     .into(imgView);
                                         }
                                     }
        );
    }
}
