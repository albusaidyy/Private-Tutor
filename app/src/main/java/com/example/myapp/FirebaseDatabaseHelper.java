package com.example.myapp;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceTutors;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Uri imagePath;



    private List<Tutor> tutors= new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Tutor> tutors, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {

        mDatabase=FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        mReferenceTutors=mDatabase.getReference("tutors");
    }


    //Reading Tutors from Database;
    public void readTutors(final DataStatus dataStatus){
        mReferenceTutors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tutors.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Tutor tutor=keyNode.getValue(Tutor.class);
                    tutors.add(tutor);
                }
                dataStatus.DataIsLoaded(tutors,keys);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Adding Tutors to Firebase
    public void addTutor(Tutor tutor, final DataStatus dataStatus){
        String key=mReferenceTutors.push().getKey();
        mReferenceTutors.child(key).setValue(tutor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                dataStatus.DataIsInserted();
            }
        });
    }
    //Updating Tutors
    public void updateTutor(String key,Tutor tutor, final DataStatus dataStatus){
        mReferenceTutors.child(key).setValue(tutor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    //Deleting Tutors
    public void deleteTutor(String key,final DataStatus dataStatus){
        mReferenceTutors.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }

}
