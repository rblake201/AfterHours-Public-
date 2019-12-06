package com.example.starterproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventInfoActivity extends AppCompatActivity {

    private Button firebaseBtn;
    private DatabaseReference database;
    private EditText myTitle, myDate, myTime, myDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        firebaseBtn = (Button) findViewById(R.id.button);
        database = FirebaseDatabase.getInstance().getReference();


        firebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                myTitle = findViewById(R.id.title);
                myDate = findViewById(R.id.date);
                myTime = findViewById(R.id.time);
                myDesc = findViewById(R.id.description);

                Event event = new Event(myTitle.getText().toString(), myDate.getText().toString(),
                                        myTime.getText().toString(), myDesc.getText().toString());

                database.child("Event").push().setValue(event);
            }
        });
    }
}