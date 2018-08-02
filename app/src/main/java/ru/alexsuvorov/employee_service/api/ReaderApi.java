package ru.alexsuvorov.employee_service.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.alexsuvorov.employee_service.model.EmployeeModel;

public interface ReaderApi {
    @GET("/api/get")
    Call<List<EmployeeModel>> getData(@Query("f_name") String employeeName, @Query("l_name") String employeeLName);
}