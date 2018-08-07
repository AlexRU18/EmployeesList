package ru.alexsuvorov.employee_service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.alexsuvorov.employee_service.model.Employee;
import ru.alexsuvorov.employee_service.model.EmployeeToSpecialty;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    /*@Transaction
    @Query("SELECT * FROM employee")
    List<EmployeeToSpecialty> loadEmployeeToSpecialty();*/

    @Transaction
    @Query("SELECT * FROM specialty"/* WHERE specialty_id= :specialty_id"*/)
    List<EmployeeToSpecialty> loadEmployeeToSpecialty(int specialty_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);

    @Query("DELETE FROM employee")
    void deleteAllEmployee();
}