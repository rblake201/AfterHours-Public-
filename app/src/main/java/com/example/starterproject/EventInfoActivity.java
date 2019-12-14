package com.example.starterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esri.arcgisruntime.geometry.Point;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class EventInfoActivity extends AppCompatActivity {

    private Button firebaseBtn;
    private DatabaseReference database;
    private EditText myTitle, myDate, myTime, myDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        //create firebase object
        firebaseBtn = (Button) findViewById(R.id.button);
        database = FirebaseDatabase.getInstance().getReference();

        //create gson object
        Gson gson = new Gson();

        //get touch location from map view
        String coordinates = getIntent().getStringExtra("point");

        //On button click save event object to firebase
        firebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //get event variables from text fields
                myTitle = findViewById(R.id.title);
                myDate = findViewById(R.id.date);
                myTime = findViewById(R.id.time);
                myDesc = findViewById(R.id.description);

                //create an event object and save edit text variables to strings in event object
                Event event = new Event(myTitle.getText().toString(), myDate.getText().toString(),
                                        myTime.getText().toString(), myDesc.getText().toString(), coordinates);

                //push event oject to firebase
                database.child("Event").push().setValue(event);

                //reset text fields
                myTitle.setText("");
                myDate.setText("");
                myTime.setText("");
                myDesc.setText("");

                //switch back to main activity/map view
                Intent intent = new Intent(EventInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}