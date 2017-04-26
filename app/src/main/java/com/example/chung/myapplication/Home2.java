package com.example.chung.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Home2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        final TextView etUsername = (TextView)findViewById(R.id.tvUsername);
        final TextView etVersion = (TextView)findViewById(R.id.tvVersion);
        final TextView test = (TextView)findViewById(R.id.test);
        final TextView test2 = (TextView)findViewById(R.id.test2);
        Button btnPAttend = (Button) findViewById(R.id.bPAttend);
        Button btnPContact = (Button) findViewById(R.id.bPContact);
        Button btnPHandbook = (Button) findViewById(R.id.bPHandbook);
        Button btnPHomework = (Button) findViewById(R.id.bPHomework);
        Button btnPNotice = (Button) findViewById(R.id.bPNotice);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        etUsername.setText(username);
        etVersion.setText(type);
        test.setText(username);
        test2.setText(password);
        btnPAttend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Home2.this, Parent_attend.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                Home2.this.startActivity(intent);
            }
        });

        btnPContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home2.this, Parent_contact.class);
                Home2.this.startActivity(intent);
            }
        });
        btnPHandbook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home2.this, Parent_handbook.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                Home2.this.startActivity(intent);
            }
        });
        btnPHomework.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home2.this, Parent_homwork.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                Home2.this.startActivity(intent);
            }
        });
        btnPNotice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home2.this, Parent_notice.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                Home2.this.startActivity(intent);
            }
        });
        /*btnPNotification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Home2.this, Parent_notification.class);
                Home2.this.startActivity(intent);
            }
        });*/
    }
}
