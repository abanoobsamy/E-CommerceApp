package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.databinding.ActivityAddAddressBinding;
import com.abanoob_samy.ecommerceapp.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private ActivityAddAddressBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        binding.adAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = binding.adName.getText().toString();
                String userCity = binding.adCity.getText().toString();
                String userAddress = binding.adAddress.getText().toString();
                String userCode = binding.adCode.getText().toString();
                String userNumber = binding.adPhone.getText().toString();

                String final_address = "";

                if (!userName.isEmpty()) {
                    final_address += " " + userName;
                }
                if (!userCity.isEmpty()) {
                    final_address += " " + userCity;
                }
                if (!userAddress.isEmpty()) {
                    final_address += " " + userAddress;
                }
                if (!userCode.isEmpty()) {
                    final_address += " " + userCode;
                }
                if (!userNumber.isEmpty()) {
                    final_address += " " + userNumber;
                }

                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty()
                        && !userCode.isEmpty() && !userNumber.isEmpty()) {

                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress", final_address);

                    db.collection("CurrentUser")
                            .document(mAuth.getCurrentUser().getUid())
                            .collection("Address")
                            .add(map)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Address Added.",
                                                Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(AddAddressActivity.this,
                                                AddAddressActivity.class));
                                    }
                                }
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "Kindly Fill All Fields!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}