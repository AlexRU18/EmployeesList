package ru.alexsuvorov.employee_service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpecialtyModel implements Serializable {

    @SerializedName("specialty_id")
    @Expose
    private int specialtyId;
    @SerializedName("name")
    @Expose
    private String name;
    private final static long serialVersionUID = 4288061416169200241L;

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}