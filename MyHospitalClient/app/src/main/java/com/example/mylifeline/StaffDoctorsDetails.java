package com.example.mylifeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class StaffDoctorsDetails extends AppCompatActivity {


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    private ImageView staffImage;
    private TextView StaffName, StaffAddress, StaffPhone, StaffHours, StaffSpeciality, StaffDegrees, StaffCabin;
    private Button staffBook, staffCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_doctors_details);
        final Intent intent = getIntent();
        String staffMobile = intent.getStringExtra("StaffMobile");
        String hospitalName = intent.getStringExtra("hospitalName");

        DatabaseReference userRef = rootRef.child("hospital_staff_lists").child(hospitalName).child(staffMobile);

        staffImage = findViewById(R.id.staffImage);
        StaffName = findViewById(R.id.staffName);
        StaffAddress = findViewById(R.id.StaffAddress);
        StaffPhone = findViewById(R.id.StaffPhone);
        StaffHours = findViewById(R.id.StaffHours);
        staffBook = findViewById(R.id.StaffBookBtn);
        staffCall = findViewById(R.id.StaffCallBtn);
        StaffSpeciality = findViewById(R.id.StaffSpeciality);
        StaffDegrees = findViewById(R.id.StaffDegrees);
        StaffCabin = findViewById(R.id.StaffCabin);

        staffBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StaffDoctorsDetails.this, Appointment.class);

                startActivity(intent1);
            }
        });


        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String url = dataSnapshot.child("imageUrl").getValue().toString();
                String staffName = dataSnapshot.child("staffName").getValue().toString();
//                String staffAdddress = dataSnapshot.child("staffAddress").getValue().toString();
                String staffPhone = dataSnapshot.child("staffMobile").getValue().toString();
//                String staffHours = dataSnapshot.child("staffWorking").getValue().toString();

                String staffSpeciality = dataSnapshot.child("staffExpertise").getValue().toString();
                String staffDegrees = dataSnapshot.child("staffDegrees").getValue().toString();
                String staffCabin = dataSnapshot.child("staffCabin").getValue().toString();

                Picasso.get().load(url).into(staffImage);
                StaffName.setText(staffName);
//                StaffAddress.setText(staffAdddress);
                StaffPhone.setText(staffPhone);
                StaffSpeciality.setText(staffSpeciality);
                StaffCabin.setText(staffCabin);
                StaffDegrees.setText(staffDegrees);

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
