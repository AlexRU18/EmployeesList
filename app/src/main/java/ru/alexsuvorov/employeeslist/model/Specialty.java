package ru.alexsuvorov.employeeslist.model;


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
}
