package com.abanoob_samy.ecommerceapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.abanoob_samy.ecommerceapp.Models.Address;
import com.abanoob_samy.ecommerceapp.Models.AllProducts;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.Activities.DetailsActivity;
import com.abanoob_samy.ecommerceapp.databinding.CustomAddressBinding;
import com.abanoob_samy.ecommerceapp.databinding.CustomAllProductsBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private Context context;
    private List<Address> addressList;
    private SelectedAddress selectedAddress;

    private RadioButton selectedRadio;

    public AddressAdapter(Context context, List<Address> addressList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressList = addressList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.addressAdd.setText(addressList.get(position).getUserAddress());
        holder.binding.selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Address address: addressList) {

                    address.setSelected(false);
                }

                addressList.get(position).setSelected(true);

                if (selectedRadio != null) {
                    selectedRadio.setChecked(false);
                }

                selectedRadio = (RadioButton) view;
                selectedRadio.setChecked(true);
                selectedAddress.setSelectedAddress(addressList.get(position).getUserAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {

        CustomAddressBinding binding;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomAddressBinding.bind(itemView);
        }
    }

    public interface SelectedAddress {
        void setSelectedAddress(String address);
    }
}
