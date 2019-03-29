package com.example.hospitaladmin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HospitalDetailsUpdate extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details_update);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = db.getReference();
        DatabaseReference useRRef = rootRef.child("hospital_lists").child(firebaseUser.getUid());
        final ArrayList<String> arrayList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HospitalDetailsUpdate.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);


        if (firebaseUser != null) {
            useRRef.addChildEventListener(new ChildEventListener() {
                hospital_registration1 hospitalRegistration1 = new hospital_registration1();

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

//                    String hospitalName = dataSnapshot.child("hospitalName").getValue().toString();
//                    arrayList.add(hospitalName);
//                    arrayAdapter.notifyDataSetChanged();

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
}
