package ru.alexsuvorov.employee_service.db;

/*public class DBAdapter {

    private final int DATABASE_VERSION = 1;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "db";
    private static final String COLUMN_AUTO_INCREMENT_ID = "auto_increment_id";
    private final String TAG = getClass().getSimpleName();

    public DBAdapter(Context context) {
        DBHelper = new DatabaseHelper(context);
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIALTY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
            onCreate(db);
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
    public static final String COLUMN_BIRTHDAY = "worker_birthday";
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
            TABLE_EMPLOYEES + " (" + COLUMN_FNAME + ", " + COLUMN_LNAME + ", " + COLUMN_BIRTHDAY + ", "
            + COLUMN_AGE + ", " + COLUMN_AVATARLINK + ", " + COLUMN_WSPECIALTY + ")";

    private static final String CREATE_SPECIALTY_TABLE = "create table "
            + TABLE_SPECIALTY + "(" + COLUMN_AUTO_INCREMENT_ID + " integer primary key autoincrement, "
            + COLUMN_SPECIALTY_ID + " text not null UNIQUE, "
            + COLUMN_SPECIALTY_NAME + " text not null, "
            + " FOREIGN KEY (" + COLUMN_AUTO_INCREMENT_ID + ") REFERENCES " + TABLE_EMPLOYEES + "(" + "UEMPLOYEE" + "));";

    /*public void insertSpecialty(Specialty specialty) {
        open();
        ContentValues values = Specialty.getContentValues(specialty);
        try {
            db.insertOrThrow(TABLE_SPECIALTY, null, values);
        } catch (SQLiteConstraintException e) {
            db.update(TABLE_SPECIALTY,
                    values,
                    COLUMN_SPECIALTY_ID + "=? and " +
                            COLUMN_SPECIALTY_NAME + "=?",
                    new String[]{String.valueOf(specialty.getSpecId()),
                            specialty.getSpecName()});
        }
        close();
    }

    /*public ArrayList<Specialty> getAllSpecialty() {
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
            Log.e(TAG, "No matches");
        }
        cur.close();
        close();
        return specialtyList;
    }

    private static String[] GET_ALL_WORKERS() {
        return new String[]{COLUMN_AUTO_INCREMENT_ID, COLUMN_FNAME,
                COLUMN_LNAME, COLUMN_BIRTHDAY, COLUMN_AGE, COLUMN_AVATARLINK, COLUMN_WSPECIALTY};
    }

    private static final String CREATE_EMPLOYEES_TABLE = "create table "
            + TABLE_EMPLOYEES + "(" + COLUMN_AUTO_INCREMENT_ID + " integer primary key autoincrement, "
            + COLUMN_FNAME + " text, "
            + COLUMN_LNAME + " text, "
            + COLUMN_BIRTHDAY + " text, "
            + COLUMN_AGE + " integer, "
            + COLUMN_AVATARLINK + " text, "
            + COLUMN_WSPECIALTY + " integer, "
            + " FOREIGN KEY (" + COLUMN_AUTO_INCREMENT_ID + ") REFERENCES " + TABLE_SPECIALTY + "(" + "UEMPLOYEE" + "));";

    public void insertWorker(Employee employee) {
        open();
        ContentValues values = Employee.getContentValues(employee);
        try {
            db.insertOrThrow(TABLE_EMPLOYEES, null, values);
        } catch (SQLiteConstraintException e) {
            db.update(TABLE_EMPLOYEES,
                    values,
                    COLUMN_FNAME + "=? and " +
                            COLUMN_LNAME + "=? and " +
                            COLUMN_BIRTHDAY + "=? and " +
                            COLUMN_AGE + "=? and " +
                            COLUMN_AVATARLINK + "=? and " +
                            COLUMN_WSPECIALTY + "=?",
                    new String[]{employee.getF_name(), employee.getL_name(), employee.getBirthday(),
                            String.valueOf(employee.getAge()), employee.getAvatarLink(),
                            String.valueOf(employee.getSpecialty())});
        }
        close();
    }

    public ArrayList<Employee> getWorkersBySpecialtyId(int specialtyId) {
        ArrayList<Employee> employeesList = new ArrayList<>();
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
                Employee employee = new Employee();
                employee.setF_name(cur.getString(cur.getColumnIndex("worker_fname")));
                employee.setL_name(cur.getString(cur.getColumnIndex("worker_lname")));
                employee.setBirthday(cur.getString(cur.getColumnIndex("worker_birthday")));
                employee.setAge(cur.getInt((cur.getColumnIndex("worker_age"))));
                employee.setAvatarLink(cur.getString(cur.getColumnIndex("worker_avatar_link")));
                employee.setSpecialty(cur.getInt((cur.getColumnIndex("worker_specialty"))));
                employeesList.add(employee);
                cur.moveToNext();
            }
        } else {
            Log.d(TAG, "cant moveToFirst(((((((");
        }
        cur.close();
        close();
        return employeesList;
    }

    public String getSpecialtyNameById(int specialtyId) {
        open();
        String specialtyName = null;
        Cursor cur = db.query(TABLE_SPECIALTY,
                GET_ALL_SPECIALTY(),
                COLUMN_SPECIALTY_ID + "=?",
                new String[]{String.valueOf(specialtyId)},
                null,
                null,
                null);
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                specialtyName = cur.getString(2);
            }
        } else {
            Log.e(TAG, "cant moveToFirst(((((((");
        }
        cur.close();
        close();
        return specialtyName;
    }
    public void deleteAllData() {
        open();
        db.delete(TABLE_NAME, null, null);
        close();
    }

    public void deleteSingleData(String userId) {
        open();
        boolean var = db.delete(TABLE_NAME, COLUMN_USER_ID + "=" + userId, null);
        close();
    }
}*/