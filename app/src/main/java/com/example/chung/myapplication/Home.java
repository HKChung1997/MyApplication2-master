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
        final TextView tvId = (TextView)findViewById(R.id.tvId);
        Button btnHWAdd = (Button)findViewById(R.id.bHwAdd) ;
        Button btnHWCHK = (Button)findViewById(R.id.bHwCHK) ;
        Button btnQRintent = (Button) findViewById(R.id.bQRintent);
        Button btnTAttend = (Button) findViewById(R.id.bTAttend);
        //Button btnTHomework = (Button) findViewById(R.id.bTHomework);
        Intent intent = getIntent();
        final Bundle bundle = this.getIntent().getExtras();
        final int userId = bundle.getInt("user_id");
        String type = intent.getStringExtra("type");
        tvVersion.setText(type);
        tvId.setText("" + userId);
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        tvUsername.setText(username);
        final String user_id = tvId.getText().toString();
        btnQRintent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("user_id", user_id);
                intent.setClass(Home.this, Teacher_select_hwQR_code.class);
                Home.this.startActivity(intent);
            }
        });
        btnTAttend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("user_id", user_id);
                intent.setClass(Home.this, Teacher_attend.class);
                Home.this.startActivity(intent);
            }
        });
        btnHWAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("user_id", user_id);
                intent.setClass(Home.this, Teacher_hw_add.class);
                Home.this.startActivity(intent);
            }
        });
        btnHWCHK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("user_id", user_id);
                intent.setClass(Home.this, Teacher_hw_chk.class);
                Home.this.startActivity(intent);
            }
        });
        /*btnTMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.setClass(Home.this, Teacher_message.class);
                Home.this.startActivity(intent);
            }
        });*/
        /*btnTHomework.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.setClass(Home.this, Teacher_homework.class);
                Home.this.startActivity(intent);
            }
        });*/
    }

}
