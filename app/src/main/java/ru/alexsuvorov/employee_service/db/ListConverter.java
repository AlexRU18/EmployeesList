package ru.alexsuvorov.employee_service.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;

import ru.alexsuvorov.employee_service.model.Employee;

public class ListConverter {
    private Gson gson = new Gson();

    @TypeConverter
    public String fromOpeningHoursDayArray(Employee[] ohda) {
        ArrayList<Employee> ohdList = new ArrayList<>(Arrays.asList(ohda));
        return gson.toJson(ohdList);
    }

    @TypeConverter
    public Employee[] toOpeningHoursDayArray(String ohdJson) {
        ArrayList<Employee> ohdList = new ArrayList<>();
        ohdList = gson.fromJson(ohdJson, new TypeToken<ArrayList<Employee>>() {
        }.getType());
        Employee[] ohda = null;
        for (int i = 0; i < ohdList.size(); i++) {
            ohda[i] = ohdList.get(i);
        }
        return ohda;
    }
}