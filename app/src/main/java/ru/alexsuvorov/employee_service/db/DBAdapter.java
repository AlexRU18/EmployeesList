package ru.alexsuvorov.employee_service.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
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
    private static final String DATABASE_NAME = "db";
    private static final String COLUMN_AUTO_INCREMENT_ID = "auto_increment_id";
    private onDbListeners mDbListener;
    private static final int actionGetData = 1, actionInsert = 2, actionUpdate = 3, actionDelete = 4, actionDeleteAll = 5;
    private Object object = null;
    private final String TAG = getClass().getSimpleName();

    public DBAdapter(Context context, EmployeeService listener) {
        DBHelper = new DatabaseHelper(context);
        mDbListener = listener;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SPECIALTY_TABLE);
            db.execSQL(UNIQUE_INDEX_SPECIALTY);
            db.execSQL(CREATE_EMPLOYEES_TABLE);
            db.execSQL(UNIQUE_INDEX_EMPLOYEE);
            //Log.d(TAG, "DATABASE_ONCREATE");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIALTY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
            onCreate(db);
            Log.d(TAG, "DATABASE_ONUPDATE");
        }
    }

    private void open() throws SQLException {
        db = DBHelper.getWritableDatabase();
    }

    private void close() {
        DBHelper.close();
    }

    private static final String TABLE_EMPLOYEES = "employees_table";
    public static final String COLUMN_FNAME = "worker_fname";
    public static final String COLUMN_LNAME = "worker_lname";
    public static final String COLUMN_BITHDAY = "worker_bithday";
    public static final String COLUMN_AGE = "worker_age";
    public static final String COLUMN_AVATARLINK = "worker_avatar_link";
    public static final String COLUMN_WSPECIALTY = "worker_specialty";

    private static final String TABLE_SPECIALTY = "specialty_table";
    public static final String COLUMN_SPECIALTY_ID = "specialty_id";
    public static final String COLUMN_SPECIALTY_NAME = "specialty_name";

    private static String[] GET_ALL_SPECIALTY() {
        return new String[]{COLUMN_AUTO_INCREMENT_ID, COLUMN_SPECIALTY_ID, COLUMN_SPECIALTY_NAME};
    }

    private static final String UNIQUE_INDEX_SPECIALTY = "CREATE UNIQUE INDEX USPECIALTTY ON " +
            TABLE_SPECIALTY + " (" + COLUMN_SPECIALTY_ID + ", " + COLUMN_SPECIALTY_NAME + ")";
    private static final String UNIQUE_INDEX_EMPLOYEE = "CREATE UNIQUE INDEX UEMPLOYEE ON " +
            TABLE_EMPLOYEES + " (" + COLUMN_FNAME + ", " + COLUMN_LNAME + ", " + COLUMN_BITHDAY + ", "
            + COLUMN_AGE + ", " + COLUMN_AVATARLINK + ", " + COLUMN_WSPECIALTY + ")";

    private static final String CREATE_SPECIALTY_TABLE = "create table "
            + TABLE_SPECIALTY + "(" + COLUMN_AUTO_INCREMENT_ID + " integer primary key autoincrement, "
            + COLUMN_SPECIALTY_ID + " text not null, "
            + COLUMN_SPECIALTY_NAME + " text not null)";

    public void insertSpecialty(Specialty specialty) {
        open();
        //Log.d(TAG, "DATABASE_insertSpecialty");
        ContentValues values = Specialty.getContentValues(specialty);
        try {
            db.insertOrThrow(TABLE_SPECIALTY, null, values);
        } catch (SQLiteConstraintException e) {
            //Log.d(TAG, "DATABASE_UPDATE_SPECIALTY_VALUES");
            db.update(TABLE_SPECIALTY,
                    values,
                    COLUMN_SPECIALTY_ID + "=? and " +
                            COLUMN_SPECIALTY_NAME + "=?",
                    new String[]{String.valueOf(specialty.getSpecId()),
                            specialty.getSpecName()});
        }
        close();
    }

    public ArrayList<Specialty> getAllSpecialty() {
        ArrayList<Specialty> specialtyList = new ArrayList<>();
        open();
        Cursor cur = db.query(TABLE_SPECIALTY, GET_ALL_SPECIALTY(), null, null, null, null, null);
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                Specialty specialty = new Specialty(cur.getInt(1), cur.getString(2));
                cur.moveToNext();
                specialtyList.add(specialty);
            }
        } else {
            Log.d(TAG, "No matches");
        }
        cur.close();
        close();
        return specialtyList;
    }

    private static String[] GET_ALL_WORKERS() {
        return new String[]{COLUMN_AUTO_INCREMENT_ID, COLUMN_FNAME,
                COLUMN_LNAME, COLUMN_BITHDAY, COLUMN_AGE, COLUMN_AVATARLINK, COLUMN_WSPECIALTY};
    }

    private static final String CREATE_EMPLOYEES_TABLE = "create table "
            + TABLE_EMPLOYEES + "(" + COLUMN_AUTO_INCREMENT_ID + " integer primary key autoincrement, "
            + COLUMN_FNAME + " text, "
            + COLUMN_LNAME + " text, "
            + COLUMN_BITHDAY + " text, "
            + COLUMN_AGE + " integer, "
            + COLUMN_AVATARLINK + " text, "
            + COLUMN_WSPECIALTY + " integer);";

    public void insertWorker(Worker worker) {
        open();
        //Log.d(TAG, "DATABASE_insertWorker");
        ContentValues values = Worker.getContentValues(worker);
        try {
            db.insertOrThrow(TABLE_EMPLOYEES, null, values);
        } catch (SQLiteConstraintException e) {
            //Log.d(TAG, "DATABASE_UPDATE_WORKER_VALUES");
            db.update(TABLE_EMPLOYEES,
                    values,
                    COLUMN_FNAME + "=? and " +
                            COLUMN_LNAME + "=? and " +
                            COLUMN_BITHDAY + "=? and " +
                            COLUMN_AGE + "=? and " +
                            COLUMN_AVATARLINK + "=? and " +
                            COLUMN_WSPECIALTY + "=?",
                    new String[]{worker.getF_name(), worker.getL_name(), worker.getBithday(),
                            String.valueOf(worker.getAge()), worker.getAvatarLink(),
                            String.valueOf(worker.getSpecialty())});
        }
        close();
    }

    public ArrayList<Worker> getWorkersBySpecialtyId(int specialtyId) {
        ArrayList<Worker> employeesList = new ArrayList<>();
        open();
        Cursor cur = db.query(TABLE_EMPLOYEES,
                GET_ALL_WORKERS(),
                COLUMN_WSPECIALTY + "=?",
                new String[]{String.valueOf(specialtyId)},
                null,
                null,
                null);
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                Worker worker = new Worker();
                worker.setF_name(cur.getColumnName(1));
                worker.setL_name(cur.getColumnName(2));
                worker.setBithday(cur.getColumnName(3));
                worker.setAge(cur.getInt(4));
                worker.setAvatarLink(cur.getColumnName(5));
                worker.setSpecialty(cur.getInt(6));
                employeesList.add(worker);
                cur.moveToNext();
            }
        } else {
            Log.d(TAG, "cant moveToFirst(((((((");
        }
        cur.close();
        close();
        return employeesList;
    }

    /*public void deleteAllData() {
        open();
        db.delete(TABLE_NAME, null, null);
        close();
    }*/

    /*public void deleteSingleData(String userId) {
        open();
        boolean var = db.delete(TABLE_NAME, COLUMN_USER_ID + "=" + userId, null);
        close();
    }*/
}