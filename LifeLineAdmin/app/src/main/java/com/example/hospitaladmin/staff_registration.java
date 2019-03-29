package com.example.hospitaladmin;
/**
 * Created by Rishabh Gupta on 29-03-2019
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.CircularToggle;

import java.util.HashMap;

public class staff_registration extends AppCompatActivity {
    public static final String TAG = "HL";
    private static final int Gallery = 4;
    public TimePicker spinnerTime, spinnerTime1;
    StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    ProgressDialog progressDialog;
    String imageUrl, hospitalName;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = db.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private DatabaseReference userRef = rootRef.child("hospital_lists").child(firebaseUser.getUid()).child("staff_lists");
    DatabaseReference hospitalRef = rootRef.child("hospital_lists").child(firebaseUser.getUid()).child("hospital_admin");

    private DatabaseReference userRef2 = rootRef.child("staff_login");
    private DatabaseReference myUserRef = rootRef.child("hospital_staff_lists");
    private EditText staffName, staffEmail, staffPassword, staffdegrees, staffWorking, staffMobile, staffCabin;
    private ImageButton staffImage;
    private Button submitButton;
    private Spinner staffExpertise;
    private String staffStartTimeSun = null, staffStartTimeMon = null, staffStartTimeTue = null, staffStartTimeWed = null, staffStartTimeThu = null, staffStartTimeFri = null, staffStartTimeSat = null, staffEndTimeSun = null, staffEndTimeMon = null, staffEndTimeTue = null, staffEndTimeWed = null, staffEndTimeThu = null, staffEndTimeFri = null, staffEndTimeSat = null, workingDay;
    private CircularToggle sun, mon, tue, wed, thu, fri, sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_registration);
        progressDialog = new ProgressDialog(this);

        staffName = findViewById(R.id.staffName);
        staffEmail = findViewById(R.id.staffEmail);
        staffPassword = findViewById(R.id.staffPassword);
        staffdegrees = findViewById(R.id.staffDegrees);
        staffExpertise = findViewById(R.id.specialistSpinner);
        staffMobile = findViewById(R.id.staffMobile);
        staffCabin = findViewById(R.id.staffCabin);
        staffImage = findViewById(R.id.staffImage);
        submitButton = findViewById(R.id.staffBuuton);

        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);

        hospitalRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                hospitalName = dataSnapshot.child("hospitalName").getValue().toString();
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


        MultiSelectToggleGroup multi = (MultiSelectToggleGroup) findViewById(R.id.group_weekdays);
        multi.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {


                if (isChecked) {
                    if (checkedId == sun.getId()) {
                        workingDay = "sunday";
                    }
                    if (checkedId == mon.getId()) {
                        workingDay = "monday";
                    }
                    if (checkedId == tue.getId()) {
                        workingDay = "tuesday";
                    }
                    if (checkedId == wed.getId()) {
                        workingDay = "wednesday";
                    }
                    if (checkedId == thu.getId()) {
                        workingDay = "thursday";
                    }
                    if (checkedId == fri.getId()) {
                        workingDay = "friday";
                    }
                    if (checkedId == sat.getId()) {
                        workingDay = "saturday";
                    }
                    Log.d(TAG, "my" + workingDay);
                    Log.d(TAG, "test" + sun.getId() + " " + mon.getId() + " " + thu.getId());
                    showOptionDialog();


                }


            }
        });


        staffImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffRegister();
                Toast.makeText(staff_registration.this, "Staff registered successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(staff_registration.this, dashboard.class);
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery) {
            progressDialog.setMessage("Uploading....");
            Uri uri = data.getData();
            progressDialog.show();
            staffImage.setImageURI(uri);


            final StorageReference fileName = mStorage.child("Photos/staffImages" + uri.getLastPathSegment() + ".png");
            fileName.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(staff_registration.this, "Upload Done", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(staff_registration.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    private void staffRegister() {

        String myStaffName = staffName.getText().toString();
        String myStaffEmail = staffEmail.getText().toString();
        String myStaffPassword = staffPassword.getText().toString();
        String myStaffDegrees = staffdegrees.getText().toString();
        String myStaffExpertise = staffExpertise.getSelectedItem().toString();
        String myStaffMobile = staffMobile.getText().toString();
        String myStaffCabin = staffCabin.getText().toString();

        if (TextUtils.isEmpty(myStaffName)) {
            staffName.setError("Please Enter name");
        }
        if (TextUtils.isEmpty(myStaffEmail)) {
            staffEmail.setError("Please Enter email");
        }
        if (TextUtils.isEmpty(myStaffPassword)) {
            staffPassword.setError("Please Enter password");
        }
        if (TextUtils.isEmpty(myStaffDegrees)) {
            staffdegrees.setError("Please Enter degrees");
        }
        if (TextUtils.isEmpty(myStaffMobile)) {
            staffMobile.setError("Please Enter mobile");
        } else {
            HashMap<String, String> usermap = new HashMap<>();
            usermap.put("staffName", myStaffName);
            usermap.put("staffEmail", myStaffEmail);
            usermap.put("staffPassword", myStaffPassword);
            usermap.put("staffDegrees", myStaffDegrees);
            usermap.put("staffExpertise", myStaffExpertise);
            usermap.put("staffMobile", myStaffMobile);
            usermap.put("staffCabin", myStaffCabin);
            usermap.put("imageUrl", imageUrl);

            userRef.child(myStaffMobile).setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(staff_registration.this, "Sucessfull", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(staff_registration.this, "Unsucessfull" + task.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            myUserRef.child(hospitalName).child(myStaffMobile).child("details").setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(staff_registration.this, "Sucessfull", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(staff_registration.this, "Unsucessfull" + task.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            DatabaseReference userRef1 = rootRef.child("hospital_lists").child(firebaseUser.getUid()).child("staff_lists").child(myStaffMobile).child("staffWorking");
            DatabaseReference myUserRef1 = rootRef.child("hospital_staff_lists").child(hospitalName).child(myStaffMobile).child("details").child("staffWorking");

            HashMap<String, String> usermap1 = new HashMap<>();
            HashMap<String, String> usermap2 = new HashMap<>();
            HashMap<String, String> usermap3 = new HashMap<>();
            HashMap<String, String> usermap4 = new HashMap<>();
            HashMap<String, String> usermap5 = new HashMap<>();
            HashMap<String, String> usermap6 = new HashMap<>();
            HashMap<String, String> usermap7 = new HashMap<>();

            usermap1.put("startTime", staffStartTimeSun);
            usermap1.put("endTime", staffEndTimeSun);
            userRef1.child("sunday").setValue(usermap1);
            myUserRef1.child("sunday").setValue(usermap1);

            usermap2.put("startTime", staffStartTimeMon);
            usermap2.put("endTime", staffEndTimeMon);
            userRef1.child("monday").setValue(usermap2);
            myUserRef1.child("sunday").setValue(usermap1);

            usermap3.put("startTime", staffStartTimeTue);
            usermap3.put("endTime", staffEndTimeTue);
            userRef1.child("tuesday").setValue(usermap3);
            myUserRef1.child("sunday").setValue(usermap1);

            usermap4.put("startTime", staffStartTimeWed);
            usermap4.put("endTime", staffEndTimeWed);
            userRef1.child("wednesday").setValue(usermap4);
            myUserRef1.child("sunday").setValue(usermap1);

            usermap5.put("startTime", staffStartTimeThu);
            usermap5.put("endTime", staffEndTimeThu);
            userRef1.child("thursday").setValue(usermap5);
            myUserRef1.child("sunday").setValue(usermap1);

            usermap6.put("startTime", staffStartTimeFri);
            usermap6.put("endTime", staffEndTimeFri);
            userRef1.child("friday").setValue(usermap6);
            myUserRef1.child("sunday").setValue(usermap1);

            usermap7.put("startTime", staffStartTimeSat);
            usermap7.put("endTime", staffEndTimeSat);
            userRef1.child("saturday").setValue(usermap7);
            myUserRef1.child("sunday").setValue(usermap1);

            HashMap<String, String> userlogin = new HashMap<>();
            userlogin.put("email", myStaffEmail);
            userlogin.put("password", myStaffPassword);
            userRef2.child(myStaffMobile).setValue(userlogin).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(staff_registration.this, "Sucessfull", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(staff_registration.this, "Unsucessfull" + task.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    public void showOptionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(staff_registration.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View optionView = inflater.inflate(R.layout.timepicker, null);

        spinnerTime = optionView.findViewById(R.id.timePicker);
        spinnerTime1 = optionView.findViewById(R.id.timePicker1);

        spinnerTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {


                if (workingDay.equals("sunday")) {

                    staffStartTimeSun = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("monday")) {

                    staffStartTimeMon = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("tuesday")) {

                    staffStartTimeTue = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("wednesday")) {

                    staffStartTimeWed = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("thurday")) {

                    staffStartTimeThu = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("friday")) {

                    staffStartTimeFri = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("saturday")) {

                    staffStartTimeSat = String.valueOf(hourOfDay + ":" + minute);
                }


            }
        });
        spinnerTime1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (workingDay.equals("sunday")) {

                    staffEndTimeSun = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("monday")) {

                    staffEndTimeMon = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("tuesday")) {

                    staffEndTimeTue = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("wednesday")) {

                    staffEndTimeWed = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("thurday")) {

                    staffEndTimeThu = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("friday")) {

                    staffEndTimeFri = String.valueOf(hourOfDay + ":" + minute);
                }
                if (workingDay.equals("saturday")) {

                    staffEndTimeSat = String.valueOf(hourOfDay + ":" + minute);
                }
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.d(TAG, "sunday" + staffStartTimeSun);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(optionView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

}
