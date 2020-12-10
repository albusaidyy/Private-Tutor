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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText EdEmail;
    private EditText EdPassword;
    private Button btnSignin;
    private TextView Tvsignup;

    private FirebaseAuth mAuth;
    private ProgressDialog mDiag;
    private TextView TvresetPassword;
    private String admin="PZNESxIT0hMGu498G4yCkCgvX6z2";

    //if user has already logged in
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() !=null){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();


        mDiag=new ProgressDialog(this);

        EdEmail=findViewById(R.id.email_login);
        EdPassword=findViewById(R.id.password_login);
        btnSignin=findViewById(R.id.btn_signin);
        Tvsignup=findViewById(R.id.Tv_signup);
        TvresetPassword = findViewById(R.id.Tv_RestPass);

        //for log in
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=EdEmail.getText().toString().trim();
                String password=EdPassword.getText().toString().trim();

                if (email.isEmpty()){
                    EdEmail.setError("Required Field..");
                    EdEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    EdPassword.setError("Required Field..");
                    EdPassword.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    EdEmail.setError("Please enter a valid Email");
                    EdEmail.requestFocus();
                    return;
                }

                if (password.length()<6){
                    EdPassword.setError("Minimum Length of password should be 6");
                    EdPassword.requestFocus();
                    return;
                }
                mDiag.setMessage("Processing..");
                mDiag.show();
                mDiag.setCancelable(false);




                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mDiag.dismiss();
                            if( mAuth.getUid().equals(admin)){
                                startActivity(new Intent(LoginActivity.this, TutorListActivity.class));
                                finish();
                            }else{
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                              finish();
                            Toast.makeText(LoginActivity.this,"Log In successful",Toast.LENGTH_SHORT).show();

                            }

                        }else{
                            mDiag.dismiss();
                            String message = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this,"Error occurred: " + message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });





        //for signup
        Tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        TvresetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActicvity.class));

            }
        });


    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        LoginActivity.this.finish();
    }
}
