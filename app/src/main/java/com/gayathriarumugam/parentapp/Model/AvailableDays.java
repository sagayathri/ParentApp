package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AvailableDays implements Serializable {

    @SerializedName("friday")
    private List<String> friday;

    @SerializedName("monday")
    private List<String> monday;

    @SerializedName("saturday")
    private List<String> saturday;

    @SerializedName("sunday")
    private List<String> sunday;

    @SerializedName("thursday")
    private List<String> thursday;

    @SerializedName("tuesday")
    private List<String> tuesday;

    @SerializedName("wednesday")
    private List<String> wednesday;

    public AvailableDays() {
    }

    public AvailableDays(List<String> friday, List<String> monday, List<String> saturday, List<String> sunday, List<String> thursday, List<String> tuesday, List<String> wednesday) {
        this.friday = friday;
        this.monday = monday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.thursday = thursday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
    }

    public List<String> getFriday() {
        return friday;
    }

    public void setFriday(List<String> friday) {
        this.friday = friday;
    }

    public List<String> getMonday() {
        return monday;
    }

    public void setMonday(List<String> monday) {
        this.monday = monday;
    }

    public List<String> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<String> saturday) {
        this.saturday = saturday;
    }

    public List<String> getSunday() {
        return sunday;
    }

    public void setSunday(List<String> sunday) {
        this.sunday = sunday;
    }

    public List<String> getThursday() {
        return thursday;
    }

    public void setThursday(List<String> thursday) {
        this.thursday = thursday;
    }

    public List<String> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<String> tuesday) {
        this.tuesday = tuesday;
    }

    public List<String> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<String> wednesday) {
        this.wednesday = wednesday;
    }
}
