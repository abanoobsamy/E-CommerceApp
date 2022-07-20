package com.abanoob_samy.ecommerceapp.Models;

public class Category {

    private String imageUrl;
    private String name;
    private String type;

    public Category() {

    }

    public Category(String imageUrl, String name, String type) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        return "Category{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
