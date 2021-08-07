package com.example.near_buy;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class shopDetailsActivity extends AppCompatActivity {
    private ImageView singleShop;
    private TextView shopName;
    private TextView shopPhone;
    private TextView shopEmail;
    private TextView IsOpen;
    private TextView shopAddress;
    private TextView filterProducts;
    private ImageButton callBtn;
    private ImageButton mapBtn;
    private ImageButton backBtn;
    private ImageButton cartBtn;
    private ImageButton filterBtn;
    private EditText searchProduct;
    private RecyclerView recycler;
    private String shopLatitude, shopLongitude;

    private FirebaseAuth fAuth;

    private String shopUid;

    private String shop_name, shop_email, shop_phone, shop_address,shop_open;

    private ProductsAdapter productsAdapter;

    private ArrayList<ModelProduct> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        singleShop = findViewById(R.id.shopSingle);
        shopName = findViewById(R.id.shopNameSingle);
        shopPhone = findViewById(R.id.shopPhoneSingle);
        shopEmail = findViewById(R.id.shopEmailSingle);
        IsOpen = findViewById(R.id.openSingle);
        shopAddress = findViewById(R.id.shopAddressSingle);
        callBtn = findViewById(R.id.callBtn);
        cartBtn = findViewById(R.id.cartBtn);
        mapBtn = findViewById(R.id.mapBtn);
        backBtn = findViewById(R.id.backBtn);
        filterBtn = findViewById(R.id.filterBtn);
        filterProducts = findViewById(R.id.filterProducts);
        searchProduct = findViewById(R.id.searchProduct);
        recycler = findViewById(R.id.showProducts);

        shopUid = getIntent().getStringExtra("shopUid");
        fAuth = FirebaseAuth.getInstance();
        loadInfo();
        loadShopDetails();
        loadShopProducts();

        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                productsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhone();
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        //TODO
        filterProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //TODO
    //need to add latitude etc.
    private void openMap() {
        String address ="https://maps.google.com/maps?safddr=" + shopLatitude + "," + shopLongitude + "&daddr=" + shopLatitude + "," +shopLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
        startActivity(intent);
    }

    private void dialPhone() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ Uri.encode(shop_phone))));
        Toast.makeText(this,""+ shop_phone,Toast.LENGTH_SHORT).show();
    }



    private void loadInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.orderByChild("Uid").equalTo(fAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String address = "" + ds.child("address").getValue();
                    String city = "" + ds.child("city").getValue();
                    long phone = (long) ds.child("phone").getValue();
                    String type = "" + ds.child("type").getValue();
                    String Uid = "" + ds.child("Uid").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadShopDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(shopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = "" + snapshot.child("name").getValue();
                shop_name = "" + snapshot.child("store").getValue();
                shop_email = "" + snapshot.child("email").getValue();
                shop_address = "" + snapshot.child("store_city").getValue();
                shop_phone = "" + snapshot.child("store_phone").getValue();
                shop_open = "" + snapshot.child("isOpen").getValue();
                shopLatitude =""+snapshot.child("latitude").getValue();
                shopLongitude =""+snapshot.child("longitude").getValue();

                shopAddress.setText(shop_address);
                shopEmail.setText(shop_email);
                shopName.setText(shop_name);
                shopPhone.setText(shop_phone);

                if(shop_open.equals("true")){
                    IsOpen.setText("Open");
                }
                else{
                    IsOpen.setText("Closed");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadShopProducts() {
        productList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(shopUid).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelProduct mp = ds.getValue(ModelProduct.class);
                    productList.add(mp);
                }
                productsAdapter = new ProductsAdapter(shopDetailsActivity.this,productList);
                recycler.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}