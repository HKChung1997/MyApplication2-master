package com.example.chung.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;

public class Teacher_hw_chk extends AppCompatActivity {

    private String TAG = Parent_attend.class.getSimpleName();
    private ProgressDialog pDialog;
    private Spinner lv;
    // URL to get contacts JSON
    ArrayList<HashMap<String, String>> contactList;
    private String username;
    private String password;
    private String userId;
    private String url;
    private String selected;
    private String hwid;
    private String hwclass;

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_hw_chk);
        Button btCheck = (Button)findViewById(R.id.btCheck);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password= intent.getStringExtra("password");
        userId = intent.getStringExtra("user_id");
        url = "https://lenchan139.org/myWorks/fyp/android/homeworkList.php?"+"username="+username+"&password="+password;


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

        btCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("selected",selected);
                int i = ordinalIndexOf(selected, "{", 1);
                int j = ordinalIndexOf(selected, ",", 1);
                int x = ordinalIndexOf(selected, ",", 3);
                int y = ordinalIndexOf(selected, "}", 1);
                hwclass = selected.substring(i+10,j);
                hwid = selected.substring(x+8,y);
                intent.putExtra("hwclass", hwclass);
                intent.putExtra("hwid", hwid);
                intent.setClass(Teacher_hw_chk.this, Teacher_hw_selected_chk.class);
                Teacher_hw_chk.this.startActivity(intent);
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
            pDialog = new ProgressDialog(Teacher_hw_chk.this);
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

                        String hwId = c.getString("hw_id");
                        String title = c.getString("title");
                        String hw_class = c.getString("hw_class");
                        String deadline = c.getString("deadline");
                        // Phone node is JSON Object
                        /*JSONArray date = c.getJSONArray("student_attend");
                        for (int j = 0; j < date.length(); j++) {
                            JSONObject jsonObject = date.getJSONObject(j);

                            String dates = jsonObject.getString("read_time");*/
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("hw_id", hwId);
                        contact.put("title", title);
                        contact.put("deadline", deadline);
                        contact.put("hw_class", hw_class);


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
                    Teacher_hw_chk.this, contactList,
                    R.layout.hw_hwchkspinner_item, new String[]{"hw_id","title", "deadline", "hw_class"}, new int[]{R.id.hwId
                    ,R.id.hwTitle, R.id.hwDate, R.id.hwClass});

            lv.setAdapter(adapter);
        }

    }
}