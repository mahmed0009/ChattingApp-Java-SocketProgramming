package com.example.heychat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class chatWin extends AppCompatActivity {

    String recieverName;
    TextView recieverNName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_win);

        recieverName = getIntent().getStringExtra("nameeee");
        recieverNName = findViewById(R.id.recievername);


        recieverNName.setText("" + recieverName);
    }
}