package com.example.near_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginbtn,NewAccount, forgot;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    DatabaseReference myRef;
    String pId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.pass);
        mLoginbtn = findViewById(R.id.Login_button);
        mCreateBtn = findViewById(R.id.already_have_account);
        NewAccount = findViewById(R.id.create_new_account);
        forgot = findViewById(R.id.forgot_password);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        FirebaseUser User = fAuth.getCurrentUser();

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("password must be at least 6 characters.");
                }

                progressBar.setVisibility(View.VISIBLE);

                //Authentication
                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
//                            pId = fAuth.getUid();
//                            myRef = FirebaseDatabase.getInstance().getReference("users").child(pId);
//                            user user= new user();
//                            user.setName(User.getDisplayName());
//                            user.setId(pId);
//                            myRef.setValue(user);

                           // DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
                            //DatabaseReference uid = users.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            //DatabaseReference hb = uid.child("have_business");
                            //hb.addValueEventListener(new ValueEventListener() {

                                //@Override
                               // public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //long result = (long) snapshot.getValue();
                                   // if(result == 1){
                                       // startActivity(new Intent(getApplicationContext(),Seller_main_activity.class));
                                   // }
                                    //else{
                                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                   // }
                               // }

                              //  @Override
                               // public void onCancelled(@NonNull DatabaseError error) {

                                //}
                           // });

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else{
                            Toast.makeText(login.this," ERROR!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            //startActivity(new Intent(getApplicationContext(), login.class));
                        }
                    }
                });
            }


        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    Toast.makeText(login.this,"Please enter your email above and click again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this,"Email was sent to: " + email, Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(login.this," ERROR!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    try {
                                        TimeUnit.SECONDS.sleep(4);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    progressBar.setVisibility(View.INVISIBLE);
                                    //startActivity(new Intent(getApplicationContext(), login.class));
                                }

                            }
                        });
            }
        });


    }

    public void launchRegisterActivity(View view)
    {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

}