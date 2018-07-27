package ru.alexsuvorov.employeeslist.utils;

import android.content.Context;

public class DBHelper {

    final String TAG = getClass().getSimpleName();

    public DBHelper(Context context) {
        // конструктор суперкласса
        //super(context, "myDB", null, 1);
    }

   /* @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "email text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }*/
}