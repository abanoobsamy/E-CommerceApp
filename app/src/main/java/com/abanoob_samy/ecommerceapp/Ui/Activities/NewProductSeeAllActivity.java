package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.Adapters.NewProductAdapter;
import com.abanoob_samy.ecommerceapp.Models.NewProducts;
import com.abanoob_samy.ecommerceapp.databinding.ActivityNewProductSeeAllBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewProductSeeAllActivity extends AppCompatActivity {

    private static final String TAG = "NewProductSeeAllActivity";

    private ActivityNewProductSeeAllBinding binding;
    private NewProductAdapter mNewProductAdapter;
    private List<NewProducts> newProductsList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewProductSeeAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        setUpRecyclerSeeAllAdapter();
    }

    private void setUpRecyclerSeeAllAdapter() {

        newProductsList = new ArrayList<>();

        mNewProductAdapter = new NewProductAdapter(this, newProductsList);

        binding.recyclerSeeAll.setHasFixedSize(true);
        binding.recyclerSeeAll.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerSeeAll.setAdapter(mNewProductAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                NewProducts seeAll = document.toObject(NewProducts.class);
                                newProductsList.add(seeAll);
                                mNewProductAdapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            Log.w(TAG, "Error NewProducts getting documents.", task.getException());

                            Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}