package com.example.chung.myapplication;

import android.content.Intent;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Teacher_hw_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_hw_add);
        final TextView test = (TextView) findViewById(R.id.tvClass);
        final EditText etClass = (EditText)findViewById(R.id.etClass);
        final EditText etSubject = (EditText)findViewById(R.id.etSubject);
        final EditText etTitle = (EditText)findViewById(R.id.etTitle);
        final EditText etDescription = (EditText)findViewById(R.id.etDescription);
        final DatePicker dpDatePicker = (DatePicker)findViewById(R.id.datePicker);
        final Button btnAdd = (Button) findViewById(R.id.btnAddhw);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password= intent.getStringExtra("password");


        btnAdd.setOnClickListener(new View.OnClickListener(){




            @Override
            public void onClick(View v) {
                final String Class = etClass.getText().toString();
                final String Subject = etSubject.getText().toString();
                final String Title = etTitle.getText().toString();
                final String Description = etDescription.getText().toString();
                final int day = dpDatePicker.getDayOfMonth();
                final int month = dpDatePicker.getMonth();
                final int year = dpDatePicker.getYear();
                final String deadline = year+"-"+month+"-"+day;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String addCHK = jsonResponse.getString("status");
                            String isSuccess = new String("success");
                            if (addCHK.equals(isSuccess)) {
                                Toast toast = Toast.makeText(Teacher_hw_add.this, "Add Howework Success", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(Teacher_hw_add.this, "Add Howework Fail", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                addhwRequest addhwRequest = new addhwRequest(username, password, Class, Subject, Title, Description, deadline,  responseListener);
                RequestQueue queue = Volley.newRequestQueue(Teacher_hw_add.this);
                queue.add(addhwRequest);
            }
        });
    }
}
