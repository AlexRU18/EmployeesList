package ru.alexsuvorov.employee_service.model;

import android.content.ContentValues;

import ru.alexsuvorov.employee_service.db.DBAdapter;

public class Specialty {

    private int specId;
    private String specName;
    final String TAG = getClass().getSimpleName();

    public Specialty(int specId, String specName) {
        this.specId = specId;
        this.specName = specName;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public static ContentValues getContentValues(Specialty specialty) {
        ContentValues initialValues = new ContentValues();
        //Log.d("SPECIALTY STATIC MODEL", "ID is: "+ specialty.getSpecId());
        initialValues.put(DBAdapter.COLUMN_SPECIALTY_ID, specialty.getSpecId());
        //Log.d("SPECIALTY STATIC MODEL", "Name is: "+ specialty.getSpecName());
        if (specialty.getSpecName() != null) {
            initialValues.put(DBAdapter.COLUMN_SPECIALTY_NAME, specialty.getSpecName());
        }
        return initialValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        if (specId != specialty.specId) return false;
        return specName != null ? specName.equals(specialty.specName) : specialty.specName == null;
    }

    @Override
    public int hashCode() {
        int result = specId;
        result = 31 * result + (specName != null ? specName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "specId=" + specId +
                ", specName='" + specName + '\'' +
                '}';
    }
}
