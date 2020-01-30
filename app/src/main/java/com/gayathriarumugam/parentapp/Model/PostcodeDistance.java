package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostcodeDistance implements Serializable {
    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("postcode")
    private String postcode;

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public String getPostcode() { return postcode; }
}