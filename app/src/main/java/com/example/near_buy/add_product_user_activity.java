package com.example.near_buy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class add_product_user_activity extends AppCompatActivity {
    private TextView title,quantity,final_price,dollar1,number;
    private ImageButton plus,minus,back;
    private Button add_to_cart;
    private String ProductName,price,product_quantity,id,description;
    private String store;
    private FirebaseAuth fAuth;
    private static int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_quantity);

        title = findViewById(R.id.titleTv);
        quantity = findViewById(R.id.pQuantity);
        //description = findViewById(R.id.descriptionTv);
        final_price = findViewById(R.id.finalPriceTv);
        dollar1 = findViewById(R.id.originalPriceTv);
        number = findViewById(R.id.quantityTv);


        plus = findViewById(R.id.incrementBtn);
        minus = findViewById(R.id.decrementBtn);
        back = findViewById(R.id.back);

        add_to_cart = findViewById(R.id.continueBtn);

        ProductName = getIntent().getStringExtra("ProductName");
        price = getIntent().getStringExtra("price");
        product_quantity = getIntent().getStringExtra("quantity");
        id = getIntent().getStringExtra("ProductId");
        description = getIntent().getStringExtra("Description");
        store = getIntent().getStringExtra("Store");
        fAuth = FirebaseAuth.getInstance();

        title.setText(ProductName);
        quantity.setText(product_quantity);
        dollar1.setText("$" + price);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                double calc = count*(Double.valueOf(price));
                dollar1.setText("$" + calc);
                number.setText(String.valueOf(count));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if(count < 1 ){
                    count = 1;
                }
                double calc = (Double.valueOf(price))*count;
                dollar1.setText("$" + calc);
                number.setText(String.valueOf(count));
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                AddToCart();
            }
        });
    }

    private void AddToCart() {
        if(fAuth.getUid() == null){
            return;
        }
        title.setFocusable(true);
        quantity.setFocusable(true);
        dollar1.setFocusable(true);


        final String timesTemp = ""+ System.currentTimeMillis();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("productId",""+id);
        hashMap.put("productName",""+ProductName);
        hashMap.put("productPrice",""+dollar1.getText());
        hashMap.put("productQuantity",""+count);
        hashMap.put("timeTemp",""+timesTemp);
        hashMap.put("uid",""+fAuth.getUid());
        hashMap.put("Description",""+description);
        hashMap.put("Store", ""+ store);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(Objects.requireNonNull(fAuth.getUid())).child("Cart").child(timesTemp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(add_product_user_activity.this, "The product is added ...", Toast.LENGTH_SHORT).show();
                        count = 1;
                        //onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_product_user_activity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        //onBackPressed();
    }

    private void clearData() {
        // clear data after uploading

    }


}
