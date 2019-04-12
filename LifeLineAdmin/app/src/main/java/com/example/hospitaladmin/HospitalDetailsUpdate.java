package com.example.hospitaladmin;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HospitalDetailsUpdate extends AppCompatActivity {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = db.getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();

    private TextView hospitalName,hospitalAddress,hospitalContact,hospitalOwner,hospitalCity,hospitalState,hospitalWebsite,hospitalRating,hospitalOpeningHours;
    private Button update;
    private DatabaseReference userRef = rootRef.child("hospital_lists").child(firebaseUser.getUid()).child("hospital_admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details_update);


        hospitalName = findViewById(R.id.hospitalName);
        hospitalAddress = findViewById(R.id.hospitalAddress);
        hospitalContact = findViewById(R.id.hospitalContact);
        hospitalOwner = findViewById(R.id.hospitalOwner);
        hospitalCity = findViewById(R.id.hospitalCity);
        hospitalState = findViewById(R.id.hospitalState);
        hospitalWebsite = findViewById(R.id.hospitalWebsite);
        hospitalRating = findViewById(R.id.hospitalRating);
        hospitalOpeningHours = findViewById(R.id.hospitalOpeningHours);
        update = findViewById(R.id.hospitalbtnUpdate);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalDetailsUpdate.this,hospital_registration2.class);
                startActivity(intent);

            }
        });

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            String myHospitalName = dataSnapshot.child("hospitalName").getValue().toString();

                hospitalName.setText(myHospitalName);
                hospitalAddress.setText(dataSnapshot.child("hospitalAddress").getValue().toString());
                    hospitalContact.setText(dataSnapshot.child("hospitalContact").getValue().toString());
                    hospitalOwner.setText(dataSnapshot.child("hospitalOrg").getValue().toString());
                    hospitalCity.setText(dataSnapshot.child("hospitalCity").getValue().toString());
                  hospitalState.setText(dataSnapshot.child("hospitalState").getValue().toString());
                    hospitalWebsite.setText(dataSnapshot.child("hospitalWebsite").getValue().toString());
                    hospitalRating.setText(dataSnapshot.child("hospitalRating").getValue().toString());
                    hospitalOpeningHours.setText(dataSnapshot.child("hospitalHours").getValue().toString());

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
