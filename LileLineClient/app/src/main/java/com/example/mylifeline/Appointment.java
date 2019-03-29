package com.example.mylifeline;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Appointment extends AppCompatActivity {

    FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = mAuth.getReference();
    DatabaseReference userRef = rootRef.child("appointment_messages").child("messages");
    String date;
    private Button book;
    private EditText editText;
    private DatePicker datePicker;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        book = findViewById(R.id.book);
        editText = findViewById(R.id.editText);
        datePicker = findViewById(R.id.date);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = dayOfMonth + "-" + monthOfYear + "-" + year;
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Appointment.this, "Your appointment is send to doctor succesfully", Toast.LENGTH_LONG).show();

            }
        });


    }
}
