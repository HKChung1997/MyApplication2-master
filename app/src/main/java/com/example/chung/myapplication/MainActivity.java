package com.example.chung.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.chung.myapplication.Forgot;
import com.example.chung.myapplication.LoginRequest;
import com.example.chung.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnLogin = (Button) findViewById(R.id.bLogin);
        Button btnForgot = (Button) findViewById(R.id.bForget);


        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , Forgot.class);
                MainActivity.this.startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("isVaild");
                            String username = jsonResponse.getString("username");
                            String type = jsonResponse.getString("type");
                            String parent = new String("parent");
                            String officer = new String("officer");
                            if (success && type.equals(officer)) {
                                Intent intent = new Intent(MainActivity.this, Home.class);
                                intent.putExtra("type", type);
                                intent.putExtra("password", password);
                                intent.putExtra("username", username);
                                MainActivity.this.startActivity(intent);
                            }  else if (success && type.equals(parent)){
                                Intent intent2 = new Intent(MainActivity.this, Home2.class);
                                intent2.putExtra("type", type);
                                intent2.putExtra("username", username);
                                intent2.putExtra("password", password);
                                MainActivity.this.startActivity(intent2);
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
