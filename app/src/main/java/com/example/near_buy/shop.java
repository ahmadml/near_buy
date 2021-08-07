package com.example.near_buy;

public class shop {
    private String email;
    private String name;
    private String store;
    private int phone, store_phone;
    private String store_address;
    private String store_city;
    private String IsOpen;
    private String type;



    public shop(){}

    public shop(String email, String IsOpen, String name , int phone, String store , String store_address, String store_city, int store_phone , String type){
        this.email = email;
        this.IsOpen = IsOpen;
        this.name = name;
        this.phone = phone;
        this.store = store;
        this.store_address = store_address;
        this.store_city = store_city;
        this.store_phone = store_phone;
        this.type = type;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop_name() {
        return store;
    }

    public void setShop_name(String shop_name) {
        this.store = shop_name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getShop_phone() {
        return store_phone;
    }

    public void setShop_phone(int shop_phone) {
        this.store_phone = shop_phone;
    }

    public String getShop_address() {
        return store_address;
    }

    public void setShop_address(String shop_address) {
        this.store_address = shop_address;
    }

    public String getShop_open() {
        return IsOpen;
    }

    public void setShop_open(String shop_open) {
        this.IsOpen = shop_open;
    }

    public String getStore_city() {
        return store_city;
    }

    public void setStore_city(String store_city) {
        this.store_city = store_city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
