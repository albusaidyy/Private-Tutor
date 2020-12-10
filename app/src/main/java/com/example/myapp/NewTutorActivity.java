package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class NewTutorActivity extends AppCompatActivity {

    private EditText mName_Ed;
    private EditText mInstitution_Ed;
    private EditText mSubject_Ed;
    private EditText mPrice_Ed;
    private EditText mAbout_Ed;
    private Spinner mGender_Spinner;
    private EditText mMobile_Ed;
    private Spinner mTutor_status_spinner;
    private EditText mLink_Ed;
    private Button mAdd_btn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add");
        setContentView(R.layout.activity_new_tutor);

        mName_Ed = findViewById(R.id.Ed_name);
        mInstitution_Ed = findViewById(R.id.Ed_institution);
        mSubject_Ed = findViewById(R.id.Ed_subject);
        mPrice_Ed = findViewById(R.id.Ed_price);
        mAbout_Ed = findViewById(R.id.Ed_about);
        mGender_Spinner = findViewById(R.id.gender_spinner);
        mMobile_Ed = findViewById(R.id.Ed_mobile);
        mTutor_status_spinner = findViewById(R.id.tutor_status_spinner);
        mLink_Ed = findViewById(R.id.Ed_link);



        mAdd_btn = findViewById(R.id.add_btn);







    mAdd_btn.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        String Cname = mName_Ed.getText().toString().trim();
        String CInstitution = mInstitution_Ed.getText().toString().trim();
        String CAbout = mAbout_Ed.getText().toString().trim();
        String CSubject = mSubject_Ed.getText().toString().trim();
        String CMobile = mMobile_Ed.getText().toString().trim();
        String CPrice = mPrice_Ed.getText().toString().trim();
        String CLink = mLink_Ed.getText().toString().trim();


            if (TextUtils.isEmpty(Cname)) {
                mName_Ed.setError("Required Field..");
                mName_Ed.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(CInstitution)) {
                mInstitution_Ed.setError("Required Field..");
                mInstitution_Ed.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(CAbout)) {
                mAbout_Ed.setError("Required Field..");
                mAbout_Ed.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(CSubject)) {
                mSubject_Ed.setError("Required Field..");
                mSubject_Ed.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(CMobile)) {
                mMobile_Ed.setError("Required Field..");
                mMobile_Ed.requestFocus();
                return;
            }
            if (!Patterns.PHONE.matcher(CMobile).matches()){
                mMobile_Ed.setError("Enter a valid Phone Number..");
                mMobile_Ed.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(CPrice)) {
                mPrice_Ed.setError("Required Field..");
                mPrice_Ed.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(CLink)){
                mLink_Ed.setError("Required Field..");
                mLink_Ed.requestFocus();
                return;
            }
            if (!Patterns.WEB_URL.matcher(CLink).matches()){
                mLink_Ed.setError("Required Field..");
                mLink_Ed.requestFocus();
                return;
            }


        Tutor tutor = new Tutor();
        tutor.setName(mName_Ed.getText().toString());
        tutor.setInstitution(mInstitution_Ed.getText().toString());
        tutor.setSubject(mSubject_Ed.getText().toString());
        tutor.setStatus(mTutor_status_spinner.getSelectedItem().toString());
        tutor.setPrice(mPrice_Ed.getText().toString());
        tutor.setAbout(mAbout_Ed.getText().toString());
        tutor.setGender(mGender_Spinner.getSelectedItem().toString());
        tutor.setMobile(mMobile_Ed.getText().toString());
        tutor.setvLink(mLink_Ed.getText().toString());
        new FirebaseDatabaseHelper().addTutor(tutor, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Tutor> tutors, List<String> keys) {

            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(NewTutorActivity.this, "The tutor has " + "been inserted successfully", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
    });



}
}

