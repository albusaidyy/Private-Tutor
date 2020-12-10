package com.example.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText EdEmail;
    private EditText EdPassword;
    private EditText EdPassword2;
    private TextView Tvsignin;
    private Button btnSignup;
    private TextView enroll;

    String name ="User Name";
    String email;
    String phone = "+254";
    String edlevel = "N/A";
    String address =  "N/A";
    String password, password2;

    //firebase
    private FirebaseAuth mAuth;
    private ProgressDialog mDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registration");
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mDiag = new ProgressDialog(this);

        EdEmail = findViewById(R.id.email_reg);
        EdPassword = findViewById(R.id.password_reg);
        EdPassword2 = findViewById(R.id.ConfirmPassword_reg);
        Tvsignin = findViewById(R.id.Tv_signin);
        btnSignup = findViewById(R.id.btn_signup);
        enroll = findViewById(R.id.Tv_email);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = EdEmail.getText().toString().trim();
                password = EdPassword.getText().toString().trim();
                password2 = EdPassword2.getText().toString().trim();


                if (email.isEmpty()) {
                    EdEmail.setError("Required Field..");
                    EdEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    EdPassword.setError("Required Field..");
                    EdPassword.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EdEmail.setError("Please enter a valid Email");
                    EdEmail.requestFocus();
                    return;
                }

                if (!password.equals(password2)) {
                    EdPassword2.setError("Password do not match..");
                    EdPassword2.requestFocus();
                    return;

                }
                if (password.length() < 6) {
                    EdPassword.setError("Minimum Length of password should be 6");
                    EdPassword.requestFocus();
                    return;
                }
                mDiag.setMessage("Creating Account..");
                mDiag.show();
                mDiag.setCancelable(false);


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserData();
                            mDiag.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                            finish();

                        } else {
                            mDiag.dismiss();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegistrationActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                mDiag.dismiss();
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                });

            }
        });

        Tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"privatetutor@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Enroll as a Tutor");
                intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });


    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users/" + mAuth.getUid());
        UserProfile userProfile = new UserProfile(name,edlevel,phone,address);
        myRef.setValue(userProfile);

    }

}
