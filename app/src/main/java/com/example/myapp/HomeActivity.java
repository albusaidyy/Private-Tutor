package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        setContentView(R.layout.activity_home);


        mAuth=FirebaseAuth.getInstance();

        mRecyclerView=findViewById(R.id.recycleView_tutors);






        new FirebaseDatabaseHelper().readTutors(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Tutor> tutors, List<String> keys) {
                findViewById(R.id.loading_tutors_pb).setVisibility(View.GONE);
                new RecyclerViewConfig2().setConfig(mRecyclerView,HomeActivity.this,tutors,keys);

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {


            case R.id.action_home:
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                return true;


            case R.id.navigation_profile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;

            case R.id.log_out:
                logout();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void logout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);

        alertDialog.setTitle("LogOut");
        alertDialog.setIcon(R.drawable.ic_logout_24dp); //set icon

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to Log out?");
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
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


    @Override

    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);

        alertDialog.setTitle("Exit");
        alertDialog.setIcon(R.drawable.ic_warning_black_24dp); //set icon

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to Exit?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.finish();
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

