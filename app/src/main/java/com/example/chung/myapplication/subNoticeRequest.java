package com.example.chung.myapplication;

/**
 * Created by Chung on 17/4/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chung on 21/3/2017.
 */

public class subNoticeRequest extends StringRequest{
    private static  final String SUBNOTICE_REQUEST_URL = "https://lenchan139.org/myWorks/fyp/android/addEventAttend.php";

    private Map<String, String> params;

    public subNoticeRequest(String username, String password, String userid, String studid, String noticeid, Response.Listener<String> listener) {
        super(Method.POST, SUBNOTICE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("parent_id", userid);
        params.put("student_id", studid);
        params.put("event_id", noticeid);
    }
    public Map<String, String> getParams() {
        return params;
    }
}