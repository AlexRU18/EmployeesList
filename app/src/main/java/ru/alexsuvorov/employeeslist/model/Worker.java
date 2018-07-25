package ru.alexsuvorov.employeeslist.model;

public class Worker {

    private String f_name;
    private String l_name;
    private String bithday;
    private String avatarLink;
    private int specialty;

    public Worker(String f_name, String l_name) {
        this.f_name = f_name;
        this.l_name = l_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        f_name.substring(0, 1).toUpperCase();
        f_name.substring(1).toLowerCase();
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        l_name.substring(0, 1).toUpperCase();
        l_name.substring(1).toLowerCase();
        this.l_name = l_name;
    }

    public String getBithday() {
        return bithday;
    }

    public void setBithday(String bithday) {
        this.bithday = bithday;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public int getSpecialty() {
        return specialty;
    }

    public void setSpecialty(int specialty) {
        this.specialty = specialty;
    }
}
