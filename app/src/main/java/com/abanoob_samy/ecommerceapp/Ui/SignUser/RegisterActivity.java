package com.abanoob_samy.ecommerceapp.Ui.SignUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.Ui.Activities.MainActivity;
import com.abanoob_samy.ecommerceapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener stateListener;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpFirebaseAuthentication();

        binding.tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        mSharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        boolean isFirstTime = mSharedPreferences.getBoolean("firstTime", true);

        if (isFirstTime) {

            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        setUpSignUp();
    }

    private void setUpSignUp() {

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = binding.edUsernameRegister.getText().toString();
                String email = binding.edEmailRegister.getText().toString();
                String password = binding.edPasswordRegister.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter User Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "Successfully Registration.",
                                            Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }
                                else {

                                    Toast.makeText(getApplicationContext(), "Registration Failed! "
                                                    + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
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

        Intent intent = new Intent(RegisterActivity.this,
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