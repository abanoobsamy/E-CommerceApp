package com.abanoob_samy.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.ecommerceapp.Models.MyCart;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.databinding.CutomMyCartBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartHolder> {

    private Context context;
    private List<MyCart> myCartList;

    private int totalAmount = 0;

    public MyCartAdapter(Context context, List<MyCart> myCartList) {
        this.context = context;
        this.myCartList = myCartList;
    }

    @NonNull
    @Override
    public MyCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartHolder(LayoutInflater.from(context)
                .inflate(R.layout.cutom_my_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartHolder holder, int position) {

        MyCart myCart = myCartList.get(position);

        holder.binding.productName.setText(myCart.getProductName());
        holder.binding.productPrice.setText(myCart.getProductPrice());
        holder.binding.currentTime.setText(myCart.getCurrentTime());
        holder.binding.currentDate.setText(myCart.getCurrentDate());
        holder.binding.totalQuantity.setText(String.valueOf(myCart.getTotalQuantity()));
        holder.binding.totalPrice.setText(String.valueOf(myCart.getTotalPrice()));

        totalAmount = totalAmount + myCart.getTotalPrice();

        //total data pass to CartActivity.
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }

    public class MyCartHolder extends RecyclerView.ViewHolder {

        CutomMyCartBinding binding;

        public MyCartHolder(@NonNull View itemView) {
            super(itemView);

            binding = CutomMyCartBinding.bind(itemView);
        }
    }
}
