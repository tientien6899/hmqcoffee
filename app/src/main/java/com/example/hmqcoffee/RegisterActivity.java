package com.example.hmqcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    TextView tvtClickhere;
    Button btnregister;
    ImageButton btnbackregister;
    TextInputEditText edtemail, edtpassword;
    TextInputLayout emailwrapper, passwordwrapper;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        AnhXa();

        edtemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    emailwrapper.setError("Your Email is not null");
                } else {
                    emailwrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    passwordwrapper.setError("Your Password is not null");
                } else {
                    passwordwrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvtClickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dangky();
            }
        });

        btnbackregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, HelloActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Dangky() {
        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email + "@gmail.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void AnhXa() {
        edtemail = findViewById(R.id.edtEmail);
        emailwrapper = findViewById(R.id.EmailWrapper);
        edtpassword = findViewById(R.id.edtPassword);
        passwordwrapper = findViewById(R.id.PasswordWrapper);
        tvtClickhere = findViewById(R.id.txtClickhere);
        btnregister = (Button) findViewById(R.id.btnRegister);
        btnbackregister = (ImageButton) findViewById(R.id.btnBackRegister);
    }
}