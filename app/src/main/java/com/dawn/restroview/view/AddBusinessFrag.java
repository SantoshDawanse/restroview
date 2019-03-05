package com.dawn.restroview.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dawn.restroview.R;
import com.dawn.restroview.adminfrags.GeneralFrag;
import com.dawn.restroview.model.AddBusiness;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddBusinessFrag extends Fragment {

    private EditText addBusinessName, addBusinessID, addBusinessAddress;
    private Button addBusinessBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private GeneralFrag generalFrag;

    public AddBusinessFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_business, container, false);

        generalFrag = new GeneralFrag();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("business");

        addBusinessName = view.findViewById(R.id.add_business_name);
        addBusinessID = view.findViewById(R.id.add_business_id);
        addBusinessAddress = view.findViewById(R.id.add_busiess_address);
        addBusinessBtn = view.findViewById(R.id.add_business_save);

        addBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBusiness(addBusinessName.getText().toString(), addBusinessID.getText().toString(), addBusinessAddress.getText().toString());
            }
        });
        return view;
    }

    private void addBusiness(String name, String regCode, String address) {

        String userID = mAuth.getCurrentUser().getUid();
        String businessID = databaseReference.child(userID).push().getKey();

        final AddBusiness addBusiness = new AddBusiness(businessID, name, regCode, address);


        databaseReference.child(userID).child(businessID).setValue(addBusiness)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addBusinessName.setText(null);
                        addBusinessID.setText(null);
                        addBusinessAddress.setText(null);
                        Toast.makeText(getContext(), "Business registration process has begun \n you will be contacted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
