package com.abanoob_samy.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.ecommerceapp.Models.Category;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.Activities.SeeAllActivity;
import com.abanoob_samy.ecommerceapp.databinding.CustomCategoryBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        Category category = categories.get(position);

        Glide.with(context).load(category.getImageUrl())
                .error(R.drawable.error).into(holder.binding.catImg);

        holder.binding.catName.setText(category.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SeeAllActivity.class);
                intent.putExtra("typeCategory", category.getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        CustomCategoryBinding binding;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomCategoryBinding.bind(itemView);
        }
    }
}
