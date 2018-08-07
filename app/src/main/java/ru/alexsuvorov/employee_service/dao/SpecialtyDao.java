package ru.alexsuvorov.employee_service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.alexsuvorov.employee_service.model.Specialty;

@Dao
public interface SpecialtyDao {

    @Query("SELECT * FROM specialty")
    List<Specialty> getAllSpecialty();

    @Insert
    void insert(Specialty specialty);

    @Delete
    void delete(Specialty specialty);

}