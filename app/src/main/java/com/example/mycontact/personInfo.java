package com.example.mycontact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class personInfo extends AppCompatActivity {

    private TextView textview_name;
    private TextView textview_number;
    private TextView textview_group;
    private TextView textview_music;
    private TextView textview_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        textview_name=(TextView) findViewById(R.id.peron_name);
        textview_number=(TextView) findViewById(R.id.person_number);
        textview_group=(TextView) findViewById(R.id.person_group);
        textview_music=(TextView) findViewById(R.id.person_music);
        textview_name.setText(getIntent().getStringExtra("name"));
        textview_number.setText(getIntent().getStringExtra("number"));
        textview_group.setText(getIntent().getStringExtra("group"));
        textview_music.setText(getIntent().getStringExtra("music"));





    }
}