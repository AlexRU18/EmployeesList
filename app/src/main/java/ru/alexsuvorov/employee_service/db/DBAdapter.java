package ru.alexsuvorov.employee_service.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.alexsuvorov.employee_service.EmployeeService;
import ru.alexsuvorov.employee_service.model.Worker;

public class DBAdapter {

    private final int DATABASE_VERSION = 1;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    public static final String DATABASE_NAME = "db";
    public static final String COLUMN_AUTO_INCREMENT_ID = "auto_increment_id";
    private onDbListeners mDbListener;
    public static final int actionGetAllData = 1, actionInsert = 2, actionUpdate = 3, actionDelete = 4, actionDeleteAll = 5;
    private Object object = null;

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
            db.execSQL(CREATE_MY_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Savedfiles");
            onCreate(db);
        }
    }

    // ---open database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // ---closes database---
    private void close() {
        DBHelper.close();
    }

    public boolean isValidCursor(Cursor cur) {
        try {
            if (cur != null) {
                return cur.getCount() > 0;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // --- Table for Data ---
    public static final String TABLE_NAME = "tbl_my_table", COLUMN_USER_ID = "user_id", COLUMN_USERNAME = "username";

    private static String[] GET_ALL_DATA() {
        return new String[]{COLUMN_AUTO_INCREMENT_ID, COLUMN_USER_ID, COLUMN_USERNAME};
    }

    private static final String CREATE_MY_TABLE = "create table "
            + TABLE_NAME + "(" + COLUMN_AUTO_INCREMENT_ID + " integer primary key autoincrement, "
            + COLUMN_USER_ID + " text not null, "
            + COLUMN_USERNAME + " text);";

    // ---insert data in database---
    public void insertData(Worker worker) {
        open();
        ContentValues initialValues = Worker.getContentValues(worker);
        boolean var = db.insert(TABLE_NAME, null, initialValues) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionInsert, object);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionInsert);
        }
        close();
    }

    // ---get all data---
    public void getAllData() {
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
    }

    // ---get all data by id---
    public void getAllDataByUserId(String userId) {
        open();
        Cursor cur = db.query(TABLE_NAME, GET_ALL_DATA(), COLUMN_USER_ID + " = ?", new String[]{userId}, null, null, null);
        if (cur != null && cur.getCount() > 0) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionGetAllData, cur);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionGetAllData);
        }

        cur.close();
        close();
    }

    // ---deletes all data---
    public void deleteAllData() {
        open();
        boolean var = db.delete(TABLE_NAME, null, null) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionDeleteAll, object);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionDeleteAll);
        }

        close();
    }

    // ---deletes single data---
    public void deleteSingleData(String userId) {
        open();
        boolean var = db.delete(TABLE_NAME, COLUMN_USER_ID + "=" + userId, null) > 0;
        if (var) {
            mDbListener.onOperationSuccess(TABLE_NAME, actionDelete, object);
        } else {
            mDbListener.onOperationFailed(TABLE_NAME, actionDelete);
        }

        close();
    }

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