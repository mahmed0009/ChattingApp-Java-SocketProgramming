package com.example.heychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    Button button;
    TextView SignUpText;
    EditText email, password;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);


        button = findViewById(R.id.LogButton);
        email = findViewById(R.id.etLogEmail);
        password = findViewById(R.id.etLogPassword);
        SignUpText = findViewById(R.id.login_signupButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Pass = password.getText().toString();

                if(Email.isEmpty() || Pass.isEmpty()){
                    Toast.makeText(login.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    boolean isValid = dbHelper.verifyLogin(Email, Pass);

                    if(isValid){
                        Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);

            }
        });
    }
}