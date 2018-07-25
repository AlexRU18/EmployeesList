package ru.alexsuvorov.employeeslist;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.alexsuvorov.employeeslist.Utils.HttpHandler;
import ru.alexsuvorov.employeeslist.Utils.Utils;
import ru.alexsuvorov.employeeslist.model.Specialty;
import ru.alexsuvorov.employeeslist.model.Worker;

public class SpecialtyListActivity extends AppCompatActivity {

    private static final String URL = "http://gitlab.65apps.com/65gb/static/raw/master/testTask.json";
    public static ArrayList<Specialty> specialtyList = new ArrayList<>();
    public static ArrayList<Worker> workerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specialty_list);

        if (Utils.isNetworkAvailable(this)) {
            new DataLoader(this).execute(URL);
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    static class DataLoader extends AsyncTask<String, Void, Void> {

        @SuppressLint("StaticFieldLeak")
        private Context mContext;
        private ProgressDialog pdialog;
        private final String TAG = getClass().getSimpleName();

        DataLoader (Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = ProgressDialog.show(mContext.getApplicationContext(), "Загрузка", "Пожалуйста, подождите");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(mContext != null){
                pdialog.dismiss();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(params[0]);
                try {
                    JSONObject reader = new JSONObject(jsonStr);
                    JSONArray response = reader.getJSONArray("response");

                    for (int i=0; i < response.length(); i++) {
                        String workerFName = response.getJSONObject(i).getString("f_name");
                        String workerLName = response.getJSONObject(i).getString("l_name");
                        String workerBithday = response.getJSONObject(i).getString("birthday");
                        String workerAvatarUrl = response.getJSONObject(i).getString("avatr_url");
                        int workerSpecialtyId = response.getJSONObject(i)
                                .getJSONArray("specialty")
                                .getInt(Integer.parseInt("specialty_id"));
                        String specialtyName = response.getJSONObject(i)
                                .getJSONArray("specialty")
                                .getJSONObject(1)
                                .getString("name");
                        Specialty specialty = new Specialty(workerSpecialtyId,specialtyName);
                        if(!specialtyList.contains(specialty)){
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
            return null;
        }
    }
}
