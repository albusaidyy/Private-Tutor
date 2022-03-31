package com.example.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

public class TutorDetailsActivity extends AppCompatActivity {

    private EditText mName_Ed;
    private EditText mInstitution_Ed;
    private EditText mSubject_Ed;
    private EditText mPrice_Ed;
    private EditText mAbout_Ed;
    private Spinner mgender_spinner;
    private EditText mMobile_Ed;
    private EditText mLink_Ed;






    private Spinner mTutor_status_spinner;

    private Button mUpdate_btn;
    private Button mDelete_btn;


    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceTutors;


    private String key;
    private String name;
    private String institution;
    private String subject;
    private String price;
    private String gender;
    private String mobile;
    private String about;
private String vLink;
    private String status;

    private FirebaseAuth mAuth;

    Uri imagePath;

    private ImageView mTutor_Img;

    private int PICK_IMAGE = 123;


    //converts image to uri after picking it from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null);
        {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                mTutor_Img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Tutor");
        setContentView(R.layout.activity_tutor_details);
        mAuth = FirebaseAuth.getInstance();


        //gets data fom the itemviews
        key=getIntent().getStringExtra("key");
        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        name = getIntent().getStringExtra("first name last name");
        institution = getIntent().getStringExtra("institution");
        subject = getIntent().getStringExtra("subject");
        price = getIntent().getStringExtra("price");
        gender = getIntent().getStringExtra("gender");
        mobile = getIntent().getStringExtra("mobile");
        about = getIntent().getStringExtra("about");
        status = getIntent().getStringExtra("status");
        vLink = getIntent().getStringExtra("vLink");

        //assigning previous view data to current view data
        mTutor_Img = findViewById(R.id.imgTutor);
        mTutor_Img.setImageBitmap(bmp);

        mName_Ed = findViewById(R.id.Ed_name);
        mName_Ed.setText(name);
        mInstitution_Ed = findViewById(R.id.Ed_institution);
        mInstitution_Ed.setText(institution);
        mSubject_Ed = findViewById(R.id.Ed_subject);
        mSubject_Ed.setText(subject);
        mPrice_Ed = findViewById(R.id.Ed_price);
        mPrice_Ed.setText(price);

        mgender_spinner = findViewById(R.id.gender_spinner);
        mgender_spinner.setSelection(getIndex_SpinnerItem(mgender_spinner, gender));

        mAbout_Ed = findViewById(R.id.Ed_about);
        mAbout_Ed.setText(about);
        mMobile_Ed = findViewById(R.id.Ed_mobile);
        mMobile_Ed.setText(mobile);

        mLink_Ed = findViewById(R.id.Ed_link);
        mLink_Ed.setText(vLink);


        mTutor_status_spinner = findViewById(R.id.tutor_status_spinner);
        mTutor_status_spinner.setSelection(getIndex_SpinnerItem(mTutor_status_spinner, status));

        mUpdate_btn = findViewById(R.id.update_btn);
        mDelete_btn = findViewById(R.id.delete_btn);

        mTutor_Img = findViewById(R.id.imgTutor);




        //picking the image from  gallery
        mTutor_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);

            }
        });




        //updating tutor details
        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                mDatabase= FirebaseDatabase.getInstance();
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference();
                mReferenceTutors=mDatabase.getReference("tutors");
                mReferenceTutors.push().getKey();

                StorageReference imageRef = storageReference.child("images").child("Tutors").child(key).child("Tutor pic");// pic.png
                if (imagePath != null) {
                UploadTask uploadTask = imageRef.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TutorDetailsActivity.this,"Image Upload Failed",Toast.LENGTH_SHORT).show();


                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(TutorDetailsActivity.this,"Image Successfully added",Toast.LENGTH_SHORT).show();
                    }
                });}

                Tutor tutor = new Tutor();
                tutor.setName(mName_Ed.getText().toString());
                tutor.setInstitution(mInstitution_Ed.getText().toString());
                tutor.setSubject(mSubject_Ed.getText().toString());
                tutor.setPrice(mPrice_Ed.getText().toString());
                tutor.setMobile(mMobile_Ed.getText().toString());
                tutor.setGender(mgender_spinner.getSelectedItem().toString());
                tutor.setAbout(mAbout_Ed.getText().toString());
                tutor.setvLink(mLink_Ed.getText().toString());
                tutor.setStatus(mTutor_status_spinner.getSelectedItem().toString());

                new FirebaseDatabaseHelper().updateTutor(key, tutor, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Tutor> tutors, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(TutorDetailsActivity.this, "Tutor record " +
                                "updated successfully", Toast.LENGTH_LONG).show();
                        finish();
                        return;

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        //deleting data button
        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteTutor(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Tutor> tutors, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        {
                            Toast.makeText(TutorDetailsActivity.this, "Tutor record " +
                                    "deleted successfully", Toast.LENGTH_LONG).show();
                            finish();
                            return;

                        }

                    }
                });
            }
        });

    }



    //selecting value on spinner
    private int getIndex_SpinnerItem(Spinner spinner, String item) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }


}
