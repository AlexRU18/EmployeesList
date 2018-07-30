package ru.alexsuvorov.employee_service.model;

import android.content.ContentValues;

import ru.alexsuvorov.employee_service.db.DBAdapter;

public class Specialty {

    private int specId;
    private String specName;

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
        initialValues.put(DBAdapter.COLUMN_SPECIALTY_ID, specialty.getSpecId());
        if (specialty.getSpecName() != null) {
            initialValues.put(DBAdapter.COLUMN_SPECIALTY_NAME, specialty.getSpecName());
        }
        return initialValues;
    }
}
