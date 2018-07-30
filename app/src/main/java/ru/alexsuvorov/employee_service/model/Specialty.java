package ru.alexsuvorov.employee_service.model;

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

    @Override
    public String toString() {
        return "id [" + specId + "], name[" + specName + "]";
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
}
