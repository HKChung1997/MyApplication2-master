package com.example.chung.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class Parent_homwork extends AppCompatActivity {

    private String TAG = Parent_attend.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get contacts JSON
    ArrayList<HashMap<String, String>> contactList;
    //String url2 = url + str + str2;
    //String str = str.getStringExtra("username");
    //String str2 = str2.getStringExtra("password");
    private String username;
    private String password;
    private String url;
    private String selected;
    private String selectedClass;
    private HashMap<String, String> contact2 = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_homwork);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        selected = intent.getStringExtra("selected");
        selectedClass = intent.getStringExtra("selectedClass");
        url = "https://lenchan139.org/myWorks/fyp/android/homeworkList.php?"+"username="+username+"&password="+password;
        TextView test = (TextView) findViewById(R.id.test);
        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.hwList);
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
            pDialog = new ProgressDialog(Parent_homwork.this);
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
                    JSONArray contacts = jsonObj.getJSONArray("homeworkList");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String Class = c.getString("hw_class");
                        String Subject = c.getString("subject");
                        String Title = c.getString("title");
                        String Description = c.getString("description");
                        String postDate = c.getString("post_date");
                        String deadLine = c.getString("deadline");
                        // Phone node is JSON Object
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("Class", Class);
                        contact.put("Subject", Subject);
                        contact.put("Title", Title);
                        contact.put("Description", Description);
                        contact.put("postDate", postDate);
                        contact.put("deadLine", deadLine);
                        for (HashMap.Entry<String, String> entry : contact.entrySet()) {
                            if (entry.getValue().equals(selectedClass)) {
                                contact2.put("Class", Class);
                                contact2.put("Subject", Subject);
                                contact2.put("Title", Title);
                                contact2.put("Description", Description);
                                contact2.put("postDate", postDate);
                                contact2.put("deadLine", deadLine);
                                // adding contact to contact list
                                contactList.add(contact);
                            }
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
                    Parent_homwork.this, contactList,
                    R.layout.hwlist_item, new String[]{"Class","Subject",
                    "Title","Description","postDate","deadLine"}, new int[]{R.id.Class,
                    R.id.Subject,R.id.Title,R.id.Description, R.id.postDate, R.id.deadLine});

            lv.setAdapter(adapter);
        }

    }
}