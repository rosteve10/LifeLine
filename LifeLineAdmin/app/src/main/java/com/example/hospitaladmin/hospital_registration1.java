package com.example.hospitaladmin;

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

public class hospital_registration1 extends AppCompatActivity {

    private Button registerbtn;
    private EditText email, password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_registration1);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        registerbtn = findViewById(R.id.continueregisterbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();


            }
        });


    }

    public void onRegister() {

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Enter Email");
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Enter Password");
        } else {
            final String myEmail = email.getText().toString();
            final String myPass = password.getText().toString();
            mAuth.createUserWithEmailAndPassword(myEmail, myPass)
                    .addOnCompleteListener(hospital_registration1.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("TAG", "createUserWithEmail:success");
                                Toast.makeText(hospital_registration1.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(hospital_registration1.this, hospital_registration2.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(hospital_registration1.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }


}
