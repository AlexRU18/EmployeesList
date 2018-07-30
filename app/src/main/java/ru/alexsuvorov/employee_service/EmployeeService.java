package ru.alexsuvorov.employee_service;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ru.alexsuvorov.employee_service.db.DBAdapter;
import ru.alexsuvorov.employee_service.db.onDbListeners;
import ru.alexsuvorov.employee_service.fragments.SpecialtyListFragment;
import ru.alexsuvorov.employee_service.model.Specialty;
import ru.alexsuvorov.employee_service.model.Worker;
import ru.alexsuvorov.employee_service.utils.DateUtil;
import ru.alexsuvorov.employee_service.utils.HttpHandler;
import ru.alexsuvorov.employee_service.utils.Utils;

public class EmployeeService extends AppCompatActivity implements onDbListeners {
    private static final String URL = "http://gitlab.65apps.com/65gb/static/raw/master/testTask.json";
    ArrayList<Specialty> specialtyList = new ArrayList<>();
    Set<Specialty> set = new HashSet<>();
    ArrayList<Worker> workerList = new ArrayList<>();
    final String TAG = getClass().getSimpleName();
    public DBAdapter mDbAdapter;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mDbAdapter = new DBAdapter(this, this);
        container = findViewById(R.id.fragmentContainer);
        if (Utils.isNetworkAvailable(this)) {
            new DataLoader(this).execute(URL);
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
        SpecialtyListFragment employeesListFragment = new SpecialtyListFragment();
        FragmentManager fManager = getSupportFragmentManager();
        fManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragmentContainer, employeesListFragment)
                .commit();
    }

    @Override
    public void onOperationSuccess(String tableName, int operation, Object obj) {

    }

    @Override
    public void onOperationFailed(String tableName, int operation) {

    }

    @SuppressLint("StaticFieldLeak")
    class DataLoader extends AsyncTask<String, Void, Void> {

        @SuppressLint("StaticFieldLeak")
        private Context mContext;
        private ProgressDialog pdialog;
        private final String TAG = getClass().getSimpleName();

        DataLoader(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = ProgressDialog.show(mContext, "Загрузка", "Пожалуйста, подождите");
        }

        @Override
        protected Void doInBackground(String... params) {
            if (!isCancelled()) {
                try {
                    HttpHandler sh = new HttpHandler();
                    String jsonStr = sh.makeServiceCall(params[0]);
                    try {
                        JSONObject reader = new JSONObject(jsonStr);
                        JSONArray response = reader.getJSONArray("response");
                        for (int i = 0; i < response.length(); i++) {
                            String workerFName = response.getJSONObject(i).getString("f_name");
                            String workerLName = response.getJSONObject(i).getString("l_name");
                            String workerBithday = response.getJSONObject(i).getString("birthday");
                            String workerAvatarUrl = response.getJSONObject(i).getString("avatr_url");
                            int specialtyId = response.getJSONObject(i)
                                    .getJSONArray("specialty")
                                    .getJSONObject(0)
                                    .getInt("specialty_id");
                            String specialtyName = response.getJSONObject(i)
                                    .getJSONArray("specialty")
                                    .getJSONObject(0)
                                    .getString("name");
                            Specialty specialty = new Specialty(specialtyId, specialtyName);
                            set.add(specialty);
                            Worker worker = new Worker();
                            worker.setF_name(workerFName);
                            worker.setL_name(workerLName);
                            worker.setBithday(workerBithday);
                            worker.setAge(DateUtil.calculateAge(workerBithday));  //!!!!!!!!!!!!!!
                            worker.setAvatarLink(workerAvatarUrl);
                            worker.setSpecialty(specialtyId);
                            workerList.add(worker);
                            mDbAdapter.insertWorker(worker);
                        }
                        for (Specialty specialty : specialtyList = new ArrayList<>(set)) {
                            mDbAdapter.insertSpecialty(specialty);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mContext != null) {
                /*SpecialtyListAdapter adapter = new SpecialtyListAdapter(this.mContext, R.layout.specialty_list,
                        specialtyList = new ArrayList<>(set));
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/



                /*dManager = new DBManager(mContext);
                SQLiteDatabase sqLiteDatabase = dManager.getWritableDatabase();
                Cursor cursor;

                for (int i = 0; i < specialtyList.size(); i++) {
                    contentValues.clear();
                    contentValues.put("id", specialtyList.get(i).getSpecId());
                    contentValues.put("name", specialtyList.get(i).getSpecName());
                    sqLiteDatabase.insert("specialty", null, contentValues);
                }

                sqLiteDatabase.execSQL("create table employees ("
                        + "id integer primary key autoincrement, "
                        + "fname text, "
                        + "lname text, "
                        + "bithday text, "
                        + "age integer, "
                        + "avatarLink text, "
                        + "specialty integer"
                        + ");");

                for (int i = 0; i < workerList.size(); i++) {
                    contentValues.clear();
                    contentValues.put("fname", workerList.get(i).getF_name());
                    contentValues.put("lname", workerList.get(i).getL_name());
                    contentValues.put("bithday", workerList.get(i).getBithday());
                    contentValues.put("age", workerList.get(i).getAge());
                    contentValues.put("avatarLink", workerList.get(i).getAvatarLink());
                    contentValues.put("specialty", workerList.get(i).getSpecialty());
                    sqLiteDatabase.insert("employees", null, contentValues);
                }

                //Log.d(TAG, "---Table specialty---");
                cursor = sqLiteDatabase.query("specialty", null, null, null, null, null, null);
                //logCursor(cursor);
                cursor.close();
                Log.d(TAG, "--- ---");

                //Log.d(TAG, "---Table employees---");
                cursor = sqLiteDatabase.query("employees", null, null, null, null, null, null);
                //logCursor(cursor);
                cursor.close();
                Log.d(TAG, "--- ---");
                dManager.close();*/

                pdialog.dismiss();
            }
        }
    }
}