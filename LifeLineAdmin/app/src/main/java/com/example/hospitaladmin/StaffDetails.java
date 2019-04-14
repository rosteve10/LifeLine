package com.example.hospitaladmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffDetails extends AppCompatActivity {


    private TextView name,email,password,degrees,speciality,mobile,cabin;
    private Button edit,delete;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = db.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private DatabaseReference userRef = rootRef.child("hospital_lists").child(firebaseUser.getUid()).child("staff_lists");
    private DatabaseReference userRef2 = rootRef.child("staff_login");
    DatabaseReference userRef3 = rootRef.child("hospital_staff_lists");
    private DatabaseReference userRef4 = rootRef.child("hospital_lists").child(firebaseUser.getUid()).child("hospital_admin");
    DatabaseReference appointmentRef = rootRef.child("appointment");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);


        name = findViewById(R.id.staffDetailsName);
        email = findViewById(R.id.staffDetailsEmail);
        password = findViewById(R.id.staffDetailsPassword);
        degrees = findViewById(R.id.staffDetailsDegrees);
        speciality = findViewById(R.id.staffDetailsSpeciality);
        mobile = findViewById(R.id.staffDetailsMobile);
        cabin = findViewById(R.id.staffDetailsCabin);
        edit = findViewById(R.id.staffEditButton);
        delete = findViewById(R.id.staffDeleteButton);
        final String myName = getIntent().getStringExtra("name");



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        final String myName = getIntent().getStringExtra("name");

                        String doctorname = dataSnapshot.child("staffName").getValue().toString();

                        if(myName.equals(doctorname)){
                            final String mobile = dataSnapshot.child("staffMobile").getValue().toString();
                            final String myMobile = mobile;
                            userRef.child(mobile).removeValue();
                            userRef2.child(myMobile).removeValue();
                            userRef4.addChildEventListener(new ChildEventListener() {
                                @Override

                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    String hospitalName = dataSnapshot.child("hospitalName").getValue().toString();
                                    userRef3.child(hospitalName).child(myMobile).removeValue();
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
                        appointmentRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                String myDoctor = dataSnapshot.child("doctorName").getValue().toString();
                                if(myName.equals(myDoctor)){
                                    String problem = dataSnapshot.child("problem").getValue().toString();
                                    appointmentRef.child(problem).removeValue();
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

                        Intent intent = new Intent(StaffDetails.this,dashboard.class);
                        startActivity(intent);
                        finish();
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
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(StaffDetails.this,staff_registration.class);
                startActivity(intent);
            }
        });

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                String doctorname = dataSnapshot.child("staffName").getValue().toString();

                if(myName.equals(doctorname)){

                    name.setText(doctorname);
                    email.setText(dataSnapshot.child("staffEmail").getValue().toString());
                    password.setText(dataSnapshot.child("staffPassword").getValue().toString());
                    degrees.setText(dataSnapshot.child("staffDegrees").getValue().toString());
                    speciality.setText(dataSnapshot.child("staffExpertise").getValue().toString());
                    mobile.setText(dataSnapshot.child("staffMobile").getValue().toString());
                    cabin.setText(dataSnapshot.child("staffCabin").getValue().toString());


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
}
