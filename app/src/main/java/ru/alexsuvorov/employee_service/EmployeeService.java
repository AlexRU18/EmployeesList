package ru.alexsuvorov.employee_service;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexsuvorov.employee_service.api.App;
import ru.alexsuvorov.employee_service.dao.EmployeeDao;
import ru.alexsuvorov.employee_service.dao.SpecialtyDao;
import ru.alexsuvorov.employee_service.db.AppDatabase;
import ru.alexsuvorov.employee_service.fragments.SpecialtyListFragment;
import ru.alexsuvorov.employee_service.model.Employee;
import ru.alexsuvorov.employee_service.model.ResponseModel;
import ru.alexsuvorov.employee_service.model.Specialty;
import ru.alexsuvorov.employee_service.utils.Utils;

public class EmployeeService extends AppCompatActivity {
    //private Set<Specialty> set = new HashSet<>();
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private ArrayList<Specialty> specialtyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().build();
        final EmployeeDao employeeDao = db.employeeDao();
        final SpecialtyDao specialtyDao = db.specialtyDao();
        if (Utils.isNetworkAvailable(this)) {
            ProgressDialog pdialog = ProgressDialog.show(this, "Загрузка", "Пожалуйста, подождите");
            App.getApi().getJSON().enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        //employeeList.addAll(response.body().getResponse());
                        for (int j = 0; j < response.body().getResponse().size(); j++) {
                            Utils.Log("Имя: " + response.body().getResponse().get(j).getF_name());
                            Employee employee = new Employee();
                            //employee.setEmployeeId(j);
                            employee.setF_name(response.body().getResponse().get(j).getF_name());
                            employee.setL_name(response.body().getResponse().get(j).getL_name());
                            employee.setBirthday(response.body().getResponse().get(j).getBirthday());
                            employee.setAge(response.body().getResponse().get(j).getAge());
                            employee.setAvatr_url(response.body().getResponse().get(j).getAvatr_url());

                            long lastId = employeeDao.insert(employee);
                            for (int i = 0; i < response.body().getResponse().get(j).getSpecialty().size(); i++) {
                                Utils.Log("Должность: " + response.body().getResponse().get(j).getSpecialty().get(i).getSpecName());
                                Specialty specialty = new Specialty();
                                specialty.setSpecName(response.body().getResponse().get(j).getSpecialty().get(i).getSpecName());
                                specialty.setSpecId(response.body().getResponse().get(j).getSpecialty().get(i).getSpecId());
                                specialty.setEmployeeId(lastId);
                                specialtyDao.insert(specialty);
                                //specialtyList.add(response.body().getResponse().get(j).getSpecialty().get(i));
                                //employee.setSpecialty(specialtyList);
                            }
                        }
                        Utils.Log("specialtyDao!!!!" + specialtyDao.findSpecialtiesForEmployee(response.body().getResponse().get(2).getEmployeeId()));
                        /*for (int i = 0; i < employeeDao.loadEmployeeToSpecialty(); i++) {
                            Utils.Log("EMPLOYES SERVICE|SpecName: " + employeeDao.loadEmployeeToSpecialty().get(i).specialties.size());
                            Utils.Log("EMPLOYES SERVICE|EmployeeName: " + employeeDao.loadEmployeeToSpecialty().get(i).employee.getF_name());
                        }*/
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