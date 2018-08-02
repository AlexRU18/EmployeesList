package ru.alexsuvorov.employee_service.api;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.alexsuvorov.employee_service.model.ResponseModel;

public interface ReaderApi {
    @GET("testTask.json")
    Call<ResponseModel> getJSON();
}