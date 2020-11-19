package com.example.hmqcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.hmqcoffee.LoginActivity.mAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageButton btnbackreset;
    Button btnreset;
    TextInputEditText edtgmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        AnhXa();

        btnbackreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = String.valueOf(edtgmail.getText());
                mAuth.sendPasswordResetEmail(a).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ForgotPasswordActivity.this, "Check your Email to reset password!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void AnhXa() {
        btnbackreset = (ImageButton) findViewById(R.id.btnBackReset);
        btnreset = (Button)findViewById(R.id.btn_resetpass);
        edtgmail = (TextInputEditText)findViewById(R.id.edt_resetpass);
    }
}