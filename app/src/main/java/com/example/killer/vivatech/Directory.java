package com.example.killer.vivatech;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class Directory extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<Person> personArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Directory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        personArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyle_view_directory);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setRecyclerViewData(); //adding data to array list
        adapter = new RecyclerAdapter(this, personArrayList);
        recyclerView.setAdapter(adapter);

    }

    private void setRecyclerViewData() {
        personArrayList.add(new Person("Office", "02506990999"));
        personArrayList.add(new Person("Office", "02506965620"));
        personArrayList.add(new Person("Office", "02506965628"));
        personArrayList.add(new Person("Telefax", "91-22-39167294"));



    }
}


