package com.example.myapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActicvity extends AppCompatActivity {

    private Button resetSendEmailButton;
    private EditText restEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_acticvity);

        mAuth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reset Password ");

        resetSendEmailButton = findViewById(R.id.send_email);
        restEmail = findViewById(R.id.EdEmail);

        resetSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = restEmail.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                    restEmail.setError("Please enter a valid Email");
                    restEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(userEmail)){
                    restEmail.setError("Please enter a valid Email");
                    restEmail.requestFocus();
                    return;
                }else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPasswordActicvity.this, "Please check your Email Account to reset your password", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPasswordActicvity.this,LoginActivity.class));
                                finish();
                            }else {
                                String message = task.getException().getMessage();
                                Toast.makeText(ResetPasswordActicvity.this, "Error Occurred" + message, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


    }
}
