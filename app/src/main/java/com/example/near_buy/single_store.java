package com.example.near_buy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class single_store extends AppCompatActivity {
    ImageView mainImageView;
    TextView title,description;

    String data1,data2;
    int myImage;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_store);

        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.store_title);
        description = findViewById(R.id.store_description);

        getData();
        setData();
    }

    private void getData(){
//        if(getIntent().hasExtra("myImage") && getIntent().hasExtra(("data1")) && getIntent().hasExtra("data2"))
        if(getIntent().hasExtra("myImage"))
        {
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage", 1);
        }
        else
         {
            Toast.makeText(this, "no data.", Toast.LENGTH_SHORT).show();
         }
    }

    private void setData()
    {
        title.setText(data1);
        description.setText(data2);
        mainImageView.setImageResource(myImage);
    }
}