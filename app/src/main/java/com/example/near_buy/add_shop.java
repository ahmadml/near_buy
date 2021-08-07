package com.example.near_buy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class add_shop extends AppCompatActivity {
    EditText Sname,Slocation,Scategory,Description;
    Button AddStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shop_layout);
    }
}
