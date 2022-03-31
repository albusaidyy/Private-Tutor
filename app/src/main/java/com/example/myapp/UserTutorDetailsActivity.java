package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserTutorDetailsActivity extends AppCompatActivity {

    private TextView mName_Ed;
    private TextView mInstitution_Ed;
    private TextView mSubject_Ed;
    private TextView mPrice_Ed;
    private TextView mAbout_Ed;
    private TextView mgender_spinner;
    private TextView mMobile_Ed;
    private TextView mVlink_Ed;


    private TextView mTutor_status_spinner;


    private Button mRequest;
    private View resource;


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

    private ImageView Tprofile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tutor Details");



        setContentView(R.layout.activity_user_tutor_details);


        resource = findViewById(R.id.resource);

        key=getIntent().getStringExtra("key");
        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        name=getIntent().getStringExtra("first name last name");
        institution=getIntent().getStringExtra("institution");
        subject=getIntent().getStringExtra("subject");
        price=getIntent().getStringExtra("price");
        gender=getIntent().getStringExtra("gender");
        mobile=getIntent().getStringExtra("mobile");
        about=getIntent().getStringExtra("about");
        vLink = getIntent().getStringExtra("vLink");
        status=getIntent().getStringExtra("status");


        Tprofile = findViewById(R.id.imgTutor);
        Tprofile.setImageBitmap(bmp);
        mName_Ed=findViewById(R.id.Ed_name);
        mName_Ed.setText(name);
        mInstitution_Ed=findViewById(R.id.Ed_institution);
        mInstitution_Ed.setText(institution);
        mSubject_Ed=findViewById(R.id.Ed_subject);
        mSubject_Ed.setText(subject);
        mPrice_Ed=findViewById(R.id.Ed_price);
        mPrice_Ed.setText(price);

        mgender_spinner=findViewById(R.id.gender_spinner);
        mgender_spinner.setText(gender);

        mAbout_Ed=findViewById(R.id.Ed_about);
        mAbout_Ed.setText(about);
        mMobile_Ed=findViewById(R.id.Ed_mobile);
        mMobile_Ed.setText(mobile);
        mVlink_Ed = findViewById(R.id.Ed_vlink);
        mVlink_Ed.setText(vLink);





        mTutor_status_spinner=findViewById(R.id.tutor_status_spinner);
        mTutor_status_spinner.setText(status);

        mRequest = findViewById(R.id.request_btn);

        resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });


        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Available".equals(mTutor_status_spinner.getText().toString())){


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mobile));
                startActivity(intent);
            }else {
                    Toast.makeText(UserTutorDetailsActivity.this,"Sorry " + name +" is Not Available at the moment",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private void showdialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserTutorDetailsActivity.this);

        alertDialog.setTitle("Resource");
        alertDialog.setIcon(R.drawable.ic_ondemand_video_24dp); //set icon

        // Setting Dialog Message
        alertDialog.setMessage("Interested in  1-1 Sessions? Watch my Introduction of the course and dont forget to book an appointment afterwards.");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("VIEW", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                WebView webView = findViewById(R.id.webView);
                webView.loadUrl(vLink);

            }

        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        // Showing Alert Message
        alertDialog.show();


    }


}

