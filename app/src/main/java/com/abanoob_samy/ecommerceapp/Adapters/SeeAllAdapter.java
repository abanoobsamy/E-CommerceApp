package com.abanoob_samy.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.ecommerceapp.Models.SeeAll;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.Activities.DetailsActivity;
import com.abanoob_samy.ecommerceapp.databinding.CustomSeeAllBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.SeeAllHolder> {

    private Context context;
    private List<SeeAll> seeAllList;

    public SeeAllAdapter(Context context, List<SeeAll> seeAllList) {
        this.context = context;
        this.seeAllList = seeAllList;
    }

    @NonNull
    @Override
    public SeeAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeeAllHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_see_all, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllHolder holder, int position) {

        SeeAll seeAll = seeAllList.get(position);

        Glide.with(context).load(seeAll.getImageUrl())
                .error(R.drawable.error).into(holder.binding.itemImage);

        holder.binding.itemNam.setText(seeAll.getName());
        holder.binding.itemCost.setText(String.valueOf(seeAll.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("products", seeAll);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seeAllList.size();
    }

    public class SeeAllHolder extends RecyclerView.ViewHolder {

        CustomSeeAllBinding binding;

        public SeeAllHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomSeeAllBinding.bind(itemView);
        }
    }
}
