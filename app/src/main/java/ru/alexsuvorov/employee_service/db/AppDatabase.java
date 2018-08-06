package ru.alexsuvorov.employee_service.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.alexsuvorov.employee_service.dao.EmployeeDao;
import ru.alexsuvorov.employee_service.dao.SpecialtyDao;
import ru.alexsuvorov.employee_service.model.Employee;
import ru.alexsuvorov.employee_service.model.Specialty;

@Database(entities = {Employee.class, Specialty.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EmployeeDao employeeDao();

    public abstract SpecialtyDao specialtyDao();
}