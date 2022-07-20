package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.Adapters.SeeAllAdapter;
import com.abanoob_samy.ecommerceapp.Models.SeeAll;
import com.abanoob_samy.ecommerceapp.databinding.ActivitySeeAllBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SeeAllActivity extends AppCompatActivity {

    private static final String TAG = "SeeAllActivity";

    private ActivitySeeAllBinding binding;
    private SeeAllAdapter mSeeAllAdapter;
    private List<SeeAll> seeAllList;

    private FirebaseFirestore db;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        type = getIntent().getStringExtra("typeCategory");

        setUpRecyclerSeeAllAdapter();
    }

    private void setUpRecyclerSeeAllAdapter() {

        seeAllList = new ArrayList<>();

        mSeeAllAdapter = new SeeAllAdapter(this, seeAllList);

        binding.recyclerSeeAll.setHasFixedSize(true);
        binding.recyclerSeeAll.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerSeeAll.setAdapter(mSeeAllAdapter);

        if (type == null && type.isEmpty()) {

            db.collection("SeeAll")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    SeeAll seeAll = document.toObject(SeeAll.class);
                                    seeAllList.add(seeAll);
                                    mSeeAllAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.w(TAG, "Error SeeAll getting documents.", task.getException());

                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("camera collection")) {

            db.collection("SeeAll")
                    .whereEqualTo("type", "camera collection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    SeeAll seeAll = document.toObject(SeeAll.class);
                                    seeAllList.add(seeAll);
                                    mSeeAllAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.w(TAG, "Error SeeAll getting documents.", task.getException());

                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else if (type != null && type.equalsIgnoreCase("shoes collection")) {

            db.collection("SeeAll")
                    .whereEqualTo("type", "shoes collection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    SeeAll seeAll = document.toObject(SeeAll.class);
                                    seeAllList.add(seeAll);
                                    mSeeAllAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.w(TAG, "Error SeeAll getting documents.", task.getException());

                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else if (type != null && type.equalsIgnoreCase("women collection")) {

            db.collection("SeeAll")
                    .whereEqualTo("type", "women collection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    SeeAll seeAll = document.toObject(SeeAll.class);
                                    seeAllList.add(seeAll);
                                    mSeeAllAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.w(TAG, "Error SeeAll getting documents.", task.getException());

                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else if (type != null && type.equalsIgnoreCase("watch collection")) {

            db.collection("SeeAll")
                    .whereEqualTo("type", "watch collection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    SeeAll seeAll = document.toObject(SeeAll.class);
                                    seeAllList.add(seeAll);
                                    mSeeAllAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.w(TAG, "Error SeeAll getting documents.", task.getException());

                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else if (type != null && type.equalsIgnoreCase("men collection")) {

            db.collection("SeeAll")
                    .whereEqualTo("type", "men collection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    SeeAll seeAll = document.toObject(SeeAll.class);
                                    seeAllList.add(seeAll);
                                    mSeeAllAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.w(TAG, "Error SeeAll getting documents.", task.getException());

                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else if (type != null && type.equalsIgnoreCase("kids clothing collection")) {

            db.collection("SeeAll")
                    .whereEqualTo("type", "kids clothing collection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    SeeAll seeAll = document.toObject(SeeAll.class);
                                    seeAllList.add(seeAll);
                                    mSeeAllAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.w(TAG, "Error SeeAll getting documents.", task.getException());

                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}