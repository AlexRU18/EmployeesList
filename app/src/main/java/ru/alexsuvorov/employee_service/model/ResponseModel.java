package ru.alexsuvorov.employee_service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Response;

public class ResponseModel implements Serializable {

    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    private final static long serialVersionUID = 5750681858598462605L;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

}