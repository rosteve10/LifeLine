package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Appointment extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener{

    FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = mAuth.getReference();


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    DatabaseReference userRef = rootRef.child("appointment");
    String date,time;
    private Button book;
    private EditText editText;
    private DatePicker datePicker;
    private TimePicker timePicker;

    String name;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        final String staffEmail = intent.getStringExtra("staffEmail");
        final String doctorName = intent.getStringExtra("staffName");


        book = findViewById(R.id.book);
        editText = findViewById(R.id.editText);
        datePicker = findViewById(R.id.date);
        timePicker = findViewById(R.id.time);

        Calendar calendar = Calendar.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
             name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

        datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "-" + monthOfYear + "-" + year;

                    }
                });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay+":"+minute;
            }
        });



        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("date",date);
                hashMap.put("time",time);
                hashMap.put("problem",editText.getText().toString());
                hashMap.put("name",name);
                hashMap.put("email",staffEmail);
                hashMap.put("doctorName",doctorName);
                hashMap.put("status","pending");
                userRef.child(editText.getText().toString()).setValue(hashMap);

                Toast.makeText(Appointment.this, "Your appointment is send to doctor, and yet to be confirm, check later", Toast.LENGTH_LONG).show();

            }
        });



    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(Appointment.this, Dashoboard.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(Appointment.this, Profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_TrackHospitals:
                Intent intent2 = new Intent(Appointment.this,HospitalLoacator.class);
                startActivity(intent2);
                break;
            case R.id.nav_PreviousAppointments:
                Intent intent3 = new Intent(Appointment.this,BookedAppointments.class);
                startActivity(intent3);
                break;
            case R.id.nav_BookAppointments:
                Intent intent4 = new Intent(Appointment.this,FindDoctor.class);
                startActivity(intent4);
                break;
            case R.id.nav_manage:
                Intent intent5 = new Intent(Appointment.this,AboutLifeLine.class);
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
                Intent intent = new Intent(Appointment.this, login.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
