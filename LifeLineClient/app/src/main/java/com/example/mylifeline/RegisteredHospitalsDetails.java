package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class RegisteredHospitalsDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    final String TAG = "HL";
    ImageView imageView2;
    TextView tvName, tvAddress, tvPhone, tvWebsite, tvRating, tvOpening;
    Button btnCall, btnDirections, searchDoctor;
    Context ctx;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_hospitals_details);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        ctx = this;
        imageView2 = (ImageView) findViewById(R.id.myimageView2);
        tvName = (TextView) findViewById(R.id.mytvName);
        tvAddress = (TextView) findViewById(R.id.mytvAddress);
        tvPhone = (TextView) findViewById(R.id.mytvPhone);
        tvRating = (TextView) findViewById(R.id.mytvRating);
        tvWebsite = (TextView) findViewById(R.id.mytvWebsite);
        tvOpening = (TextView) findViewById(R.id.mytvOpening);
        btnCall = (Button) findViewById(R.id.mybtnCall);
        btnDirections = (Button) findViewById(R.id.mybtnDirections);
        searchDoctor = findViewById(R.id.searchDoctor);
        final Intent intent = getIntent();
        String hospitalName = intent.getStringExtra("hospitalName");
        Log.d(TAG, "test" + hospitalName);
        DatabaseReference userRef = rootRef.child("hospital_lists").child("myHospital_lists").child(hospitalName);

        searchDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RegisteredHospitalsDetails.this, AvailableDoctorsList.class);
                intent1.putExtra("hospitalName", tvName.getText().toString());
                intent1.putExtra("hospitalMobile", tvPhone.getText().toString());
                startActivity(intent1);
            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+tvPhone.getText().toString()));
                startActivity(i);
            }
        });

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String url = dataSnapshot.child("imageUrl").getValue().toString();
                String hName = dataSnapshot.child("hospitalName").getValue().toString();
                String hAddress = dataSnapshot.child("hospitalAddress").getValue().toString();
                String hPhone = dataSnapshot.child("hospitalContact").getValue().toString();
                String hRating = dataSnapshot.child("hospitalRating").getValue().toString();

                String hWebsite = dataSnapshot.child("hospitalWebsite").getValue().toString();
                String hopening = dataSnapshot.child("hospitalHours").getValue().toString();

                Picasso.get().load(url).into(imageView2);
                tvName.setText(hName);
                tvAddress.setText(hAddress);
                tvPhone.setText(hPhone);
                tvRating.setText(hRating);
                tvWebsite.setText(hWebsite);
                tvOpening.setText(hopening);


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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(RegisteredHospitalsDetails.this, Dashoboard.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(RegisteredHospitalsDetails.this, Profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_TrackHospitals:
                Intent intent2 = new Intent(RegisteredHospitalsDetails.this,HospitalLoacator.class);
                startActivity(intent2);
                break;
            case R.id.nav_PreviousAppointments:
                Intent intent3 = new Intent(RegisteredHospitalsDetails.this,BookedAppointments.class);
                startActivity(intent3);
                break;
            case R.id.nav_BookAppointments:
                Intent intent4 = new Intent(RegisteredHospitalsDetails.this,FindDoctor.class);
                startActivity(intent4);
                break;
            case R.id.nav_manage:
                Intent intent5 = new Intent(RegisteredHospitalsDetails.this,AboutLifeLine.class);
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
                Intent intent = new Intent(RegisteredHospitalsDetails.this, login.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
