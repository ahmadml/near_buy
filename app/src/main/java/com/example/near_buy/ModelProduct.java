package com.example.near_buy;

public class ModelProduct {
    private String productId;
    private String productName;
    private String productPrice;
    private String productQuantity;
    private String timeTemp;
    private String uid;
    private String Description;
    private String Store;


    public ModelProduct(){}

    public ModelProduct(String productId, String productName, String productPrice, String productQuantity, String timeTemp, String uid, String Description, String Store) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.timeTemp = timeTemp;
        this.uid = uid;
        this.Description = Description;
        this.Store = Store;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String gettimeTemp() {
        return timeTemp;
    }


    public void settimeTemp(String timestamp) {
        this.timeTemp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String Store) {
        this.Store = Store;
    }
}
