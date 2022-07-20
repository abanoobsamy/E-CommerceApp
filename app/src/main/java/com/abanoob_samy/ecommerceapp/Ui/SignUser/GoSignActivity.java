package com.abanoob_samy.ecommerceapp.Ui.SignUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.abanoob_samy.ecommerceapp.Ui.Activities.MainActivity;
import com.abanoob_samy.ecommerceapp.databinding.ActivityGoSignBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GoSignActivity extends AppCompatActivity {

    private static final String TAG = "GoSignActivity";
    private ActivityGoSignBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener stateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityGoSignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(GoSignActivity.this, LoginActivity.class));
            }
        });

        binding.btnGoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(GoSignActivity.this, RegisterActivity.class));
            }
        });

        setUpFirebaseAuthentication();
    }

    private void setUpFirebaseAuthentication() {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser fUser = mAuth.getCurrentUser();

                //check if the user logged in

                if (fUser != null) {

                    Log.d(TAG, "onAuthStateChanged: SignIn" + fUser.getUid());
                }
                else {

                    Log.d(TAG, "onAuthStateChanged: SignOut");
                }
            }
        };
    }

    private void setUpStartActivity() {

        Intent intent = new Intent(GoSignActivity.this,
                MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(stateListener);

        //if user is already logged in go to HomeActivity directly.
        if (mAuth.getCurrentUser() != null) {

            setUpStartActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (stateListener != null) {
            mAuth.removeAuthStateListener(stateListener);
        }
    }

}