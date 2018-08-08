package ru.alexsuvorov.employee_service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

//Generated by http://www.jsonschema2pojo.org/

@Entity(indices = {@Index(value = {"f_name", "l_name", "birthday", "avatar_url"})})
public class Employee implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "employee_id")
    private long employeeId;
    @ColumnInfo(name = "f_name")
    private String f_name;
    @ColumnInfo(name = "l_name")
    private String l_name;
    @ColumnInfo(name = "birthday")
    private String birthday;
    @Ignore
    @ColumnInfo(name = "age")
    private int age;
    @ColumnInfo(name = "avatar_url")
    private String avatr_url;
    @Ignore
    private List<Specialty> specialty;
    @Ignore
    private final static long serialVersionUID = -8824149947485321362L;

    public Employee(long employeeId, String f_name, String l_name, String birthday, String avatr_url) {
        this.employeeId = employeeId;
        this.f_name = f_name;
        this.l_name = l_name;
        this.birthday = birthday;
        this.avatr_url = avatr_url;
    }

    @Ignore
    public Employee() {
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setId(long id) {

        this.employeeId = id;

    }

    /*public void setEmployeeId(int id) {
        this.employeeId = id;
    }*/

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatr_url() {
        return avatr_url;
    }

    public void setAvatr_url(String avatr_url) {
        this.avatr_url = avatr_url;
    }

    public List<Specialty> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(List<Specialty> specialty) {
        this.specialty = specialty;
    }
}