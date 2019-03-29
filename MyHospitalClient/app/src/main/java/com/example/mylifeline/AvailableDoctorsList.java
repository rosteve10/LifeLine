package com.example.mylifeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AvailableDoctorsList extends AppCompatActivity {


    final ArrayList<StaffListItems> staffListItems = new ArrayList<>();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRootRef = db.getReference();
    String TAG = "HL";
    DatabaseReference userRef = myRootRef.child("hospital_staff_lists");
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_doctors_list);
        String Tag = "HL";
        Intent intent = getIntent();
        final String hospitalName = intent.getStringExtra("hospitalName");
        String hospitalMobile = intent.getStringExtra("hospitalMobile");
        Log.d(TAG, "test" + hospitalMobile + hospitalName);


        userRef.child(hospitalName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String staffName = dataSnapshot.child("details").child("staffName").getValue().toString();
                String staffExpertise = dataSnapshot.child("details").child("staffExpertise").getValue().toString();
                String staffImageUrl = dataSnapshot.child("details").child("imageUrl").getValue().toString();
                String staffMobile = dataSnapshot.child("details").child("staffMobile").getValue().toString();

                staffListItems.add(new StaffListItems(staffName, staffExpertise, staffImageUrl, staffMobile, hospitalName));


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
        setRecyclerView();
        adapter.notifyDataSetChanged();


    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.staffRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new StaffAdapter(staffListItems, this);
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
