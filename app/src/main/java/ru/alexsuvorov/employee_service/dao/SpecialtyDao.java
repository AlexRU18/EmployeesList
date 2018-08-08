package ru.alexsuvorov.employee_service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import ru.alexsuvorov.employee_service.model.Specialty;

@Dao
public interface SpecialtyDao {

    @Query("SELECT * FROM specialty")
    List<Specialty> getAllSpecialty();

    @Transaction
    @Query("SELECT * FROM specialty WHERE employeeOwner_id = :employeeId")
    List<Specialty> findSpecialtiesForEmployee(long employeeId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Specialty specialty);

    @Delete
    void delete(Specialty specialty);

}