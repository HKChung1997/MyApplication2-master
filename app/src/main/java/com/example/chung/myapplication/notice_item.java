package com.example.chung.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class notice_item extends AppCompatActivity {
    private String username;
    private String password;
    private String studid;
    private String test;
    private String userid;
    private String noticeid;
    private String title = "hi";
    private String message = "hi";

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }


    public void AlertDialog() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Sign notice?")
                .setMessage("Are you sure you have read all the content of this notice?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String loginCHK = jsonResponse.getString("status");
                                    String isSuccess = new String("success");
                                    if (loginCHK.equals(isSuccess)) {
                                        Toast toast = Toast.makeText(notice_item.this, "Submit Success", Toast.LENGTH_LONG);
                                        toast.show();
                                    } else{
                                        Toast toast = Toast.makeText(notice_item.this, "Submit Fail", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        subNoticeRequest subNoticeRequest= new subNoticeRequest(username, password, userid, studid, noticeid, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(notice_item.this);
                        queue.add(subNoticeRequest);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_notice);
        TextView tvtest = (TextView)findViewById(R.id.tvNoticeId);
        TextView tvusername = (TextView)findViewById(R.id.tvUsername);
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit) ;
        int zero = 0;
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        userid = intent.getStringExtra("user_id");
        studid = Integer.toString(zero);
        test = intent.getStringExtra("test");
        int j = ordinalIndexOf(test, "=", 2);
        int i = ordinalIndexOf(test, ",", 2);
        noticeid = test.substring(j+1, i);
        tvtest.setText(noticeid);
        btnSubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                AlertDialog();
            }
        });

    }
}