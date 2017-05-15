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

public class addhwRequest extends StringRequest{
    private static  final String ADDHW_REQUEST_URL = "https://lenchan139.org/myWorks/fyp/android/staff_only/addHomework.php";

    private Map<String, String> params;

    public addhwRequest(String username, String password, String addclass, String subject, String title,String description,  String deadline, Response.Listener<String> listener) {
        super(Method.POST, ADDHW_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("class", addclass);
        params.put("subject", subject);
        params.put("title", title);
        params.put("description", description);
        params.put("deadline", deadline);
    }
    public Map<String, String> getParams() {
        return params;
    }
}