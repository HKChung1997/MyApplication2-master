package com.example.chung.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TextView tvUsername = (TextView)findViewById(R.id.tvUsername);
        final TextView tvVersion = (TextView)findViewById(R.id.tvVersion);
        Button btnQRintent = (Button) findViewById(R.id.bQRintent);
        Button btnTAttend = (Button) findViewById(R.id.bTAttend);
        Button btnTMessage = (Button) findViewById(R.id.bTMessage);
        Button btnTHomework = (Button) findViewById(R.id.bTHomework);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String username = intent.getStringExtra("username");
        tvUsername.setText(username);
        tvVersion.setText(type);
        btnQRintent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home.this, QR_Code.class);
                Home.this.startActivity(intent);
            }
        });
        btnTAttend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home.this, Teacher_attend.class);
                Home.this.startActivity(intent);
            }
        });
        btnTMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home.this, Teacher_message.class);
                Home.this.startActivity(intent);
            }
        });
        btnTHomework.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home.this, Teacher_homework.class);
                Home.this.startActivity(intent);
            }
        });
    }

}
