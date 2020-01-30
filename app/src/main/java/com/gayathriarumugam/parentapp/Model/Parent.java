package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Parent implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("bookedSlots")
    private List<ParentBookedDetails> bookedDetails;

    @SerializedName("address")
    private Address address;

    @SerializedName("childDetails")
    private ChildDetails childDetails;

    public List<ParentBookedDetails> getBookedDetails() {
        return bookedDetails;
    }

    public void setBookedDetails(List<ParentBookedDetails> bookedDetails) {
        this.bookedDetails = bookedDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ChildDetails getChildDetails() {
        return childDetails;
    }

    public void setChildDetails(ChildDetails childDetails) {
        this.childDetails = childDetails;
    }
}
