package com.abanoob_samy.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.ecommerceapp.Models.NewProducts;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.Activities.DetailsActivity;
import com.abanoob_samy.ecommerceapp.databinding.CustomNewProductsBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.NewProductsHolder> {

    private Context context;
    private List<NewProducts> newProductsList;

    public NewProductAdapter(Context context, List<NewProducts> newProductsList) {
        this.context = context;
        this.newProductsList = newProductsList;
    }

    @NonNull
    @Override
    public NewProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewProductsHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_new_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductsHolder holder, int position) {

        NewProducts newProducts = newProductsList.get(position);

        Glide.with(context).load(newProducts.getImageUrl())
                .error(R.drawable.error).into(holder.binding.newImg);

        holder.binding.newProductName.setText(newProducts.getName());
        holder.binding.newPrice.setText(String.valueOf(newProducts.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("products", newProducts);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newProductsList.size();
    }

    public class NewProductsHolder extends RecyclerView.ViewHolder {

        CustomNewProductsBinding binding;

        public NewProductsHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomNewProductsBinding.bind(itemView);
        }
    }
}
