package com.example.a2101658224_ba26_project.model;

public class Product {
    private int id;
    private String name;
    private String imageLink;
    private String productSpecs;
    private int productPrice;

    public Product(int id, String name, int productPrice, String productSpecs, String imageLink) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
        this.productSpecs = productSpecs;
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getProductSpecs() {
        return productSpecs;
    }

    public void setProductSpecs(String productSpecs) {
        this.productSpecs = productSpecs;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
