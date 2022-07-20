package com.abanoob_samy.ecommerceapp.Models;

import java.io.Serializable;

public class NewProducts implements Serializable {

    private String imageUrl;
    private String description;
    private String name;
    private String rating;
    private int price;

    public NewProducts() {

    }

    public NewProducts(String imageUrl, String description, String name, String rating, int price) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "NewProducts{" +
                "imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", rating='" + rating + '\'' +
                ", price=" + price +
                '}';
    }
}

