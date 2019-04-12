package com.example.hospitaladmin;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hospitaladmin.adapters.AppointmentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StaffDashboard extends AppCompatActivity {

ArrayList<AppointmentItem> appointmentItems = new ArrayList<>();

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRootRef = db.getReference();
    DatabaseReference userRef = myRootRef.child("staff_login");
    DatabaseReference appointmentRef = myRootRef.child("appointment");
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String TAG = "HL";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);


        mDrawerLayout = findViewById(R.id.drawer_layout1);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();
        setRecyclerView();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_logout:
//                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StaffDashboard.this, staff_login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_refresh:
                finish();
                startActivity(getIntent());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData(){


        appointmentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String name = dataSnapshot.child("name").getValue().toString();
                String time = dataSnapshot.child("date").getValue().toString();
                String problemn = dataSnapshot.child("problem").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String staffEmail = getIntent().getStringExtra("staffEmail");
                Log.d(TAG,"test"+name + " " + time + " " + problemn );



                if(email.equals(staffEmail)) {
                    appointmentItems.add(new AppointmentItem(name, time, problemn));
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
        recyclerView = findViewById(R.id.staffRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AppointmentAdapter(appointmentItems,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }




}
