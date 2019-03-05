package com.dawn.restroview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText businessPerson, businessContact, businessEmail, businessPassword, businessConfirmPass;
    private Button registerBtn;

    private LinearLayout registerLayout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        registerLayout = findViewById(R.id.register_layout);

        businessPerson = findViewById(R.id.register_person_et);
        businessContact = findViewById(R.id.register_contact_et);
        businessEmail = findViewById(R.id.register_email_et);
        businessPassword = findViewById(R.id.register_password_et);
        businessConfirmPass = findViewById(R.id.register_confirm_password_et);
        registerBtn = findViewById(R.id.register_register_btn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()){
                    registerBusiness();
                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerBusiness(){

        final String person = businessPerson.getText().toString();
        final String contact = businessContact.getText().toString();
        final String email = businessEmail.getText().toString();
        String password = businessPassword.getText().toString();
        String confirmPassword = businessConfirmPass.getText().toString();


        if (person.isEmpty()){
            businessPerson.setError("contact person is required");
            businessPerson.requestFocus();
            return;
        }

        if (contact.isEmpty()){
            businessContact.setError("contact is required");
            businessContact.requestFocus();
            return;
        }

        if (email.isEmpty()){
            businessEmail.setError("email is required");
            businessEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            businessPassword.setError("password is required");
            businessPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            businessPassword.setError("password must have at least 6 characters");
            businessPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()){
            businessConfirmPass.setError("Confirm password is required");
            businessConfirmPass.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)){
            businessConfirmPass.setError("Password mismatch");
            businessConfirmPass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Map<String, Object> user = new HashMap<>();

                            user.put("person", person);
                            user.put("contact", contact);
                            user.put("email", email);

                            firestore.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
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
                                    });
                        }
                    }
                });
    }

    private boolean checkInternetConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
