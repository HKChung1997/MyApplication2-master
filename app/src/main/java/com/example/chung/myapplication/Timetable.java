package com.example.chung.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;


public class Timetable extends AppCompatActivity {
    private String selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        final ImageView imgView = (ImageView) findViewById(R.id.ImageView01);
        Button btnSelect = (Button)findViewById(R.id.btnSelect);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final String[] Class = {"1A","1B","1C","1D","2A","2B","2C","2D","3A","3B","3C","3D","4A","4B","4C","4D","5A","5B","5C","5D","6A","6B","6C","6D"};
        ArrayAdapter<String> ClassList = new ArrayAdapter<>(Timetable.this,
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
                                                     .load("https://lenchan139.org/myWorks/fyp/web/handbook/upload/timetable_" + selected)
                                                     .resize(3000, 3000)
                                                     .into(imgView);
                                         }
                                     }
        );
    }
}
