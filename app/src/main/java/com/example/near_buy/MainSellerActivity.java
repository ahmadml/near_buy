package com.example.near_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainSellerActivity extends AppCompatActivity {
    private ImageButton addprodactBtn, remove;
    private TextView nameT;
    private LinearLayout l;
    private FirebaseAuth firebaseAuth;
    ArrayList<ModelProduct> cartList;
    ArrayList<ModelProduct> deleteList;
    private ProductsAdapterSeller ProductsAdapterSeller;
    private RecyclerView recycle;
    DatabaseReference db;
    public Dialog MyDialog;
    TextView tvDeleteDiaog;
    public Button Yes, No;
    public static Context contextOfApplication;
    String store_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main_seller);

        tvDeleteDiaog = findViewById(R.id.txt_dia);
        deleteList = new ArrayList<>();
        db= FirebaseDatabase.getInstance().getReference("Products");

        nameT = findViewById(R.id.nameTv);
        addprodactBtn = findViewById(R.id.addProductBtn);
        remove = findViewById(R.id.remove_prod2);
        l = findViewById(R.id.linear2);
        firebaseAuth = FirebaseAuth.getInstance();
        loadMyInfo();
        addprodactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSellerActivity.this, AddProductActivity.class);
                intent.putExtra("store_name", store_name);
                startActivity(intent);
//              startActivity(new Intent(MainSellerActivity.this, AddProductActivity.class));
            }
        });

        loadProd();
        recycle = (RecyclerView) findViewById(R.id.showProducts3);
    }

    @Override
    protected void onStart(){
        super.onStart();
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                deleteList.clear();
//                for(DataSnapshot ds: snapshot.getChildren()){
//                    ModelProduct mp = ds.getValue(ModelProduct.class);
//                    deleteList.add(mp);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        recycle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void loadProd() {
        cartList = new ArrayList<>();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = currentUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(currentId).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelProduct mp = ds.getValue(ModelProduct.class);
                    cartList.add(mp);
                }
                ProductsAdapterSeller = new ProductsAdapterSeller(MainSellerActivity.this, cartList);
                recycle.setAdapter(ProductsAdapterSeller);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadMyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue();
                    nameT.setText(name);
                    store_name = "" + ds.child("store").getValue();

                    try {

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }


//    l.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//            String currentId = currentUser.getUid();
//
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
//            ref.child(currentId).child("Products").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    deleteList.clear();
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        ModelProduct mp = ds.getValue(ModelProduct.class);
//                        cartList.add(mp);
//                    }
//                    ProductsAdapterCart = new ProductsAdapterCart(getApplicationContext(), cartList);
//                    recycle.setAdapter(ProductsAdapterCart);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//    });
}