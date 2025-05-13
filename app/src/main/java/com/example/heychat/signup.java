package com.example.heychat;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    TextView backToLogin;
    Button signUp;
    EditText SignUpEmail, SignUpPass, SignUpUserName;
    DatabaseHelper myDB;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        myDB = new DatabaseHelper(this);

        signUp = findViewById(R.id.buttonSignUp);
        SignUpEmail = findViewById(R.id.etSignUpEmail);
        SignUpPass = findViewById(R.id.etSingUpPass);
        SignUpUserName = findViewById(R.id.etSingUpName);
        backToLogin = findViewById(R.id.back_to_login_on_signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = SignUpEmail.getText().toString().trim();
                String password = SignUpPass.getText().toString().trim();
                String username = SignUpUserName.getText().toString().trim();
                String status = "Hey I'm using this application";

                if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(signup.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = task.getResult().getUser().getUid();
                            database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("user").child(id);

                            Users users = new Users(id, username, email, password, status);
                            reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> dbTask) {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(signup.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(signup.this, login.class));
                                        finish();
                                    } else {
                                        Toast.makeText(signup.this, "Failed to save user to database", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(signup.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Also insert into SQLite
                boolean isInserted = myDB.insertData(email, password, username);
                if (isInserted) {
                    Toast.makeText(signup.this, "Saved in SQL DB", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(signup.this, "Failed to save in SQL DB", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, login.class));
                finish();
            }
        });
    }
}
