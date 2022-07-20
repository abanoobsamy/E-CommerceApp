package com.abanoob_samy.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.ecommerceapp.Models.AllProducts;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.Activities.DetailsActivity;
import com.abanoob_samy.ecommerceapp.databinding.CustomAllProductsBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.CategoryHolder> {

    private Context context;
    private List<AllProducts> allProductsList;

    public AllProductAdapter(Context context, List<AllProducts> allProductsList) {
        this.context = context;
        this.allProductsList = allProductsList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_all_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        AllProducts allProducts = allProductsList.get(position);

        Glide.with(context).load(allProducts.getImageUrl())
                .error(R.drawable.error).into(holder.binding.allImg);

        holder.binding.allProductName.setText(allProducts.getName());
        holder.binding.allPrice.setText(String.valueOf(allProducts.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("products", allProducts);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allProductsList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        CustomAllProductsBinding binding;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomAllProductsBinding.bind(itemView);
        }
    }
}
