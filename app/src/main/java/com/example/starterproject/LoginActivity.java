package com.example.starterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        signIn();
    }

    private void signIn() {
        EditText emailEdit = findViewById(R.id.etName);
        EditText passwordEdit = findViewById(R.id.etPassword);

        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, (task) -> {
            if(task.isSuccessful()) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
