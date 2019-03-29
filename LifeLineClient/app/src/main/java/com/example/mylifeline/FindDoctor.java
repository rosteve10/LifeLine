package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindDoctor extends AppCompatActivity {

    public static final String TAG = "HL";
    private static String myCity;
    private Button button;
    private EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

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

}
