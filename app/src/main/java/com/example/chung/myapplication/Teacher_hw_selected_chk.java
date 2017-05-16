package com.example.chung.myapplication;

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

public class Teacher_hw_selected_chk extends AppCompatActivity {

    private String TAG = Parent_attend.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private String username;
    private String password;
    private String Class;
    private String hwid;
    private HashMap<String, String> contact2 = new HashMap<String, String>();
    private String url;
    //Button Event = (Button)findViewById(R.id.btEvent);
    //final TextView username = (TextView)findViewById(R.id.test);
    //final TextView password = (TextView)findViewById(R.id.test2);
    // URL to get contacts JSON
    ArrayList<HashMap<String, String>> contactList;
    //String username = getIntent().getExtras().getString("test").toString();
    // String password = getIntent().getExtras().getString("test2").toString();
    //username.setText(str);
    //password.setText(str2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_hw_selected_chk);
        contactList = new ArrayList<>();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password= intent.getStringExtra("password");
        Class= intent.getStringExtra("hwclass");
        hwid= intent.getStringExtra("hwid");
        url = "https://lenchan139.org/myWorks/fyp/android/staff_only/hwDetails.php?"+"username="+username+"&password="+password+"&class="+Class+"&hw_id="+hwid;
        //Intent intent = getIntent();
        //final String str = intent.getStringExtra("test");
        //final String str2 = intent.getStringExtra("test2");
        //username.setText(str);
        //password.setText(str2);
        lv = (ListView) findViewById(R.id.chkList);
        new GetContacts().execute();
        /*Event.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setClass(Parent_eventAttend.this, Parent_handbook.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                Parent_eventAttend.this.startActivity(intent);
            }
        });*/
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Teacher_hw_selected_chk.this);
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
                    JSONArray contacts = jsonObj.getJSONArray("studArray");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String studName = c.getString("student_name");
                        String studClass = c.getString("student_class");
                        String studId = c.getString("student_id");
                        // Phone node is JSON Object
                        JSONArray date = c.getJSONArray("stud_hw_record");
                        for (int j = 0; j < date.length(); j++) {
                            JSONObject jsonObject = date.getJSONObject(j);

                            String recordId = jsonObject.getString("record_id");
                            String hwId = jsonObject.getString("hw_id");
                            String dates = jsonObject.getString("read_time");
                            // tmp hash map for single contact
                            HashMap<String, String> contact = new HashMap<>();

                            // adding each child node to HashMap key => value
                            contact.put("hw_id", hwId);
                            // adding contact to contact list
                            for (HashMap.Entry<String, String> entry :contact.entrySet()) {
                                if (entry.getValue().equals(hwid)) {
                                    contact.put("studName", studName);
                                    contact.put("studClass", studClass);
                                    contact.put("student_id", studId);
                                    contact.put("record_id", recordId);
                                    contact.put("hw_id", hwId);
                                    contact.put("read_time", dates);
                                    contactList.add(contact);
                                }
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
                    Teacher_hw_selected_chk.this, contactList,
                    R.layout.hwchklist_item, new String[]{"studName","studClass","student_id",
                    "record_id", "hw_id", "read_time"}, new int[]{R.id.studName,
                    R.id.studClass, R.id.studentId, R.id.Record, R.id.HWid, R.id.Handoutdate});

            lv.setAdapter(adapter);
        }

    }
}