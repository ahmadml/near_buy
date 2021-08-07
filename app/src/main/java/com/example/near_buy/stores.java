package com.example.near_buy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class stores extends AppCompatActivity {

    RecyclerView storeRecycler;
    String s1[], s2[];
    int store_icons[] = {R.drawable.davids_fishhouse,R.drawable.toys_are_mine,R.drawable.ovad_sabich,R.drawable.groceriesa,R.drawable.a_store ,R.drawable.chipotle};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        storeRecycler = findViewById(R.id.storeRecyclerView);

        s1 = getResources().getStringArray(R.array.store_names);
        s2 = getResources().getStringArray(R.array.store_description);

        MyAdapter myAdapter = new MyAdapter(this, s1 ,s2, store_icons);
        storeRecycler.setAdapter(myAdapter);
        storeRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}