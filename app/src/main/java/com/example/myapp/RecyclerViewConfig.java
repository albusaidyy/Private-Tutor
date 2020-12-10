package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RecyclerViewConfig {

    private Context mContext;
    private TutorAdapter mTutorsAdapter;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceTutors;

    public void setConfig(RecyclerView recyclerView, Context context,List<Tutor>tutors,List<String> keys){
        mContext=context;
        mTutorsAdapter=new TutorAdapter(tutors,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mTutorsAdapter);
    }
    class TutorItemView extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mInstitution;
        private TextView mSubject;
        private TextView mStatus;
        private TextView mPrice;
        private TextView mGender;
        private TextView mMobile;
        private TextView mAbout;
        private TextView mLink;
        private ImageView Tprofile;

        private ProgressBar pload;

        private String key;
        //taking the layout design
        public TutorItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.tutor_list_item, parent, false));

            pload = itemView.findViewById(R.id.progressbar);
            mName = (TextView) itemView.findViewById(R.id.Tv_name);
            mInstitution = (TextView) itemView.findViewById(R.id.Tv_institution);
            mSubject = (TextView) itemView.findViewById(R.id.Tv_subject);
            mPrice=itemView.findViewById(R.id.Tv_price);
            mGender=itemView.findViewById(R.id.Tv_gender);
            mMobile=itemView.findViewById(R.id.Tv_Mobile);
            mAbout=itemView.findViewById(R.id.Tv_About);
            mLink= itemView.findViewById(R.id.Tv_vLink);

            Tprofile = itemView.findViewById(R.id.Tpic);

            mStatus =  itemView.findViewById(R.id.Tv_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(mContext,TutorDetailsActivity.class);
                    intent.putExtra("key",key);

                    Drawable mDrawable = Tprofile.getDrawable();
                    Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    intent.putExtra("image", bytes); //put bitmap image as array of bytes
                     intent.putExtra("first name last name",mName.getText().toString());
                    intent.putExtra("institution",mInstitution.getText().toString());
                    intent.putExtra("subject",mSubject.getText().toString());
                    intent.putExtra("status",mStatus.getText().toString());
                    intent.putExtra("price",mPrice.getText().toString());
                    intent.putExtra("gender",mGender.getText().toString());
                    intent.putExtra("mobile",mMobile.getText().toString());
                    intent.putExtra("about",mAbout.getText().toString());
                    intent.putExtra("vLink",mLink.getText().toString());


                    mContext.startActivity(intent);


                }
            });


        }

        public void bind(Tutor tutor, String key) {

            mDatabase= FirebaseDatabase.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference = firebaseStorage.getReference();
            mReferenceTutors=mDatabase.getReference("tutors");
            mReferenceTutors.push().getKey();
            //reading profile image
            StorageReference imageRef = storageReference.child("images").child("Tutors").child(key).child("Tutor pic");// pic.png
            Task<Uri> downUri = imageRef.getDownloadUrl();
            downUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(Tprofile);

                }
            });

            mName.setText(tutor.getName());
            mInstitution.setText(tutor.getInstitution());
            mSubject.setText(tutor.getSubject());
            mStatus.setText(tutor.getStatus());
            mPrice.setText(tutor.getPrice());
            mGender.setText(tutor.getGender());
            mMobile.setText(tutor.getMobile());
            mAbout.setText(tutor.getAbout());
            mLink.setText(tutor.getvLink());

            this.key = key;
        }
    }
        class TutorAdapter extends RecyclerView.Adapter<TutorItemView> {
            private List<Tutor> mTutorList;
            private List<String> mKeys;

            public TutorAdapter(List<Tutor> mTutorList, List<String> mKeys) {
                this.mTutorList = mTutorList;
                this.mKeys = mKeys;
            }

            @NonNull
            @Override
            public TutorItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new TutorItemView(parent);
            }

            @Override
            public void onBindViewHolder(@NonNull TutorItemView holder, int position) {
                holder.bind(mTutorList.get(position),mKeys.get(position));

            }

            @Override
            public int getItemCount() {
                return mTutorList.size();
            }
        }




    }



