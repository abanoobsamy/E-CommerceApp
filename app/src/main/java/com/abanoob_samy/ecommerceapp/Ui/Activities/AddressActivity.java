package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abanoob_samy.ecommerceapp.Adapters.AddressAdapter;
import com.abanoob_samy.ecommerceapp.Models.Address;
import com.abanoob_samy.ecommerceapp.Models.AllProducts;
import com.abanoob_samy.ecommerceapp.Models.NewProducts;
import com.abanoob_samy.ecommerceapp.Models.SeeAll;
import com.abanoob_samy.ecommerceapp.databinding.ActivityAddressBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    private static final String TAG = "AddressActivity";

    @Override
    public void setSelectedAddress(String address) {

        mAddress = address;
    }

    private ActivityAddressBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private List<Address> addressList;
    private AddressAdapter mAddressAdapter;
    private String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Object obj = getIntent().getSerializableExtra("item");

        binding.addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });

        binding.paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double amount = 0.0;

                if (obj instanceof NewProducts) {

                    NewProducts newProducts = (NewProducts) obj;
                    amount = newProducts.getPrice();
                }
                else if (obj instanceof AllProducts) {

                    AllProducts allProducts = (AllProducts) obj;
                    amount = allProducts.getPrice();
                }
                else if (obj instanceof SeeAll) {

                    SeeAll seeAll = (SeeAll) obj;
                    amount = seeAll.getPrice();
                }

                Intent intent = new Intent(AddressActivity.this, CheckPaymentActivity.class);
                intent.putExtra("amount", amount);
                startActivity(intent);
            }
        });

        addressList = new ArrayList<>();

        mAddressAdapter = new AddressAdapter(this, addressList, this);

        binding.addressRecycler.setHasFixedSize(true);
        binding.addressRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.addressRecycler.setAdapter(mAddressAdapter);

        db.collection("CurrentUser")
                .document(mAuth.getCurrentUser().getUid())
                .collection("Address")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult().getDocuments()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Address address = document.toObject(Address.class);
                                addressList.add(address);
                                mAddressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}