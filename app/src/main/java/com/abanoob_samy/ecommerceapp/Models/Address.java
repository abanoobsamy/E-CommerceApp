package com.abanoob_samy.ecommerceapp.Models;

public class Address {

    private String userAddress;
    private boolean isSelected;

    public Address() {

    }

    public Address(String userAddress, boolean isSelected) {
        this.userAddress = userAddress;
        this.isSelected = isSelected;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Address{" +
                "userAddress='" + userAddress + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
