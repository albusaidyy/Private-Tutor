package com.example.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditProfleActivity extends AppCompatActivity {

    private EditText newUserName;
    private EditText newEdlevel;
    private EditText newPhoneNumber;
    private EditText newAdress;
    private TextView ChangePass;

    private ImageView userProfile;
    private Button save;

    private static int PICK_IMAGE = 123;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Uri imagePath;

    //converts image to uri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) ;
        {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfile.setImageBitmap(bitmap);
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
        actionBar.setTitle("Edit Profile");
        setContentView(R.layout.activity_edit_profle);

        newUserName = findViewById(R.id.EdNameUpdate);
        newEdlevel = findViewById(R.id.EdLevelUpdate);
        newPhoneNumber = findViewById(R.id.EdPhoneUpdate);
        newAdress = findViewById(R.id.EdAddressUpdate);
        userProfile = findViewById(R.id.profile);
        ChangePass = findViewById(R.id.TvchangePass);


        save = findViewById(R.id.btnsaveUpdate);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        //picking the image from  gallery
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);

            }
        });


        //reading the userdata
        final DatabaseReference myRef = firebaseDatabase.getReference("Users/" + mAuth.getUid());
        final StorageReference imageRef = storageReference.child("images").child("Users").child(mAuth.getUid()).child("Profile pic");// userId/images/profile_pic.png

        //reading the profile image from storage


            Task<Uri> downUri = imageRef.getDownloadUrl();
            downUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(userProfile);

                }
            });

        //updating the profile
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newUserName.setText(userProfile.getUserName());
                newEdlevel.setText(userProfile.getEducLevel());
                newPhoneNumber.setText(userProfile.getUserPhone());
                newAdress.setText(userProfile.getUserAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfleActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = newUserName.getText().toString().trim();
                String Edlevel = newEdlevel.getText().toString().trim();
                String phone = newPhoneNumber.getText().toString().trim();
                String address = newAdress.getText().toString().trim();

                UserProfile userProfile = new UserProfile(name, Edlevel, phone, address);
                myRef.setValue(userProfile);


                    //uploading image
                if (imagePath != null) {
                    UploadTask uploadTask = imageRef.putFile(imagePath);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfleActivity.this, "Image update failed", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditProfleActivity.this, "Image Successfully added", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Toast.makeText(EditProfleActivity.this,"Profile updated",Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent (EditProfleActivity.this,ChangePassword.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
