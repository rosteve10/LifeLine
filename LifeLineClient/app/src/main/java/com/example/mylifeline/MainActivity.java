package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    int count = 0;
    private TextView text;
    Runnable updateTextRunnable = new Runnable() {
        public void run() {
            count++;
            if (count % 2 == 0) {
                text.setTextSize(50);
                text.setText("LifeLine App Helps You to Find Nearby Doctors ");
            } else {
                text.setTextSize(40);//        FirebaseApp.initializeApp(this);


                text.setText("Lifeline App Helps You To Book\n" +
                        "  Appointments With Doctors");
            }
            handler.postDelayed(this, 4000);
        }
    };
    private Button getStartedButton;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        getStartedButton = findViewById(R.id.get_started_btn);

        handler.post(updateTextRunnable);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });


    }
}
