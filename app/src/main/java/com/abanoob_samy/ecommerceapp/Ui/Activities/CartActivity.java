package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.Adapters.MyCartAdapter;
import com.abanoob_samy.ecommerceapp.Models.MyCart;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private ActivityCartBinding binding;

    private ArrayList<MyCart> myCartList;
    private MyCartAdapter myCartAdapter;

    private int overAllTotalAmount;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        binding.buyNowDetails.setVisibility(View.GONE);

        //to get data from MyCartAdapter to CartActivity.
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        myCartList = new ArrayList<>();

        myCartAdapter = new MyCartAdapter(this, myCartList);

        binding.recyclerCart.setHasFixedSize(true);
        binding.recyclerCart.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recyclerCart.setAdapter(myCartAdapter);

        db.collection("AddToCart")
                .document(mAuth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult().getDocuments()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                MyCart myCart = document.toObject(MyCart.class);
                                myCartList.add(myCart);
                                myCartAdapter.notifyDataSetChanged();
                                binding.buyNowDetails.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            Log.w(TAG, "Error AddToCart getting documents.", task.getException());

                            Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        binding.buyNowDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
//
//                if (newProducts != null) {
//
//                    intent.putExtra("item", newProducts);
//                }
//                else if (allProducts != null) {
//
//                    intent.putExtra("item", allProducts);
//                }
//                else if (seeAll != null) {
//
//                    intent.putExtra("item", seeAll);
//                }
//
//                startActivity(intent);
//            }
//        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalAmount", 0);
            binding.textTotalPrice.setText(String.valueOf(totalBill));
        }
    };
}