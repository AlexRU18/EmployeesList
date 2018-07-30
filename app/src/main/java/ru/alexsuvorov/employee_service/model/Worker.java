package ru.alexsuvorov.employee_service.model;

import android.content.ContentValues;

import ru.alexsuvorov.employee_service.db.DBAdapter;
import ru.alexsuvorov.employee_service.utils.DateUtil;

public class Worker {
    final String TAG = getClass().getSimpleName();
    private String f_name;
    private String l_name;
    private String bithday;
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

    public String getBithday() {
        return bithday;
    }

    public void setBithday(String bithday) {
        DateUtil.validDateString(bithday);
        this.bithday = bithday;
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
            initialValues.put(DBAdapter.COLUMN_USER_ID, worker.getF_name()); //COLUMN
        }
        if (worker.getL_name() != null) {
            initialValues.put(DBAdapter.COLUMN_USERNAME, worker.getL_name()); //COLUMN
        }
        if (worker.getBithday() != null) {
            initialValues.put(DBAdapter.COLUMN_USERNAME, worker.getBithday()); //COLUMN
        }
        initialValues.put(DBAdapter.COLUMN_USERNAME, worker.getAge()); //COLUMN
        if (worker.getAvatarLink() != null) {
            initialValues.put(DBAdapter.COLUMN_USERNAME, worker.getAvatarLink()); //COLUMN
        }
        initialValues.put(DBAdapter.COLUMN_USERNAME, worker.getSpecialty()); //COLUMN

        return initialValues;
    }
}
