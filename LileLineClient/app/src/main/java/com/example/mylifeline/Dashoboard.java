package com.example.mylifeline;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mylifeline.models.MainActivityViewModel;
import com.example.mylifeline.utils.Utils;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;

public class Dashoboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "HL";
    private static final int RC_SIGN_IN = 9001;
    FirebaseFirestore mFirestore;
    Utils utilsObj = new Utils();
    private MainActivityViewModel mViewModel;
    private LinearLayout previousAppointmentVw, scheduleAppointmentVw, trackhospitals, profileVw;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashoboard);


        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeAll();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        FirebaseApp.initializeApp(this);

        if (requestCode == RC_SIGN_IN) {
            mViewModel.setIsSigningIn(false);

            if (resultCode != RESULT_OK && shouldStartSignIn()) {
                startSignIn();
            }
        }
    }

    private boolean shouldStartSignIn() {
        return (!mViewModel.getIsSigningIn() && FirebaseAuth.getInstance().getCurrentUser() == null);
    }

    private void startSignIn() {
        // Sign in with FirebaseUI
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build()))
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
        mViewModel.setIsSigningIn(true);
    }

    @Override
    public void onStart() {
        super.onStart();
//        FirebaseApp.initializeApp(this);


        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn();
            return;
        }
//        FirebaseApp.initializeApp(this);

    }

    public void initializeAll() {
        previousAppointmentVw = findViewById(R.id.previous_appointmet);
        scheduleAppointmentVw = findViewById(R.id.schedule_appointment);
        profileVw = findViewById(R.id.profile_vw);
        trackhospitals = findViewById(R.id.track_hospital);
        trackhospitals.setOnClickListener(this);
        previousAppointmentVw.setOnClickListener(this);
        scheduleAppointmentVw.setOnClickListener(this);
        profileVw.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_vw:
                Intent intent = new Intent(Dashoboard.this, Profile.class);
                startActivity(intent);
                break;
            case R.id.previous_appointmet:
//                Intent previousAppointmetIntent = new Intent(DashboardActivity.this, PreviousAppointmentActivity.class);
//                startActivity(previousAppointmetIntent);
                break;
            case R.id.track_hospital:
                Intent trackLioskIntent = new Intent(Dashoboard.this, HospitalLoacator.class);
                startActivity(trackLioskIntent);
                utilsObj.showCustomToast(getApplicationContext(), "Inprogress");
                break;
            case R.id.schedule_appointment:
                Intent scheduleAppointmentIntent = new Intent(Dashoboard.this, FindDoctor.class);
                startActivity(scheduleAppointmentIntent);
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_logout:
                AuthUI.getInstance().signOut(this);
                Intent intent = new Intent(Dashoboard.this, login.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Dashoboard.this, login.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(Dashoboard.this, Dashoboard.class);
                startActivity(intent);
            case R.id.nav_profile:
                Intent intent1 = new Intent(Dashoboard.this, Profile.class);
                startActivity(intent1);
        }

        return false;
    }
}
