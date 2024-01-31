package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    private EditText signupUser, signupEmail, signupPassword, signupPassword2;
    private Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_signup);


        signupUser = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupPassword2 = findViewById(R.id.signup_password2);
        signupButton = findViewById(R.id.signup_button);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = signupUser.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String passwordConfirm = signupPassword2.getText().toString().trim();

                if (!password.equals(passwordConfirm)){
                    Toast.makeText(Signup.this, "Check Password.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Signup.this, "wooooo.", Toast.LENGTH_SHORT).show();
                    // add signup to firebase here
                }
            }
        });
    }
}