package com.dawn.restroview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dawn.restroview.model.TapDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView foodLove, foodSad, serviceLove, serviceSad;

    private ImageView companyLogo;
    private TextView showDate, showTime;

    private TapDatabase tapDatabase;

    //firebase firestore
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_design);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //for company logo
        companyLogo = findViewById(R.id.dashboard_company_logo);

        showDate = findViewById(R.id.dashboard_date_tv);
        showTime = findViewById(R.id.dashboard_time_tv);

        showDate.setText(getCurrentDate());
//        showTime.setText(getCurrentTime());

        //for food feedback
        foodLove = findViewById(R.id.dashboard_food_love);
        foodSad = findViewById(R.id.dashboard_food_sad);
        //for service feedback
        serviceLove = findViewById(R.id.dashboard_service_love);
        serviceSad = findViewById(R.id.dashboard_service_sad);

        //database initialization
        tapDatabase = new TapDatabase(getApplicationContext());

        //tap event listener
        companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Admin.class));
            }
        });

        foodLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapDatabase.openDB();
                tapDatabase.insertFoodTapData("love");
                tapDatabase.closeDB();

                Toast.makeText(MainActivity.this, "Thank you for your feedback", Toast.LENGTH_SHORT).show();
            }
        });

        foodSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapDatabase.openDB();
                tapDatabase.insertFoodTapData("sad");
                tapDatabase.closeDB();

                Toast.makeText(MainActivity.this, "Thank you for your feedback", Toast.LENGTH_SHORT).show();
            }
        });

        serviceLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapDatabase.openDB();
                tapDatabase.insertServiceTapData("love");
                tapDatabase.closeDB();

                Toast.makeText(MainActivity.this, "Thank you for your feedback", Toast.LENGTH_SHORT).show();
            }
        });

        serviceSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapDatabase.openDB();
                tapDatabase.insertServiceTapData("sad");
                tapDatabase.closeDB();

                Toast.makeText(MainActivity.this, "Thank you for your feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //getting current time
    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Date date = new Date();
        return timeFormat.format(date);
    }

    //getting current date
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    //inserting data into firebase firestore
    private void insertDataFirestore(String tap){

        Map<String, Object> tapData = new HashMap<>();
        tapData.put("tap", tap);
        tapData.put("date", getCurrentDate());
        tapData.put("time", getCurrentTime());

        /*firebaseFirestore.collection("tap_database")
                .document(FirebaseAuth.getInstance().getUid())
                .collection()
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "User registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "User details registration failed", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }
}
