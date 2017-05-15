package com.example.chung.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

public class Teacher_hw_stud_select extends AppCompatActivity {

    private String TAG = Parent_attend.class.getSimpleName();
    private ProgressDialog pDialog;
    private Spinner lv;
    // URL to get contacts JSON
    ArrayList<HashMap<String, String>> contactList;
    private String username;
    private String password;
    private String url;
    private String selected;
    private String selectedClass;
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_hw_stud_select);
        Button btSelect = (Button)findViewById(R.id.btSelect);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password= intent.getStringExtra("password");
        url = "https://lenchan139.org/myWorks/fyp/android/attendDetails.php?"+"username="+username+"&password="+password;

        contactList = new ArrayList<>();

        lv = (Spinner) findViewById(R.id.spSpinner);
        new GetContacts().execute();
        lv.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selected = parent.getItemAtPosition(position).toString();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        btSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("selected",selected);
                int i = ordinalIndexOf(selected, "=", 2);
                int j = ordinalIndexOf(selected, ",", 2);
                selectedClass = selected.substring(i+1,j);
                intent.putExtra("selectedClass", selectedClass);
                intent.setClass(Teacher_hw_stud_select.this, Parent_homwork.class);
                Teacher_hw_stud_select.this.startActivity(intent);
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
            pDialog = new ProgressDialog(Teacher_hw_stud_select.this);
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

                        String student_id = c.getString("student_id");
                        String student_name = c.getString("student_name");
                        String student_class = c.getString("student_class");
                        // Phone node is JSON Object
                        /*JSONArray date = c.getJSONArray("student_attend");
                        for (int j = 0; j < date.length(); j++) {
                            JSONObject jsonObject = date.getJSONObject(j);

                            String dates = jsonObject.getString("read_time");*/
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("student_id", student_id);
                        contact.put("student_name", student_name);
                        contact.put("student_class", student_class);

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
            SpinnerAdapter adapter = new SimpleAdapter(
                    Teacher_hw_stud_select.this, contactList,
                    R.layout.hwstudlist_item, new String[]{"student_id","student_name", "student_class"}, new int[]{R.id.studId
                    ,R.id.studName, R.id.studClass});

            lv.setAdapter(adapter);
        }

    }
}