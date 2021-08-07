package com.example.near_buy;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private  String name;
    private  String uid;
    private String email;
    private String store_name;
    private String store_address;
    private  int  phone, store_phone;
    private  String type ="seller";

    private double latitude , longitude;

    private String IsOpen;
    private  String Store_city;
    private List<ContactsContract.CommonDataKinds.Relation> products = new ArrayList<>();


    public Manager(){
    }

    //send data at register
    public Manager(String uid,String email, String IsOpen, String name, int phone,String store,String address, String Store_city,int store_phone, String type, double longitude, double latitude){
        this.uid=uid;
        this.email = email;
        this.IsOpen = IsOpen;
        this.name = name;
        this.phone = phone;
        this.store_name=store;
        this.store_address = address;
        this.Store_city = Store_city;
        this.store_phone = store_phone;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //retrieve info from database
    public Manager(String uid,String email, String IsOpen,  String name, int store_phone, String store, List<ContactsContract.CommonDataKinds.Relation> products, String store_address,double longitude, double latitude){
        this.uid=uid;
        this.name = name;
        this.email = email;
        this.store_name = store;
        this.store_address = store_address;
        this.store_phone = store_phone;
        this.products = products;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStore() {
        return store_name;
    }

    public void setStore(String store) {
        this.store_name = store;
    }

    public List<ContactsContract.CommonDataKinds.Relation> getProducts() {
        return products;
    }

    public void setProducts(List<ContactsContract.CommonDataKinds.Relation> products) {
        this.products = products;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(int store_phone) {
        this.store_phone = store_phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(String isOpen) {
        IsOpen = isOpen;
    }

    public String getStore_city() {
        return Store_city;
    }

    public void setStore_city(String store_city) {
        Store_city = store_city;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}