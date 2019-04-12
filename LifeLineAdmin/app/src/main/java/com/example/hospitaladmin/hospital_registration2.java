package com.example.hospitaladmin;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */



import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class hospital_registration2 extends AppCompatActivity {

    public static final String TAG = "HL";
    private static final int Gallery = 4;
    StorageReference mStorage;
    ProgressDialog progressDialog;
    private Button hospitalRegistration;
    private EditText hospitalName, hospitalAddress, hospitalContact, hospitalOrg, hospitalCity, hospitalState, hospitalWebsite, hospitalRating, hospitalHours;
    private ImageButton hospitalPhoto;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = db.getReference();
    private DatabaseReference userRef = rootRef.child("hospital_lists");
    private FirebaseAuth mAuth;

    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_registration2);

        mAuth = FirebaseAuth.getInstance();

        hospitalPhoto = findViewById(R.id.hospital_photo);
        hospitalRegistration = findViewById(R.id.hospital_registerbtn);
        hospitalName = findViewById(R.id.hospital_name);
        hospitalAddress = findViewById(R.id.hospital_address);
        hospitalContact = findViewById(R.id.hospital_contact);
        hospitalOrg = findViewById(R.id.hospital_org);
        hospitalCity = findViewById(R.id.hospital_city);
        hospitalState = findViewById(R.id.hospital_state);
        hospitalWebsite = findViewById(R.id.hospitalWebsite);
        hospitalRating = findViewById(R.id.hospitalRating);
        hospitalHours = findViewById(R.id.hospitalHours);


        mStorage = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        hospitalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery);
            }
        });


        hospitalRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });


        FirebaseUser firebaseUser = mAuth.getCurrentUser();
//        if (firebaseUser != null) {
//            final ArrayList<String> arrayList = new ArrayList<>();
//
//            DatabaseReference myUserRef = userRef.child(firebaseUser.getUid());
//            myUserRef.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//
//                    if (dataSnapshot.hasChild("hospitalName")) {
//                        String hospitalName = dataSnapshot.child("hospitalName").getValue().toString();
//                        arrayList.add(hospitalName);
//                        Log.d(TAG, "hosName=" + dataSnapshot.child("hospitalName").getValue().toString());
//
//                    }
////                    Log.d(TAG,"hosName="+dataSnapshot.child("hospitaName").getValue().toString());
//
////                    hospitalAddress.setText(dataSnapshot.child("hospitalAddress").getValue().toString());
////                    hospitalContact.setText(dataSnapshot.child("hospitalContact").getValue().toString());
////                    hospitalOrg.setText(dataSnapshot.child("hospitalOrg").getValue().toString());
////                    hospitalCity.setText(dataSnapshot.child("hospitalCity").getValue().toString());
//////                    hospitalState.setText(dataSnapshot.child("hospitalState").getValue().toString());
////                    hospitalWebsite.setText(dataSnapshot.child("hospitalWebsite").getValue().toString());
////                    hospitalRating.setText(dataSnapshot.child("hospitalRating").getValue().toString());
////                    hospitalHours.setText(dataSnapshot.child("hospitalHours").getValue().toString());
//                    hospitalRegistration.setText("Update Details");
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                arrayList.forEach((new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        hospitalName.setText(s);
//                    }
//                }));
//            }
//
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery) {
            progressDialog.setMessage("Uploading....");
            final Uri uri = data.getData();
            progressDialog.show();
            hospitalPhoto.setImageURI(uri);


            final StorageReference fileName = mStorage.child("Photos/adminImage/" + uri.getLastPathSegment() + ".png");
            fileName.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(hospital_registration2.this, "Upload Done", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    fileName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(hospital_registration2.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    private void register() {


        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        String myhospitalName = hospitalName.getText().toString().trim();
        String myHospitalAddress = hospitalAddress.getText().toString().trim();
        String myHospitalContact = hospitalContact.getText().toString().trim();
        String myHospitalOrg = hospitalOrg.getText().toString().trim();
        String myHospitalCity = hospitalCity.getText().toString().trim();
        String myHospitalState = hospitalState.getText().toString().trim();
        String myHospitalWebsite = hospitalWebsite.getText().toString().trim();
        String myHospitalRating = hospitalRating.getText().toString().trim();
        String myHospitalHours = hospitalHours.getText().toString().trim();
        String myImageUrl = imageUrl;

        String myHospitalUid = firebaseUser.getUid();
        if (TextUtils.isEmpty(myhospitalName)) {
            hospitalName.setError("Enter UserName");
        }
        if (TextUtils.isEmpty(myHospitalAddress)) {
            hospitalAddress.setError("Enter email");
        }
        if (TextUtils.isEmpty(myHospitalContact)) {
            hospitalContact.setError("Enter hospital contact");
        }
        if (TextUtils.isEmpty(myHospitalCity)) {
            hospitalCity.setError("Enter hospital city");
        }
        if (TextUtils.isEmpty(myHospitalState)) {
            hospitalState.setError("Enter state");
        }
        if (TextUtils.isEmpty(myHospitalHours)) {
            hospitalHours.setError("Enter Working hours");
        } else {
            HashMap<String, String> userMap = new HashMap<String, String>();
            userMap.put("hospitalName", myhospitalName);
            userMap.put("hospitalAddress", myHospitalAddress);
            userMap.put("hospitalContact", myHospitalContact);
            userMap.put("hospitalOrg", myHospitalOrg);
            userMap.put("hospitalCity", myHospitalCity);
            userMap.put("hospitalState", myHospitalState);
            userMap.put("hospitalWebsite", myHospitalWebsite);
            userMap.put("hospitalRating", myHospitalRating);
            userMap.put("hospitalHours", myHospitalHours);
            userMap.put("imageUrl", myImageUrl);

            userRef.child("myHospital_lists").child(myhospitalName).child("details").setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(hospital_registration2.this, "Sucessfull Registered", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(hospital_registration2.this, "Unsucessfull" + task.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            userRef.child(myHospitalUid).child("hospital_admin").child("details").setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(hospital_registration2.this, "Sucessfull Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(hospital_registration2.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(hospital_registration2.this, "Unsucessfull" + task.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }


}
