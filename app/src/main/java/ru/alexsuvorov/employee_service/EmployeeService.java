package ru.alexsuvorov.employee_service;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexsuvorov.employee_service.api.App;
import ru.alexsuvorov.employee_service.db.DBAdapter;
import ru.alexsuvorov.employee_service.fragments.SpecialtyListFragment;
import ru.alexsuvorov.employee_service.model.Employee;
import ru.alexsuvorov.employee_service.model.ResponseModel;
import ru.alexsuvorov.employee_service.utils.Utils;

public class EmployeeService extends AppCompatActivity {
    //private static final String URL = "http://gitlab.65apps.com/65gb/static/raw/master/testTask.json";
    //Set<Specialty> set = new HashSet<>();
    ArrayList<Employee> employeeList = new ArrayList<>();
    public DBAdapter mDbAdapter;
    private ProgressDialog pdialog;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mDbAdapter = new DBAdapter(this);
        container = findViewById(R.id.fragmentContainer);
        if (Utils.isNetworkAvailable(this)) {
            pdialog = ProgressDialog.show(this, "Загрузка", "Пожалуйста, подождите");
            App.getApi().getJSON().enqueue(new Callback<ResponseModel>() {

                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        employeeList.addAll(response.body().getResponse());
                        for (int j = 0; j < employeeList.size(); j++) {
                            Utils.Log("List " + employeeList.get(j).getF_name());
                            mDbAdapter.insertWorker(employeeList.get(j));
                        }
                    } else {
                        Utils.Log("response code " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(EmployeeService.this, "Server error", Toast.LENGTH_LONG).show();
                }
            });
            SpecialtyListFragment employeesListFragment = new SpecialtyListFragment();
            FragmentManager fManager = getSupportFragmentManager();
            fManager.beginTransaction()
                    .add(R.id.fragmentContainer, employeesListFragment)
                    .commit();
            pdialog.dismiss();

        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
    }
}