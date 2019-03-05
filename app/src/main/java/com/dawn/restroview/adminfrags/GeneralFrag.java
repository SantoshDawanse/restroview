package com.dawn.restroview.adminfrags;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.dawn.restroview.view.AddBusinessFrag;
import com.dawn.restroview.R;
import com.dawn.restroview.view.BusinessListFrag;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralFrag extends Fragment {

    private ImageView profileImage, profileSignOut;
    private TextView profileName;
    private Spinner profileBusinesses;

    private Switch addBusinessSwitch;

    private FloatingActionButton addBusinessFab;

    private FirebaseAuth mAuth;


    public GeneralFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        mAuth = FirebaseAuth.getInstance();

        profileImage = view.findViewById(R.id.general_profile_image);
        profileName = view.findViewById(R.id.general_profile_name);

        profileSignOut = view.findViewById(R.id.general_profile_sign_out);
        //for multiple businesses
        profileBusinesses = view.findViewById(R.id.general_profile_businesses);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.profile_businesses, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        profileBusinesses.setAdapter(adapter);

        loadBusinesses(new BusinessListFrag());

        //floating action button
        addBusinessSwitch = view.findViewById(R.id.general_add_business);

        addBusinessSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    loadBusinesses(new AddBusinessFrag());
                } else {
                    loadBusinesses(new BusinessListFrag());
                }
            }
        });

        return view;
    }

    private void loadBusinesses(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.general_container, fragment);
        fragmentTransaction.commit();
    }
}
