package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;

public class FindDoctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "HL";
    private static String myCity;
    private Button button;
    private EditText city;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        button = findViewById(R.id.specialtySearchButton);
        city = findViewById(R.id.cityEditText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDoctor.this, RegisteredHospitalList.class);
                Bundle bundle = new Bundle();
                getCity();
                bundle.putString("city", myCity);
                intent.putExtras(bundle);

                Log.d(TAG, "tes1" + getCity());
                startActivity(intent);
            }
        });


    }

    public String getCity() {
        myCity = city.getText().toString();
        return myCity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(FindDoctor.this, Dashoboard.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(FindDoctor.this, Profile.class);
                startActivity(intent1);
                break;
            case R.id.nav_TrackHospitals:
                Intent intent2 = new Intent(FindDoctor.this,HospitalLoacator.class);
                startActivity(intent2);
                break;
            case R.id.nav_PreviousAppointments:
                Intent intent3 = new Intent(FindDoctor.this,BookedAppointments.class);
                startActivity(intent3);
                break;
            case R.id.nav_BookAppointments:
                Intent intent4 = new Intent(FindDoctor.this,FindDoctor.class);
                startActivity(intent4);
                break;
            case R.id.nav_manage:
                Intent intent5 = new Intent(FindDoctor.this,AboutLifeLine.class);
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
                Intent intent = new Intent(FindDoctor.this, login.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
