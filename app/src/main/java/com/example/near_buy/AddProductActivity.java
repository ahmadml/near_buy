package com.example.near_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

    private ImageView imageProduct;
    private EditText proName,proPrice,quantity,Description;
    private Button AddPro;

    private Uri image_uri;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private String productName,productPeice,productQuantity,productDecription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        imageProduct = findViewById(R.id.imageView3);
        proName = findViewById(R.id.titleEt);
        proPrice = findViewById(R.id.priceEt);
        quantity = findViewById(R.id.QuantatyEt);
        Description = findViewById(R.id.DescriptionEt);
        AddPro = findViewById(R.id.addProductBtn);
        firebaseAuth = FirebaseAuth.getInstance();


       /* imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        AddPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
    }

    private void inputData() {
        productName = proName.getText().toString().trim();
        productPeice = proPrice.getText().toString().trim();
        productQuantity = quantity.getText().toString().trim();
        productDecription = Description.getText().toString().trim();

        if(TextUtils.isEmpty(productName)){
            Toast.makeText(this,"The Name is Empty... ",Toast.LENGTH_LONG).show();
            proName.setError("Invalid Title");
            proName.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(productPeice)){
            Toast.makeText(this,"The Price is Empty... ",Toast.LENGTH_LONG).show();
            proPrice.setError("Invalid value");
            proPrice.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(productQuantity)){
            Toast.makeText(this,"The Quantity is Empty... ",Toast.LENGTH_LONG).show();
            quantity.setError("Invalid value");
            quantity.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(productDecription)){
            Toast.makeText(this,"The Description is Empty... ",Toast.LENGTH_LONG).show();
            Description.setError("Invalid value");
            Description.setFocusable(true);
            return;
        }
        
        addproduct();

    }

    private void addproduct() {
        //add data to db
       // progressDialog.setMessage("saving...");
       // progressDialog.show();

        final String timesTemp = ""+ System.currentTimeMillis();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("productId",""+timesTemp);
        hashMap.put("productName",productName);
        hashMap.put("productPrice",productPeice);
        hashMap.put("productQuantity",productQuantity);
        hashMap.put("Description",productDecription);
        hashMap.put("timeTemp",timesTemp);
        hashMap.put("uid",""+firebaseAuth.getUid());
        hashMap.put("Store", getIntent().getStringExtra("store_name"));


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(firebaseAuth.getUid()).child("Products").child(timesTemp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //added to db
                        //progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this, "The product is added ...", Toast.LENGTH_SHORT).show();
                        clearData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //progressDialog.dismiss();
                            Toast.makeText(AddProductActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearData() {
        // clear data after uploading
        proName.setText("");
        proPrice.setText("");
        quantity.setText("");
        Description.setText("");
    }
}