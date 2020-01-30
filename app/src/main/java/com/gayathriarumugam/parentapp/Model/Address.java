package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("county")
    private String county;

    @SerializedName("locality")
    private String locality;

    @SerializedName("line1")
    private String line1;

    @SerializedName("line2")
    private String line2;

    @SerializedName("postcode")
    private String postcode;

    public Address() {
    }

    public Address(String city, String country, String county, String locality, String line1, String line2, String postcode) {
        this.city = city;
        this.country = country;
        this.county = county;
        this.locality = locality;
        this.line1 = line1;
        this.line2 = line2;
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
