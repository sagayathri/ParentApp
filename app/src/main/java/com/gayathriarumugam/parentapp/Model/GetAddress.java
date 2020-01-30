package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetAddress implements Serializable {
    @SerializedName("latitude")
    Double latitude;
    @SerializedName("longitude")
    Double longitude;
    @SerializedName("addresses")
    List<String> addresses;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<String> getAddresses() {
        return addresses;
    }
}
