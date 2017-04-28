package com.example.chung.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class dummy1 extends AppCompatActivity {

    private ListView listView;
    private ListAdapter listAdapter;
    ArrayList<HashMap<String, String>> contactList;
    private String id1;
    private String id2;
    private String id3;
    private String id4;
    private String id5;
    private String id6;
    private String id7;
    private String id8;
    private String id9;
    private String id10;
    private String id11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy1);
        id1 = "1 Students are expected to behave with courtesy and consideration at all times.";
        id2 = "2 No jewellery allowed.";
        id3 = "3 Students must walk within the building at all times.";
        id4 = "4 Students should not bring to school anything to eat or drink (apart from packed lunch – no sweets or pop).  We encourage children to have water bottles in classrooms.";
        id5 = "5 The school expects students staying for school meals to behave in a quiet, orderly manner.  They should be polite to supervisory assistants at all times and remain on school premises after their meal.";
        id6 = "6 We expect all students to respect their own and other people property and make sure that school premises and its surroundings are kept in good condition.";
        id7 = "7 Any form of violence towards others e.g fighting, verbal violence (swearing, verbal bullying etc.), will lead to those involved being disiplined.";
        id8 = "8 Addressing each other and all teaching staff, support staff and visitors in a courteous manner using the prefix Mr, Mrs etc., where known.";
        id9 = "9 We recommend that students leave toys abd other personal belongings at home and do not bring them into school.  The school governors regret that they can not accept insurance responsibility for property lost at school.  Parents are requested to cover the contingency as part of household insurance.";
        id10 = "10 Students on educational visits are expected to show the same standard of behaviour as they would on school premises.";
        id11 = "11 Student should not access school via the main entrance unless they arrive after 9am registration time.";

        listView= (ListView) findViewById(R.id.listView);
        contactList = new ArrayList<>();
        HashMap<String, String> contact = new HashMap<>();
        contact.put("id1", id1);
        contact.put("id2", id2);
        contact.put("id3", id3);
        contact.put("id4", id4);
        contact.put("id5", id5);
        contact.put("id6", id6);
        contact.put("id7", id7);
        contact.put("id8", id8);
        contact.put("id9", id9);
        contact.put("id10", id10);
        contact.put("id11", id11);
        contactList.add(contact);
        ListAdapter adapter = new SimpleAdapter(
                dummy1.this, contactList,
                R.layout.dummy_item, new String[]{"id1","id2",
                "id3", "id4","id5", "id6", "id7", "id8", "id9", "id10","id11"}, new int[]{R.id.id1,
                R.id.id2, R.id.id3, R.id.id4, R.id.id5, R.id.id6, R.id.id7, R.id.id8, R.id.id9, R.id.id10, R.id.id11});

        listView.setAdapter(adapter);
    }
}