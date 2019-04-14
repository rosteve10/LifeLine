package com.example.hospitaladmin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientProfile extends AppCompatActivity {

    FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = mAuth.getReference();
    DatabaseReference userRef = rootRef.child("profile");

    private TextView bloodGroup,name, gender,address,state,country,pincode,email,mobile;
    private Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);


        bloodGroup = findViewById(R.id.patientBloodGroup);
        name = findViewById(R.id.patientName);
        gender = findViewById(R.id.patientGender);
        address = findViewById(R.id.patientaddress);
        state = findViewById(R.id.patientState);
        country = findViewById(R.id.patientCountry);
        pincode = findViewById(R.id.patientPincode);
        email = findViewById(R.id.patientEmail);
        mobile = findViewById(R.id.patientMobile);
        call = findViewById(R.id.patientbtnCall);




        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                String myBloodGroup = dataSnapshot.child("bloodGroup").getValue().toString();
                String myName  = dataSnapshot.child("firstName").getValue().toString()+" "+dataSnapshot.child("lastName").getValue().toString();
                String myGender = dataSnapshot.child("gender").getValue().toString();
                String myAddress = dataSnapshot.child("address").getValue().toString();
                String myState = dataSnapshot.child("state").getValue().toString();
                String myCountry = dataSnapshot.child("country").getValue().toString();
                String myPincode = dataSnapshot.child("pincode").getValue().toString();
                String myEmail = dataSnapshot.child("email").getValue().toString();
                String myMobile = dataSnapshot.child("mobile").getValue().toString();
                String checkName = getIntent().getStringExtra("name");

                if(String.valueOf(checkName).toLowerCase().equals(String.valueOf(myName).toLowerCase())) {

                    bloodGroup.setText(myBloodGroup);
                    name.setText(myName);
                    gender.setText(myGender);
                    address.setText(myAddress);
                    state.setText(myState);
                    country.setText(myCountry);
                    pincode.setText(myPincode);
                    email.setText(myEmail);
                    mobile.setText(myMobile);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
}
