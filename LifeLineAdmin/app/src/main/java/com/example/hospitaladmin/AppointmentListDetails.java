package com.example.hospitaladmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentListDetails extends AppCompatActivity {


    private Button confirm,cancel,profile;
    FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = mAuth.getReference();
    DatabaseReference userRef = rootRef.child("appointment");


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list_details);


        confirm = findViewById(R.id.confirmAppointment);
        cancel = findViewById(R.id.cancelAppointment);
        profile = findViewById(R.id.viewProfile);





        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getIntent().getStringExtra("name");
                Intent intent = new Intent(AppointmentListDetails.this,PatientProfile.class);
                intent.putExtra("name",name);
                startActivity(intent);


            }
        });
        final String problem = getIntent().getStringExtra("problem");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRef.child(problem).child("status").setValue("yey!!Your appointment is confirmed");

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.child(problem).child("status").setValue("sorry! Doctor is busy your appointment is not confirmed");
                userRef.child(problem).removeValue();
            }
        });



    }
}
