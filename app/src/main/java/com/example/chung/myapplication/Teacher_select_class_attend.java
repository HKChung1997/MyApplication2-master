package com.example.chung.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Teacher_select_class_attend extends AppCompatActivity {

    private String TAG = Parent_attend.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private String username;
    private String password;
    private String Class;
    private String url;
    private String jsonStr;
    private String dates;
    private String selectedDate;
    private HashMap<String, String> contact2 = new HashMap<String, String>();
    // URL to get contacts JSON
    ArrayList<HashMap<String, String>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select_class_attend);
        contactList = new ArrayList<>();
        Intent intent = getIntent();
        Class = intent.getStringExtra("selected");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        selectedDate = intent.getStringExtra("Date");
        url = "https://lenchan139.org/myWorks/fyp/android/staff_only/attendDetails.php?" + "username=" + username + "&password=" + password + "&class=" + Class;
        TextView tvClass = (TextView) findViewById(R.id.tvClass);
        tvClass.setText(Class);
        lv = (ListView) findViewById(R.id.classAttenList);
            new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Teacher_select_class_attend.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null ) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("studArray");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String student_Id = c.getString("student_id");
                        String studName = c.getString("student_name");
                        // Phone node is JSON Object
                        JSONArray date = c.getJSONArray("student_attend");
                        for (int j = 0; j < date.length(); j++) {
                            JSONObject jsonObject = date.getJSONObject(j);

                            dates = jsonObject.getString("attend_date");
                                // tmp hash map for single contact
                                // adding each child node to HashMap key => value
                            HashMap<String,String> contact = new HashMap<String, String>();
                                contact.put("studName", studName);
                                contact.put("student_id", student_Id);
                                contact.put("attendDate", dates);
                            for (HashMap.Entry<String, String> entry :contact.entrySet()) {
                                if (entry.getValue().equals(selectedDate)) {
                                    contact2.put("studName", studName);
                                    contact2.put("student_id", student_Id);
                                    contact2.put("attendDate", dates);
                                    contactList.add(contact2);
                                }
                            }
                            // adding contact to contact list
                        }
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
                return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Teacher_select_class_attend.this, contactList,
                    R.layout.teacher_attenlist_item, new String[]{"studName","student_id",
                    "attendDate"}, new int[]{R.id.studName,
                     R.id.studentId, R.id.attenDate});
                lv.setAdapter(adapter);
        }

    }
}