package com.example.hospitaladmin;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class staff_login extends AppCompatActivity {

    public static final String TAG = "HL";
    ArrayList<String> arrayListEmail = new ArrayList<>();
    ArrayList<String> arrayListPassword = new ArrayList<>();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRootRef = db.getReference();
    DatabaseReference userRef = myRootRef.child("staff_login");
    private EditText staffEmail, staffPassword;
    private Button loginbtn;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        staffEmail = findViewById(R.id.staffloginemail);
        staffPassword = findViewById(R.id.staffloginpassword);
        loginbtn = findViewById(R.id.staffLoginbtn2);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                Log.d(TAG, "auth" + arrayListEmail);
            }
        });


    }

    private void login() {

        final String myEmail = staffEmail.getText().toString();
        final String myPassword = staffPassword.getText().toString();

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String email = dataSnapshot.child("email").getValue().toString();
                String password = dataSnapshot.child("password").getValue().toString();
                if (myEmail.equals(email) && myPassword.equals(password)) {
                    Toast.makeText(staff_login.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(staff_login.this,StaffDashboard.class);
                    intent.putExtra("staffEmail",myEmail);
                    startActivity(intent);

                } else {
                    return;
//                    Toast.makeText(staff_login.this, "Login UnSuccess", Toast.LENGTH_SHORT).show();

                }


                arrayListEmail.add(email);
                arrayListPassword.add(password);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String email = dataSnapshot.child("email").getValue().toString();
//                String password = dataSnapshot.child("password").getValue().toString();
//
//                if(myEmail.equals(email)&&myPassword.equals(password)){
//                    Toast.makeText(staff_login.this,"Login Success",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(staff_login.this,"Login UnSuccess",Toast.LENGTH_SHORT).show();
//
//                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
