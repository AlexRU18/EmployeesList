package ru.alexsuvorov.employee_service.model;

import android.content.ContentValues;

import java.io.Serializable;

import ru.alexsuvorov.employee_service.db.DBAdapter;

public class Worker implements Serializable {
    final String TAG = getClass().getSimpleName();
    private String f_name;
    private String l_name;
    private String birthday;
    private int age;
    private String avatarLink;
    private int specialty;

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        char[] charFname = f_name.toCharArray();
        charFname[0] = Character.toUpperCase(charFname[0]);
        for (int i = 1; i < charFname.length; i++) {
            charFname[i] = Character.toLowerCase(charFname[i]);
        }
        this.f_name = new String(charFname);
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        char[] charLname = l_name.toCharArray();
        charLname[0] = Character.toUpperCase(charLname[0]);
        for (int i = 1; i < charLname.length; i++) {
            charLname[i] = Character.toLowerCase(charLname[i]);
        }
        this.l_name = new String(charLname);
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String bithday) {
        this.birthday = bithday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public static ContentValues getContentValues(Worker worker) {
        ContentValues initialValues = new ContentValues();
        if (worker.getF_name() != null) {
            initialValues.put(DBAdapter.COLUMN_FNAME, worker.getF_name()); //COLUMN
        }
        if (worker.getL_name() != null) {
            initialValues.put(DBAdapter.COLUMN_LNAME, worker.getL_name()); //COLUMN
        }
        if (worker.getBirthday() != null) {
            initialValues.put(DBAdapter.COLUMN_BIRTHDAY, worker.getBirthday()); //COLUMN
        }
        initialValues.put(DBAdapter.COLUMN_AGE, worker.getAge()); //COLUMN
        if (worker.getAvatarLink() != null) {
            initialValues.put(DBAdapter.COLUMN_AVATARLINK, worker.getAvatarLink()); //COLUMN
        }
        initialValues.put(DBAdapter.COLUMN_WSPECIALTY, worker.getSpecialty()); //COLUMN
        return initialValues;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "TAG='" + TAG + '\'' +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", bithday='" + birthday + '\'' +
                ", age=" + age +
                ", avatarLink='" + avatarLink + '\'' +
                ", specialty=" + specialty +
                '}';
    }
}
