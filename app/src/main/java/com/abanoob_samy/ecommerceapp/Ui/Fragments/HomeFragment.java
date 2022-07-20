package com.abanoob_samy.ecommerceapp.Ui.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.Adapters.AllProductAdapter;
import com.abanoob_samy.ecommerceapp.Adapters.CategoryAdapter;
import com.abanoob_samy.ecommerceapp.Adapters.NewProductAdapter;
import com.abanoob_samy.ecommerceapp.Models.AllProducts;
import com.abanoob_samy.ecommerceapp.Models.Category;
import com.abanoob_samy.ecommerceapp.Models.NewProducts;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.Activities.NewProductSeeAllActivity;
import com.abanoob_samy.ecommerceapp.Ui.Activities.PopularSeeAllActivity;
import com.abanoob_samy.ecommerceapp.Ui.Activities.SeeAllActivity;
import com.abanoob_samy.ecommerceapp.databinding.FragmentHomeBinding;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;

    private CategoryAdapter mCategoryAdapter;
    private NewProductAdapter mNewProductAdapter;
    private AllProductAdapter mAllProductAdapter;

    private List<Category> categories;
    private List<NewProducts> newProductsList;
    private List<AllProducts> allProductsList;

    private FirebaseFirestore db;

    private ProgressDialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        db = FirebaseFirestore.getInstance();

        binding.homeLayout.setVisibility(View.GONE);

        setUpSliderShow();

        setUpDialog();

        //Setup Adapters
        setUpRecyclerCategoryAdapter();
        setUpRecyclerNewProductAdapter();
        setUpRecyclerAllProductAdapter();

        setUpSeeAll();

        return binding.getRoot();
    }

    private void setUpSeeAll() {

        binding.categorySeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireContext(), SeeAllActivity.class);
                startActivity(intent);
            }
        });

        binding.newProductsSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireContext(), NewProductSeeAllActivity.class);
                startActivity(intent);
            }
        });

        binding.popularSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireContext(), PopularSeeAllActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpSliderShow() {

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.amazon_cash_back, "Discount 10%", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.redmi_note_9, "Discount 20%", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.maxresdefault, "Discount 30%", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.samsung_bluefest, "Discount 40%", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.mobile_greana, "Discount 50%", ScaleTypes.CENTER_CROP));

        binding.imageSlider.setImageList(slideModels);
    }

    private void setUpDialog() {

        dialog = new ProgressDialog(requireContext());
        dialog.setTitle("Welcome to my E-Commerce App");
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void setUpRecyclerCategoryAdapter() {

        categories = new ArrayList<>();

        mCategoryAdapter = new CategoryAdapter(requireContext(), categories);

        binding.recCategory.setHasFixedSize(true);
        binding.recCategory.setLayoutManager(new LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false));

        binding.recCategory.setAdapter(mCategoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Category category = document.toObject(Category.class);
                                categories.add(category);
                                mCategoryAdapter.notifyDataSetChanged();
                                binding.homeLayout.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        } else {
                            Log.w(TAG, "Error Category getting documents.", task.getException());

                            Toast.makeText(requireContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setUpRecyclerNewProductAdapter() {

        newProductsList = new ArrayList<>();

        mNewProductAdapter = new NewProductAdapter(requireContext(), newProductsList);

        binding.newProductRec.setHasFixedSize(true);
        binding.newProductRec.setLayoutManager(new LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false));

        binding.newProductRec.setAdapter(mNewProductAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                NewProducts newProducts = document.toObject(NewProducts.class);
                                newProductsList.add(newProducts);
                                mNewProductAdapter.notifyDataSetChanged();
                                binding.homeLayout.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        } else {
                            Log.w(TAG, "Error NewProducts getting documents.", task.getException());

                            Toast.makeText(requireContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setUpRecyclerAllProductAdapter() {

        allProductsList = new ArrayList<>();

        mAllProductAdapter = new AllProductAdapter(requireContext(), allProductsList);

        binding.popularRec.setHasFixedSize(true);
        binding.popularRec.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        binding.popularRec.setAdapter(mAllProductAdapter);

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
                                binding.homeLayout.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        } else {
                            Log.w(TAG, "Error AllProducts getting documents.", task.getException());

                            Toast.makeText(requireContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}