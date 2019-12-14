package com.example.starterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity{// implements View.OnClickListener {

    private FirebaseAuth auth;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create a Firebase Authorization object for Sign-in and Registration
        auth = FirebaseAuth.getInstance();

    }

    // The Sign-in functionality
    public void signIn(View view) {
        // Get email from text field
        EditText emailEdit = findViewById(R.id.etName);
        String email = emailEdit.getText().toString();

        // Get password from text field
        EditText passwordEdit = findViewById(R.id.etPassword);
        String password = passwordEdit.getText().toString();

        // Talk to Firebase and authenticate login information
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, (task) -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent); // Start the main activity, which brings us to the map
                finish();
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show(); // Lets us know if
            }   // the user doesn't exists or if they typed something in wrong.
        });
    }

    // The User creation functionality
    public void createUser(View view) {

        // Get email from text field
        EditText emailEdit = findViewById(R.id.etName);
        String email = emailEdit.getText().toString();

        // Get password from text field
        EditText passwordEdit = findViewById(R.id.etPassword);
        String password = passwordEdit.getText().toString();

        // Start the user registration process with information from text fields
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, (task) -> {
            {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent); // Start the main activity, which brings us to the map if user creation
                    finish();              // was successful
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show(); // Lets us know if
                }   // the user doesn't exists or if they typed something in wrong.
            }
        });
    }
}
