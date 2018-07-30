package ru.alexsuvorov.employee_service.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ru.alexsuvorov.employee_service.EmployeeService;
import ru.alexsuvorov.employee_service.model.Specialty;
import ru.alexsuvorov.employee_service.model.Worker;

public class DBAdapter {

    private final int DATABASE_VERSION = 1;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    public static final String DATABASE_NAME = "db";
    public static final String COLUMN_AUTO_INCREMENT_ID = "auto_increment_id";
    private onDbListeners mDbListener;
    public static final int actionGetData = 1, actionInsert = 2, actionUpdate = 3, actionDelete = 4, actionDeleteAll = 5;
    private Object object = null;
    final String TAG = getClass().getSimpleName();

    public DBAdapter(Context context, EmployeeService listener) {
        DBHelper = new DatabaseHelper(context);
        mDbListener = listener;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_EMPLOYEES_TABLE);
            db.execSQL(CREATE_SPECIALTY_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Savedfiles");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    private void close() {
        DBHelper.close();
    }

    public boolean isValidCursor(Cursor cur) {
        try {
            return cur != null && cur.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static final String TABLE_SPECIALTY = "specialty_table";
    public static final String COLUMN_SPECIALTY_ID = "specialty_id";
    public static final String COLUMN_SPECIALTY_NAME = "specialty_name";

    private static String[] GET_ALL_SPECIALTY() {
        return new String[]{COLUMN_AUTO_INCREMENT_ID, COLUMN_SPECIALTY_ID, COLUMN_SPECIALTY_NAME};
    }

    private static final String CREATE_SPECIALTY_TABLE = "create table "
            + TABLE_SPECIALTY + "(" + COLUMN_AUTO_INCREMENT_ID + " integer primary key autoincrement, "
            + COLUMN_SPECIALTY_ID + " text not null, "
            + COLUMN_SPECIALTY_NAME + " text not null);";

    public void insertSpecialty(Specialty specialty) {
        open();
        ContentValues initialValues = Specialty.getContentValues(specialty);
        boolean var = db.insert(TABLE_SPECIALTY, null, initialValues) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_SPECIALTY, actionInsert, object);
        } else {
            mDbListener.onOperationFailed(TABLE_SPECIALTY, actionInsert);
        }
        close();
    }

    public void getAllSpecialty() {
        open();
        Cursor cur = db.query(TABLE_SPECIALTY, GET_ALL_SPECIALTY(), null, null, null, null, null);
        if (cur != null && cur.getCount() > 0) {
            mDbListener.onOperationSuccess(TABLE_SPECIALTY, actionGetData, cur);
        } else {
            mDbListener.onOperationFailed(TABLE_SPECIALTY, actionGetData);
        }

        assert cur != null;
        cur.close();
        close();
    }

    private static final String TABLE_EMPLOYEES = "employees_table";
    public static final String COLUMN_EMPLOYEE_ID = "worker_id";
    public static final String COLUMN_FNAME = "worker_fname";
    public static final String COLUMN_LNAME = "worker_lname";
    public static final String COLUMN_BITHDAY = "worker_bithday";
    public static final String COLUMN_AGE = "worker_age";
    public static final String COLUMN_AVATARLINK = "worker_avatar_link";
    public static final String COLUMN_SPECIALTY = "worker_specialty";

    private static String[] GET_ALL_WORKERS() {
        return new String[]{COLUMN_AUTO_INCREMENT_ID, COLUMN_EMPLOYEE_ID, COLUMN_FNAME,
                COLUMN_LNAME, COLUMN_BITHDAY, COLUMN_AGE, COLUMN_AVATARLINK, COLUMN_SPECIALTY};
    }

    private static final String CREATE_EMPLOYEES_TABLE = "create table "
            + TABLE_EMPLOYEES + "(" + COLUMN_AUTO_INCREMENT_ID + " integer primary key autoincrement, "
            + COLUMN_EMPLOYEE_ID + " text not null, "
            + COLUMN_FNAME + " text, "
            + COLUMN_LNAME + " text, "
            + COLUMN_BITHDAY + " text, "
            + COLUMN_AGE + " integer, "
            + COLUMN_SPECIALTY + " integer);";

    public void insertWorker(Worker worker) {
        open();
        ContentValues initialValues = Worker.getContentValues(worker);
        boolean var = db.insert(TABLE_EMPLOYEES, null, initialValues) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_EMPLOYEES, actionInsert, object);
        } else {
            mDbListener.onOperationFailed(TABLE_EMPLOYEES, actionInsert);
        }
        close();
    }

    public ArrayList<Worker> getAllWorkersBySpecialtyId(String specialtyId) {

        ArrayList<Worker> employeesList = new ArrayList<>();
        int columnIndex = 3;
        open();
        Cursor cur = db.query(TABLE_EMPLOYEES, GET_ALL_WORKERS(), COLUMN_SPECIALTY + " = ?", new String[]{specialtyId}, null, null, null);

        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                Worker worker = new Worker();
                worker.setF_name(cur.getColumnName(columnIndex));
                Log.d(TAG, "GET_DATA_setF_name: " + cur.getColumnName(columnIndex));
                worker.setL_name(cur.getColumnName(columnIndex));
                worker.setBithday(cur.getColumnName(columnIndex));
                worker.setAge(cur.getInt(columnIndex));
                worker.setAvatarLink(cur.getColumnName(columnIndex));
                worker.setSpecialty(cur.getInt(columnIndex));
                cur.moveToNext();
                Log.d(TAG, "GET_DATA_WORKER: " + worker.toString());
            }
        }


        /*if (cur != null && cur.getCount() > 0) {
            cur.getColumnName()
            mDbListener.onOperationSuccess(TABLE_EMPLOYEES, actionGetData, cur);
        } else {
            mDbListener.onOperationFailed(TABLE_EMPLOYEES, actionGetData);
        }*/

        if (cur != null) {
            cur.close();
        }
        close();
        return employeesList;
    }

    /*public void getAllData() {
        open();
        Cursor cur = db.query(TABLE_NAME, GET_ALL_DATA(), null, null, null, null, null);
        if (cur != null && cur.getCount() > 0) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionGetAllData, cur);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionGetAllData);
        }

        assert cur != null;
        cur.close();
        close();
    }*/

    /*public void deleteAllData() {
        open();
        boolean var = db.delete(TABLE_NAME, null, null) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionDeleteAll, object);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionDeleteAll);
        }

        close();
    }*/

    /*public void deleteSingleData(String userId) {
        open();
        boolean var = db.delete(TABLE_NAME, COLUMN_USER_ID + "=" + userId, null) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionDelete, object);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionDelete);
        }

        close();
    }*/

    // ---updates a data details---
    /*public void updateData(Worker worker) {
        open();
        ContentValues initialValues = Worker.getContentValues(worker);
        boolean var = db.update(TABLE_NAME, initialValues, COLUMN_USER_ID + "=" + worker.getUserId(), null) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionUpdate, object);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionUpdate);
        }
        close();
    }*/
}