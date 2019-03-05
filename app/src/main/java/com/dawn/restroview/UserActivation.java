package com.dawn.restroview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivation extends AppCompatActivity {

    private EditText privateKeyET;
    private Button submitBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activation);

        mAuth = FirebaseAuth.getInstance();

        privateKeyET = findViewById(R.id.user_activation_private_key);
        submitBtn = findViewById(R.id.user_activation_submit_btn);

        final String privateKey = privateKeyET.getText().toString();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (privateKey.equals("chatpate")){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.signOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.signOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
    }
}
