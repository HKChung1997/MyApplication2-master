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

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private String isSuccess;
    private String isFail;
    private String loginCHK;
    private String type;
    private String username;
    private String password;
    private String parent;
    private String staff;
    private Integer userId;
    public void toastmessage() {
        Toast toast;
        toast = Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_LONG);
        toast.show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnLogin = (Button) findViewById(R.id.bLogin);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            type = jsonResponse.getString("type");
                            loginCHK = jsonResponse.getString("isVaild");
                            isSuccess = new String("true");
                            isFail = new String("false");
                            parent = new String("parent");
                            staff = new String("staff");
                            if (loginCHK.equals(isSuccess) && type.equals(staff)) {
                                userId = jsonResponse.getInt("user_id");
                                username = jsonResponse.getString("username");
                                Intent intent = new Intent(MainActivity.this, Home.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("user_id", userId);
                                intent.putExtras(bundle);
                                intent.putExtra("type", type);
                                intent.putExtra("password", password);
                                intent.putExtra("username", username);
                                MainActivity.this.startActivity(intent);
                            } else if (loginCHK.equals(isSuccess) && type.equals(parent)) {
                                userId = jsonResponse.getInt("user_id");
                                username = jsonResponse.getString("username");
                                Intent intent = new Intent(MainActivity.this, Home2.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("user_id", userId);
                                intent.putExtras(bundle);
                                intent.putExtra("type", type);
                                intent.putExtra("username", username);
                                intent.putExtra("password", password);
                                MainActivity.this.startActivity(intent);
                            } else if (loginCHK.equals(isFail)){
                                toastmessage();
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
