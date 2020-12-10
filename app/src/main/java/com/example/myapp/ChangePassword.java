package com.example.myapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog mDiag;
    private EditText mNewPass;
    private EditText mConPass;
    private Button mChangePass;

    private String ConfirmPass;
    private String mPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Change Password");

        mAuth = FirebaseAuth.getInstance();
        mDiag = new ProgressDialog(this);

        mNewPass = findViewById(R.id.passswordChageEd);
        mConPass = findViewById(R.id.passswordConfirm);
        mChangePass = findViewById(R.id.btn_Pass_change);




        mChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPass = mNewPass.getText().toString().trim();
                ConfirmPass = mConPass.getText().toString().trim();

                if (TextUtils.isEmpty(mPass)) {
                    mNewPass.setError("Required Field..");
                    mNewPass.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(ConfirmPass)) {
                    mConPass.setError("Required Field...");
                    mConPass.requestFocus();
                    return;

                }
                if (!mPass.equals(ConfirmPass)) {
                    mConPass.setError("Passwords do not match");
                    mConPass.requestFocus();
                    return;
                }
                if (mPass.length() < 6) {
                    mNewPass.setError("Minimum Length of password should be 6");
                    mNewPass.requestFocus();
                    return;
                }
                if (mAuth.getCurrentUser() != null) {
                    mDiag.setMessage("Changing Password.. Please wait.");
                    mDiag.show();
                    mDiag.setCancelable(false);

                    mAuth.getCurrentUser().updatePassword(mPass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mDiag.dismiss();
                                        Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        mDiag.dismiss();
                                        Toast.makeText(ChangePassword.this, "Error changing password", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }


            }
        });


    }

}
