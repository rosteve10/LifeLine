package com.example.mylifeline;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookedAppointments extends AppCompatActivity {


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRootRef = db.getReference();
    DatabaseReference appointmentRef = myRootRef.child("appointment");
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String TAG = "HL";
    ArrayList<BookedListItem> bookedListItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_appointments);

        getData();
        setRecyclerView();


    }


    public void getData(){


        appointmentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String name = dataSnapshot.child("name").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String problemn = dataSnapshot.child("problem").getValue().toString();
                String doctoremail = dataSnapshot.child("email").getValue().toString();
                String time = dataSnapshot.child("time").getValue().toString();
                String doctorName = dataSnapshot.child("doctorName").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String myName = getIntent().getStringExtra("name");



                Log.d(TAG,"test"+doctorName + " " + date + " " + problemn + " " + time+" "+status+ " " );



                if(myName.equals(name)) {
                    bookedListItems.add(new BookedListItem(doctorName,date,time,problemn,status));
                    adapter.notifyDataSetChanged();
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

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.bookedrecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new BookedAdapter(bookedListItems,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
