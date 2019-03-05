package com.dawn.restroview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private TextView adminEmail, noOfBusinesses, defaultBusiness;
    private Spinner businessSpinner;

    private ArrayList<String> businessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        adminEmail = findViewById(R.id.settings_admin_email);
        noOfBusinesses = findViewById(R.id.settings_no_businesses);
        defaultBusiness = findViewById(R.id.settings_default_business);

        businessSpinner = findViewById(R.id.settings_business_spinner);

        //setting up the data
        adminEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        //getting business details from database
        getBusinessData();

    }

    private void getBusinessData() {
        FirebaseDatabase.getInstance().getReference("business")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        businessList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String businessName = snapshot.child("businessName").getValue().toString();

                            businessList.add(businessName);
                        }

                        noOfBusinesses.setText(String.valueOf(businessList.size()));
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, businessList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        businessSpinner.setAdapter(adapter);

                        defaultBusiness.setText(businessSpinner.getSelectedItem().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
