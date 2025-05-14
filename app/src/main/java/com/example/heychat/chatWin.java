package com.example.heychat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class chatWin extends AppCompatActivity {

    String reciverName, reciverUid, senderUid;
    TextView reciverNName;

    CardView sendBtn;
    EditText textMsg;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    String senderRoom, reciverRoom;
    RecyclerView mmessageAdpter;
    ArrayList<msgModelClass> messagesArrayList;
    messagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_win);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Initialize variables
        messagesArrayList = new ArrayList<>();

        // Get data from intent
        reciverName = getIntent().getStringExtra("nameeee");
        reciverUid = getIntent().getStringExtra("uid");
        senderUid = firebaseAuth.getUid();

        // Chat room names
        senderRoom = senderUid + reciverUid;
        reciverRoom = reciverUid + senderUid;

        // Initialize views
        mmessageAdpter = findViewById(R.id.msgadpter);
        sendBtn = findViewById(R.id.sendbtn);
        textMsg = findViewById(R.id.textmsg);
        reciverNName = findViewById(R.id.recievername);  // ✅ Corrected ID

        reciverNName.setText(reciverName);

        // RecyclerView setup
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessageAdpter.setLayoutManager(linearLayoutManager);

        messagesAdapter = new messagesAdapter(chatWin.this, messagesArrayList);
        mmessageAdpter.setAdapter(messagesAdapter);

        // Listen for messages in senderRoom
        DatabaseReference messageRef = database.getReference().child("chats").child(senderRoom).child("message");

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    msgModelClass messages = dataSnapshot.getValue(msgModelClass.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Send button functionality
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textMsg.getText().toString().trim();

                if (message.isEmpty()) {
                    Toast.makeText(chatWin.this, "Enter the message first", Toast.LENGTH_SHORT).show();
                    return;
                }

                textMsg.setText("");
                Date date = new Date();
                msgModelClass messages = new msgModelClass(message, senderUid, date.getTime());

                database.getReference().child("chats").child(senderRoom).child("message").push().setValue(messages)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats").child(reciverRoom).child("message").push().setValue(messages);
                            }
                        });
            }
        });
    }
}
