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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;




public class signup extends AppCompatActivity {

    TextView backToLogin;
    Button signUp;
    EditText SignUpEmail, SignUpPass, SignUpUserName;
    DatabaseHelper myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        myDB = new DatabaseHelper(this);

        signUp = findViewById(R.id.buttonSignUp);
        SignUpEmail = findViewById(R.id.etSignUpEmail);
        SignUpPass = findViewById(R.id.etSingUpPass);
        SignUpUserName = findViewById(R.id.etSingUpName);
        backToLogin = findViewById(R.id.back_to_login_on_signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = SignUpEmail.getText().toString();
                String password = SignUpPass.getText().toString();
                String username = SignUpUserName.getText().toString();

                if(email.isEmpty() || password.isEmpty() || username.isEmpty()){
                    Toast.makeText(signup.this, "Please Fill all the Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean isInserted = myDB.insertData(email, password, username);

                    if(isInserted){
                        Toast.makeText(signup.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(signup.this, "Unable to Create New Account", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}