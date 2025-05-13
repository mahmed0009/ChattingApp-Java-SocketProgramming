package com.example.heychat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button button;
    TextView SignUpText;
    EditText email, password;
    DatabaseHelper dbHelper;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);
        auth = FirebaseAuth.getInstance();

        button = findViewById(R.id.LogButton);
        email = findViewById(R.id.etLogEmail);
        password = findViewById(R.id.etLogPassword);
        SignUpText = findViewById(R.id.login_signupButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Pass = password.getText().toString();

                if (Email.isEmpty() || Pass.isEmpty()) {
                    Toast.makeText(login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (isNetworkAvailable()) {
                        //  Firebase Auth (when online)
                        auth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    try {
                                        Toast.makeText(login.this, "Firebase Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (Exception e) {
                                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(login.this, "Firebase Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // SQLite Auth (when offline)
                        boolean isValid = dbHelper.verifyLogin(Email, Pass);


                        if (isValid) {
                            Toast.makeText(login.this, "Login Successful (Offline)", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(login.this, "Invalid email or password (Offline)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                finish();
                startActivity(intent);
            }
        });
    }

    // Network availability check method
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
