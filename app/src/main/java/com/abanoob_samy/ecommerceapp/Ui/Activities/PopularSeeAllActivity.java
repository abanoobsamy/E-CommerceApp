package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.Adapters.AllProductAdapter;
import com.abanoob_samy.ecommerceapp.Models.AllProducts;
import com.abanoob_samy.ecommerceapp.databinding.ActivityPopularSeeAllBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PopularSeeAllActivity extends AppCompatActivity {

    private static final String TAG = "PopularSeeAllActivity";

    private ActivityPopularSeeAllBinding binding;
    private AllProductAdapter mAllProductAdapter;
    private List<AllProducts> allProductsList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopularSeeAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        setUpRecyclerSeeAllAdapter();
    }

    private void setUpRecyclerSeeAllAdapter() {

        allProductsList = new ArrayList<>();

        mAllProductAdapter = new AllProductAdapter(this, allProductsList);

        binding.recyclerSeeAll.setHasFixedSize(true);
        binding.recyclerSeeAll.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerSeeAll.setAdapter(mAllProductAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                AllProducts allProducts = document.toObject(AllProducts.class);
                                allProductsList.add(allProducts);
                                mAllProductAdapter.notifyDataSetChanged();
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