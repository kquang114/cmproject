package com.example.cmproject.Model;

import java.io.Serializable;

public class Driver implements Serializable {
    private String phone;
    private String name;
    private String license;
    private double latitude;
    private double lng;


    public Driver(String phone, String name, String license, double latitude, double lng) {
        this.phone = phone;
        this.name = name;
        this.license = license;
        this.latitude = latitude;
        this.lng = lng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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


}
