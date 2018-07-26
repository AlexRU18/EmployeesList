package ru.alexsuvorov.employeeslist;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.alexsuvorov.employeeslist.adapters.SpecialtyListAdapter;
import ru.alexsuvorov.employeeslist.fragments.EmployeesListFragment;
import ru.alexsuvorov.employeeslist.model.Specialty;
import ru.alexsuvorov.employeeslist.model.Worker;
import ru.alexsuvorov.employeeslist.utils.HttpHandler;
import ru.alexsuvorov.employeeslist.utils.Utils;

public class SpecialtyListActivity extends AppCompatActivity {
    private static final String URL = "http://gitlab.65apps.com/65gb/static/raw/master/testTask.json";
    public ArrayList<Specialty> specialtyList = new ArrayList<>();
    public ArrayList<Worker> workerList = new ArrayList<>();
    ListView listView;
    EmployeesListFragment employeesListFragment;
    private final String TAG = getClass().getSimpleName();
    DataLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specialty_list);
        listView = findViewById(R.id.spec_list);
        employeesListFragment = new EmployeesListFragment();
        if (Utils.isNetworkAvailable(this)) {
            loader = new DataLoader(this);
            if (!loader.isCancelled()) {
                loader.execute(URL);
            }
            //new DataLoader(this).execute(URL);
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
                            String workerBithday = response.getJSONObject(i).getString("birthday");
                            String workerAvatarUrl = response.getJSONObject(i).getString("avatr_url");
                            int workerSpecialtyId = response.getJSONObject(i)
                                    .getJSONArray("specialty")
                                    .getJSONObject(0)
                                    .getInt("specialty_id");
                            String specialtyName = response.getJSONObject(i)
                                    .getJSONArray("specialty")
                                    .getJSONObject(0)
                                    .getString("name");
                            Specialty specialty = new Specialty(workerSpecialtyId, specialtyName);
                            if (!specialtyList.contains(specialty)) {
                                specialtyList.add(specialty);
                            }
                            Worker worker = new Worker(workerFName, workerLName);
                            worker.setBithday(workerBithday);
                            worker.setAvatarLink(workerAvatarUrl);
                            worker.setSpecialty(workerSpecialtyId);
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
                SpecialtyListAdapter adapter = new SpecialtyListAdapter(this.mContext, R.layout.specialty_list, specialtyList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pdialog.dismiss();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
        }
    }
}