package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AvailableDoctorsList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    final ArrayList<StaffListItems> staffListItems = new ArrayList<>();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRootRef = db.getReference();
    String TAG = "HL";
    DatabaseReference userRef = myRootRef.child("hospital_staff_lists");
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_doctors_list);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

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
                adapter.notifyDataSetChanged();

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



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(AvailableDoctorsList.this, Dashoboard.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(AvailableDoctorsList.this, Profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_TrackHospitals:
                Intent intent2 = new Intent(AvailableDoctorsList.this,HospitalLoacator.class);
                startActivity(intent2);
                break;
            case R.id.nav_PreviousAppointments:
                Intent intent3 = new Intent(AvailableDoctorsList.this,BookedAppointments.class);
                startActivity(intent3);
                break;
            case R.id.nav_BookAppointments:
                Intent intent4 = new Intent(AvailableDoctorsList.this,FindDoctor.class);
                startActivity(intent4);
                break;
            case R.id.nav_manage:
                Intent intent5 = new Intent(AvailableDoctorsList.this,AboutLifeLine.class);
                startActivity(intent5);
                break;
        }

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.action_logout:
                AuthUI.getInstance().signOut(this);
                Intent intent = new Intent(AvailableDoctorsList.this, login.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.staffRecyclerView);
//        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new StaffAdapter(staffListItems, this);
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
