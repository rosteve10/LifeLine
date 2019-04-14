package com.example.hospitaladmin;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginbtn, forgotbtn, registerbtn, staffLoginbtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this, "Already in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, dashboard.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginbtn = findViewById(R.id.loginbtn);
        forgotbtn = findViewById(R.id.forgotbtn);
        registerbtn = findViewById(R.id.registerbtn);
        staffLoginbtn = findViewById(R.id.staffLoginbtn1);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, hospital_registration1.class);
                startActivity(intent);
            }
        });

        staffLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, staff_login.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();

            }
        });

        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    public void onLogin() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Enter Email");
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Enter Password");
        } else {
            final String myEmail = email.getText().toString();
            final String myPass = password.getText().toString();
            mAuth.signInWithEmailAndPassword(myEmail, myPass)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid().toString();
                                Log.i("TAG", "USER: " + user.toString());
                                Log.i("TAG", "UserID: " + userId);
                                Toast.makeText(MainActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, dashboard.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }
}
