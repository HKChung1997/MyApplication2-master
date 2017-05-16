package com.example.chung.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.zxing.Result;

import java.lang.reflect.Array;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QR_Code extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String username;
    private String password;
    private String userid;
    private String hwid;
    private int hw_id;
    private String strResult;
    private String studIdResult;
    private String studIdWrongResult;
    private  String Text;
    public void toastmessage() {
        Toast toast;
        toast = Toast.makeText(QR_Code.this, "Please Scan A Valid QR Code", Toast.LENGTH_LONG);
        toast.show();
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__code);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        userid = intent.getStringExtra("user_id");
        hwid = intent.getStringExtra("hwid");
        hw_id = Integer.valueOf(hwid);
        mScannerView = new ZXingScannerView(this);
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }


    public void onClick(View v) {
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Text = "ams_qr";
        strResult = result.getText();
        int y = ordinalIndexOf(strResult, ":", 1);
        studIdWrongResult = strResult.substring(0, y);
        if (studIdWrongResult.equals(Text)) {
            int j = ordinalIndexOf(strResult, "'", 3);
            int i = ordinalIndexOf(strResult, "'", 4);
            studIdResult = strResult.substring(j + 1, i);
            Intent intent = new Intent(QR_Code.this, QRCodeConfirm.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", userid);
            bundle.putString("hwId", hwid);
            bundle.putString("studId", studIdResult);
            bundle.putString("password", password);
            bundle.putString("username", username);
            intent.putExtras(bundle);
            QR_Code.this.startActivity(intent);
        } else if (!studIdWrongResult.equals(Text)){
            toastmessage();
        }
    }
}
