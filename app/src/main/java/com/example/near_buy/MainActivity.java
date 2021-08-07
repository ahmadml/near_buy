package com.example.near_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.homeFrag);
        topLevelDestinations.add(R.id.storeFrag);
        topLevelDestinations.add(R.id.cartFrag);
        topLevelDestinations.add(R.id.userFrag);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }




    public void launchStoresActivity(View view) {
        Intent intent = new Intent(this, stores.class);
        startActivity(intent);

    }
    public void launchItemsActivity(View view) {
        Intent intent = new Intent(this, items.class);
        startActivity(intent);
    }

    public void LaunchRegister(View view) {
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            Toast.makeText(MainActivity.this,"You are already logged in",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
    public void LaunchLogin(View view) {
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            Toast.makeText(MainActivity.this,"You are already logged in",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void logout(View view){
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() == null){
            Toast.makeText(MainActivity.this,"you are not logged in",Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this,"logged out successfully",Toast.LENGTH_SHORT).show();
    }

    public void switch_user(View view) {
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() == null)
        {
            Toast.makeText(MainActivity.this,"You are not logged in",Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this,"change user",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void seller_details(View view) {
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() == null)
        {
            Toast.makeText(MainActivity.this,"You are not logged in",Toast.LENGTH_SHORT).show();
            return;
        }
        String fa = fAuth.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(fAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ds = snapshot.child("type").getValue().toString();
                if(ds.equals("seller")){
                    Toast.makeText(MainActivity.this,"start new activity seller",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainSellerActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"You are not a seller",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"canceled",Toast.LENGTH_SHORT).show();
            }
        });
    }



}