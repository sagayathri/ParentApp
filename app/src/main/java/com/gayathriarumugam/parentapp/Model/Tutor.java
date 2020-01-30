package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Tutor implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("subjects")
    private List<String> subjects;

    @SerializedName("bookedSlots")
    private List<BookedSlots> bookedSlots;

    @SerializedName("address")
    private Address address;

    @SerializedName("availableDays")
    private AvailableDays availableDays;

    public Tutor() {
    }

    public Tutor(long id, String name, List<String> subjects, List<BookedSlots> bookedSlots, Address address, AvailableDays availableDays) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
        this.bookedSlots = bookedSlots;
        this.address = address;
        this.availableDays = availableDays;
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

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<BookedSlots> getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(List<BookedSlots> bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AvailableDays getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(AvailableDays availableDays) {
        this.availableDays = availableDays;
    }
}
