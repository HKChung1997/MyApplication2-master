package com.example.chung.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Parent_notification extends AppCompatActivity {

    private ListView listView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_notification);

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"PeterChan@gmail.com", "MaryLee@gmail.com", "LilyCheung@gmail.com", "JohnLaw@gmail.com"});
        listView.setAdapter(listAdapter);
    }
}