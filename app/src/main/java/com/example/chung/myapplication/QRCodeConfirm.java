package com.example.chung.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class QRCodeConfirm extends AppCompatActivity {
    private String username;
    private String password;
    private String hw_Id;
    private String stud_Id;
    private String staff_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_confirm);
        final Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Intent intent = getIntent();
        final Bundle bundle = this.getIntent().getExtras();
        username= bundle.getString("username");
        password = bundle.getString("password");
        hw_Id= bundle.getString("hwId");
        stud_Id= bundle.getString("studId");
        staff_Id= bundle.getString("userId");
        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int staffId = Integer.valueOf(staff_Id);
                int studId = Integer.valueOf(stud_Id);
                int hwId = Integer.valueOf(hw_Id);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String loginCHK = jsonResponse.getString("isVaild");
                            String isSuccess = new String("true");
                            if (loginCHK.equals(isSuccess)) {
                                Toast toast = Toast.makeText(QRCodeConfirm.this, "Add Record Success", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(QRCodeConfirm.this, "Add Record Fail", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                addhwrecordRequest addhwrecordRequest = new addhwrecordRequest(username, password, hwId, studId, staffId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(QRCodeConfirm.this);
                queue.add(addhwrecordRequest);
            }
        });
        TextView tvStudid = (TextView)findViewById(R.id.QRstudId);
        TextView tvHWid = (TextView)findViewById(R.id.QRhwId);
        tvHWid.setText(hw_Id);
        tvStudid.setText(stud_Id);
    }
}
