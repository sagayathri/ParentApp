package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetDistance implements Serializable {
    @SerializedName("from")
    private PostcodeDistance from;

    @SerializedName("to")
    private PostcodeDistance to;

    @SerializedName("metres")
    private double metres;


    public PostcodeDistance getFrom() { return from; }

    public PostcodeDistance getTo() { return to; }

    public double getMetres() { return metres; }
}



