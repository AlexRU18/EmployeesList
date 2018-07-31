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
import ru.alexsuvorov.employee_service.fragments.SpecialtyListFragment;
import ru.alexsuvorov.employee_service.model.Specialty;
import ru.alexsuvorov.employee_service.model.Worker;
import ru.alexsuvorov.employee_service.utils.DateUtil;
import ru.alexsuvorov.employee_service.utils.HttpHandler;
import ru.alexsuvorov.employee_service.utils.Utils;

public class EmployeeService extends AppCompatActivity {
    private static final String URL = "http://gitlab.65apps.com/65gb/static/raw/master/testTask.json";
    Set<Specialty> set = new HashSet<>();
    ArrayList<Worker> workerList = new ArrayList<>();
    public DBAdapter mDbAdapter;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mDbAdapter = new DBAdapter(this);
        container = findViewById(R.id.fragmentContainer);
        if (Utils.isNetworkAvailable(this)) {
            new DataLoader(this).execute(URL);
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
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
                            String birthdayDateResponse = response.getJSONObject(i).getString("birthday");
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
                            String workerBirthday = DateUtil.validDateString(birthdayDateResponse);
                            worker.setBirthday(workerBirthday);
                            int workerAge = DateUtil.calculateAge(workerBirthday);
                            worker.setAge(workerAge);
                            worker.setAvatarLink(workerAvatarUrl);
                            worker.setSpecialty(specialtyId);
                            workerList.add(worker);
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
                for (Specialty specialty : set) {
                    mDbAdapter.insertSpecialty(specialty);
                }
                for (Worker worker : workerList) {
                    mDbAdapter.insertWorker(worker);
                }
                SpecialtyListFragment employeesListFragment = new SpecialtyListFragment();
                FragmentManager fManager = getSupportFragmentManager();
                fManager.beginTransaction()
                        .add(R.id.fragmentContainer, employeesListFragment)
                        .commit();
                pdialog.dismiss();
            }
        }
    }
}