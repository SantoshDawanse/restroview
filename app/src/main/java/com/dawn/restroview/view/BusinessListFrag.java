package com.dawn.restroview.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dawn.restroview.R;
import com.dawn.restroview.controller.BusinessListAdapter;
import com.dawn.restroview.model.AddBusiness;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessListFrag extends Fragment {

    private RecyclerView businessList;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<AddBusiness> businessArrayList;
    private BusinessListAdapter businessListAdapter;

    private TextView noBusinessText;

    public BusinessListFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_list, container, false);

        businessList = view.findViewById(R.id.business_list_rv);

        noBusinessText = view.findViewById(R.id.business_list_null_tv);

        layoutManager = new LinearLayoutManager(getContext());
        businessList.setLayoutManager(layoutManager);

        getBusinessData();

        return view;
    }

    //retrieve business data from firebase realtime database
    private void getBusinessData(){
        FirebaseDatabase.getInstance()
                .getReference("business")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        businessArrayList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String businessName = snapshot.child("businessName").getValue().toString();

                            businessArrayList.add(new AddBusiness(businessName));
                        }

                        if (businessArrayList.size() == 0){
                            businessList.setVisibility(View.GONE);
                            noBusinessText.setVisibility(View.VISIBLE);
                        }
                        businessListAdapter = new BusinessListAdapter(businessArrayList, getContext());
                        businessList.setAdapter(businessListAdapter);
                        businessListAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
