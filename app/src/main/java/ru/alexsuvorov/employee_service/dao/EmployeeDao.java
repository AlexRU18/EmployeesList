package ru.alexsuvorov.employee_service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.alexsuvorov.employee_service.model.Employee;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    /*@Transaction
    @Query("SELECT * FROM employee")
    List<EmployeeToSpecialty> loadEmployeeToSpecialty();*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Employee employee);

    @Delete
    void delete(Employee employee);

    @Query("DELETE FROM employee")
    void deleteAllEmployee();
}