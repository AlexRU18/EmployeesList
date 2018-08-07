package ru.alexsuvorov.employee_service.model;

import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseModel {

    @SerializedName("response")
    @Expose
    private ArrayList<Employee> response;

    @Ignore
    public ResponseModel(ArrayList<Employee> employees) {
        this.response = employees;
    }

    public List<Employee> getResponse() {
        return response;
    }
}