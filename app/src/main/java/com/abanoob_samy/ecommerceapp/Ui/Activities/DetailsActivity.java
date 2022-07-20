package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.Models.AllProducts;
import com.abanoob_samy.ecommerceapp.Models.NewProducts;
import com.abanoob_samy.ecommerceapp.Models.SeeAll;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.databinding.ActivityDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private NewProducts newProducts = null;
    private AllProducts allProducts = null;
    private SeeAll seeAll = null;

    private int totalQuantity = 1;
    private int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Object obj = getIntent().getSerializableExtra("products");

        if (obj instanceof NewProducts) {
            newProducts = (NewProducts) obj;
        }
        else if (obj instanceof AllProducts) {
            allProducts = (AllProducts) obj;
        }
        else if (obj instanceof SeeAll) {
            seeAll = (SeeAll) obj;
        }

        if (newProducts != null) {

            Glide.with(this).load(newProducts.getImageUrl())
                    .error(R.drawable.error).into(binding.imageDetails);

            binding.detailsName.setText(newProducts.getName());
            binding.detailsDescription.setText(newProducts.getDescription());
            binding.detailsPrice.setText(String.valueOf(newProducts.getPrice()));
            binding.ratingDetails.setText(newProducts.getRating());

            totalPrice = newProducts.getPrice() * totalQuantity;
        }

        if (allProducts != null) {

            Glide.with(this).load(allProducts.getImageUrl())
                    .error(R.drawable.error).into(binding.imageDetails);

            binding.detailsName.setText(allProducts.getName());
            binding.detailsDescription.setText(allProducts.getDescription());
            binding.detailsPrice.setText(String.valueOf(allProducts.getPrice()));
            binding.ratingDetails.setText(allProducts.getRating());

            totalPrice = allProducts.getPrice() * totalQuantity;
        }

        if (seeAll != null) {

            Glide.with(this).load(seeAll.getImageUrl())
                    .error(R.drawable.error).into(binding.imageDetails);

            binding.detailsName.setText(seeAll.getName());
            binding.detailsDescription.setText(seeAll.getDescription());
            binding.detailsPrice.setText(String.valueOf(seeAll.getPrice()));
            binding.ratingDetails.setText(seeAll.getRating());

            totalPrice = seeAll.getPrice() * totalQuantity;
        }

        binding.addToCartDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCart();
            }
        });

        binding.buyNowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailsActivity.this, AddressActivity.class);

                if (newProducts != null) {

                    intent.putExtra("item", newProducts);
                }
                else if (allProducts != null) {

                    intent.putExtra("item", allProducts);
                }
                else if (seeAll != null) {

                    intent.putExtra("item", seeAll);
                }

                startActivity(intent);
            }
        });

        binding.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity < 10) {

                    totalQuantity++;
                    binding.textQuantity.setText(String.valueOf(totalQuantity));

                    if (newProducts != null) {
                        totalPrice = newProducts.getPrice() * totalQuantity;
                    }
                    else if (allProducts != null) {
                        totalPrice = allProducts.getPrice() * totalQuantity;
                    }
                    else if (seeAll != null) {
                        totalPrice = seeAll.getPrice() * totalQuantity;
                    }

                    return;
                }

                Toast.makeText(getApplicationContext(), "Ten at Most.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity > 1) {

                    totalQuantity--;
                    binding.textQuantity.setText(String.valueOf(totalQuantity));
                    return;
                }

                Toast.makeText(getApplicationContext(), "One at Least.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", binding.detailsName.getText().toString());
//        cartMap.put("productDesc", binding.detailsDescription.getText().toString());
        cartMap.put("productPrice", binding.detailsPrice.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalPrice", totalPrice);
        cartMap.put("totalQuantity", totalQuantity);

        db.collection("AddToCart")
                .document(mAuth.getCurrentUser().getUid())
                .collection("User")
                .add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                Toast.makeText(getApplicationContext(), "Added To Cart Successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}