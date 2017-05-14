package com.example.chung.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView;
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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parent_notice extends AppCompatActivity {

    private String TAG = Parent_attend.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get contacts JSON
    ArrayList<HashMap<String, String>> contactList;
    private String username;
    private String password;
    private String user_id;
    private String url;
    private ListAdapter adapter;
    private HashMap<String, String> contact = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_notice);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        user_id = intent.getStringExtra("user_id");
        url = "https://lenchan139.org/myWorks/fyp/android/noticeList.php?" + "username=" + username + "&password=" + password;


        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.noticeList);
        new GetContacts().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                String value = lv.getItemAtPosition(position).toString();
                Intent intent = new Intent();
                intent.setClass(Parent_notice.this, notice_item.class);
                intent.putExtra("test", value);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("user_id", user_id);
                Parent_notice.this.startActivity(intent);
            }
        });
    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Parent_notice.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("noticeList");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("notice_id");
                        String title = c.getString("title");
                        String description = c.getString("description");
                        String post_date = c.getString("post_date");
                        String begin_time = c.getString("begin_time");
                        String end_time = c.getString("end_time");
                        String submit_deadline = c.getString("submit_deadline");
                        // Phone node is JSON Object
                        /*JSONArray date = c.getJSONArray("student_attend");
                        for (int j = 0; j < date.length(); j++) {
                            JSONObject jsonObject = date.getJSONObject(j);

                            String dates = jsonObject.getString("read_time");*/
                        // tmp hash map for single contact
                        contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("title", title);
                        contact.put("description", description);
                        contact.put("post_date", post_date);
                        contact.put("begin_time", begin_time);
                        contact.put("end_time", end_time);
                        contact.put("submit_deadline", submit_deadline);

                        // adding contact to contact list
                        contactList.add(contact);
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
            adapter = new SimpleAdapter(
                    Parent_notice.this, contactList,
                    R.layout.activity_notice_item, new String[]{"id","title","description", "post_date", "begin_time", "end_time", "submit_deadline"}, new int[]{R.id.noticeId
                    ,R.id.noticeTitle, R.id.noticeDescription, R.id.noticePDate, R.id.noticeBT, R.id.noticeET, R.id.noticeDL});

            lv.setAdapter(adapter);
        }

    }
}