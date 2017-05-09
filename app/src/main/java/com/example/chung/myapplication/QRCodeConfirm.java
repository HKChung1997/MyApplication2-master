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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_confirm);
        final TextView tvUsername = (TextView) findViewById(R.id.QRusername);
        final TextView tvPassword = (TextView) findViewById(R.id.QRpassword);
        final TextView tvStudId = (TextView) findViewById(R.id.QRuserid);
        final TextView tvHwId = (TextView) findViewById(R.id.QRselected);
        final TextView tvUserid = (TextView) findViewById(R.id.QRstaff);
        final Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Intent intent = getIntent();
        final Bundle bundle = this.getIntent().getExtras();
        final String tvusername = bundle.getString("username");
        final String tvpassword = bundle.getString("password");
        final String tvhwId = bundle.getString("hwId");
        final String tvstudId = bundle.getString("studId");
        final String tvstaffId = bundle.getString("userId");
        tvUsername.setText(tvusername);
        tvUserid.setText(tvstaffId);
        tvPassword.setText(tvpassword);
        tvStudId.setText(tvstudId);
        tvHwId.setText(tvhwId);
        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String username = tvUsername.getText().toString();
                final String password = tvPassword.getText().toString();
                final String c = tvUserid.getText().toString();
                final String d = tvStudId.getText().toString();
                final String e = tvHwId.getText().toString();
                int staffId = Integer.valueOf(c);
                int studId = Integer.valueOf(d);
                int hwId = Integer.valueOf(e);
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
    }
}
