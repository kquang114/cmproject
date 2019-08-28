package com.example.cmproject.Model;

import java.io.Serializable;

public class Parking implements Serializable {

    private int id;
    private String name;
    private String address;
    private int price;
    private double latitude;
    private double lng;
    private String type;

    public Parking(int id, String name, String address, int price, double latitude, double lng, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.latitude = latitude;
        this.lng = lng;
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
