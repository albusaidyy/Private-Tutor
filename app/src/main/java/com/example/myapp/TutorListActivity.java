package com.example.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class TutorListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton fabbtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tutor");
        setContentView(R.layout.activity_tutor_list);
        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recycleView_tutors);

        new FirebaseDatabaseHelper().readTutors(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Tutor> tutors, List<String> keys) {
                findViewById(R.id.loading_tutors_pb).setVisibility(View.GONE);
                new RecyclerViewConfig().setConfig(mRecyclerView, TutorListActivity.this, tutors, keys);

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

        fabbtn = findViewById(R.id.fabadd);


        //Add Data
        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TutorListActivity.this, NewTutorActivity.class));


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.admin_home:
                startActivity(new Intent(TutorListActivity.this, TutorListActivity.class));
                return true;

            case R.id.admin_add:
                startActivity(new Intent(TutorListActivity.this, NewTutorActivity.class));
                return true;

            case R.id.admin_log_out:
                mAuth.signOut();
                startActivity(new Intent(TutorListActivity.this, LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override

    public void onBackPressed() {
    // super.onBackPressed();
    // Not calling **super**, disables back button in current screen.
    }
}


