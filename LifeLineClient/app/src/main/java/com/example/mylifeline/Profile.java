package com.example.mylifeline;
/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    private EditText bloodGroup,firstName,lastName, gender,address,state,country,pincode,email,mobile;
    private Button done;

    FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = mAuth.getReference();
    DatabaseReference userRef = rootRef.child("profile");
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseFirestore.setLoggingEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bloodGroup = findViewById(R.id.bloodGroup);
        firstName = findViewById(R.id.firstname_txt_fld);
        lastName = findViewById(R.id.last_name_txt_fld);
        gender = findViewById(R.id.genderTxtFld);
        address = findViewById(R.id.address_line1);
        state = findViewById(R.id.state_name_edit_txt);
        country = findViewById(R.id.country_name_edit_txt);
        pincode = findViewById(R.id.pincode_txt_fld);
        email = findViewById(R.id.email_txt_fld);
        mobile = findViewById(R.id.mobile_no_txt_fld);
        done = findViewById(R.id.done_btn);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("bloodGroup",bloodGroup.getText().toString());
                hashMap.put("firstName",firstName.getText().toString());
                hashMap.put("lastName",lastName.getText().toString());
                hashMap.put("gender",gender.getText().toString());
                hashMap.put("address",address.getText().toString());
                hashMap.put("state",state.getText().toString());
                hashMap.put("country",country.getText().toString());
                hashMap.put("pincode",pincode.getText().toString());
                hashMap.put("email",email.getText().toString());
                hashMap.put("mobile",mobile.getText().toString());
                userRef.child(mobile.getText().toString()).setValue(hashMap);
                Toast.makeText(Profile.this,"updated succesfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Profile.this,Dashoboard.class);
                startActivity(intent);
            }
        });




    }




}
