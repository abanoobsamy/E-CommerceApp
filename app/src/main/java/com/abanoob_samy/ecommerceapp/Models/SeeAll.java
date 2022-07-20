package com.abanoob_samy.ecommerceapp.Models;

import java.io.Serializable;

public class SeeAll implements Serializable {

    private String imageUrl;
    private String description;
    private String name;
    private String type;
    private String rating;
    private int price;

    public SeeAll() {

    }

    public SeeAll(String imageUrl, String description, String name, String type, String rating, int price) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "SeeAll{" +
                "imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", rating='" + rating + '\'' +
                ", price=" + price +
                '}';
    }
}

