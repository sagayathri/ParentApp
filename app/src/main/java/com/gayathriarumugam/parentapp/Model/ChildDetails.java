package com.gayathriarumugam.parentapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChildDetails implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @SerializedName("grade")
    private String grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
