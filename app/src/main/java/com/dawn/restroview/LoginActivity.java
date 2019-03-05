package com.dawn.restroview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    LinearLayout linearLayout;

    private ImageView logoImage;
    private EditText userEmail, userPassword;
    private Button loginBtn, registerBtn;
    private TextView forgetPass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        if (isSignedIn()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        linearLayout = findViewById(R.id.login_layout);

        userEmail = findViewById(R.id.login_email_et);
        userPassword = findViewById(R.id.login_password_et);

        loginBtn = findViewById(R.id.login_login_btn);
        forgetPass = findViewById(R.id.login_forget_pass_tv);
        registerBtn = findViewById(R.id.login_register_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }

    private void userLogin(){
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (email.isEmpty()){
            userEmail.setError("email is missing");
            userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            userPassword.setError("password is missing");
            userPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Snackbar.make(linearLayout, "User cannot be signed in!!!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean isSignedIn(){
        return mAuth.getCurrentUser() != null;
    }

}
