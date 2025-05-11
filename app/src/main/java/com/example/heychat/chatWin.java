package com.example.heychat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class chatWin extends AppCompatActivity {

    String recieverName;
    String recieverEmail;
    TextView recieverNName;


    CardView sendBtn;
    EditText textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_win);

        sendBtn = findViewById(R.id.sendbtn);
        textMsg = findViewById(R.id.textmsg);


        recieverName = getIntent().getStringExtra("nameeee");
        recieverEmail = getIntent().getStringExtra("emailll");
        recieverNName = findViewById(R.id.recievername);


        recieverNName.setText("" + recieverName);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textMsg.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(chatWin.this, "Enter the message first", Toast.LENGTH_SHORT).show();
                }
                textMsg.setText("");
                msgModelClass messages = new msgModelClass(message, recieverEmail);
            }
        });
    }
}