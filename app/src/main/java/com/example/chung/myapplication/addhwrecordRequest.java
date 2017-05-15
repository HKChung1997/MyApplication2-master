package com.example.chung.myapplication;

/**
 * Created by Chung on 17/4/2017.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chung on 21/3/2017.
 */

public class addhwrecordRequest extends StringRequest{
    private static  final String ADDHWRECORD_REQUEST_URL = "https://lenchan139.org/myWorks/fyp/android/staff_only/addHwChecker.php";

    private Map<String, String> params;

    public addhwrecordRequest(String username, String password, int hwId, int studId, int staffId, Response.Listener<String> listener) {
        super(Request.Method.POST, ADDHWRECORD_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("hw_id", hwId + "");
        params.put("stud_id", studId + "");
        params.put("staff_id", staffId + "");
    }
    public Map<String, String> getParams() {
        return params;
    }
}