package com.example.hospitaladmin;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hospitaladmin.adapters.Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final ArrayList<StaffListItem> staffListItems = new ArrayList<>();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRootRef = db.getReference();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private CardView staffList;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference userRef = myRootRef.child("hospital_lists").child(firebaseUser.getUid()).child("staff_lists");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        staffList = findViewById(R.id.staffList);

//        staffList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        getData();
        setRecyclerView();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };


    }

    public void getData() {
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String staffName = dataSnapshot.child("staffName").getValue().toString();
                String staffExpertise = dataSnapshot.child("staffExpertise").getValue().toString();
                String staffImageUrl = dataSnapshot.child("imageUrl").getValue().toString();


                staffListItems.add(new StaffListItem(staffName, staffExpertise, staffImageUrl));
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

    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new Adapter(staffListItems,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(dashboard.this, MainActivity.class);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_staff:
                Intent intent = new Intent(dashboard.this, staff_registration.class);
                startActivity(intent);
                break;
            case R.id.nav_manage:
                Intent intent2 = new Intent(dashboard.this, HospitalDetailsUpdate.class);
                startActivity(intent2);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);

        return false;
    }


}
