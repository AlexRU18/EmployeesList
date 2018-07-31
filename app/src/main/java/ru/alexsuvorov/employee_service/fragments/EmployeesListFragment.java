package ru.alexsuvorov.employee_service.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ru.alexsuvorov.employee_service.R;
import ru.alexsuvorov.employee_service.adapters.EmployeesListAdapter;
import ru.alexsuvorov.employee_service.db.DBAdapter;
import ru.alexsuvorov.employee_service.model.Worker;

public class EmployeesListFragment extends Fragment {

    private FragmentManager fragmentManager;
    ArrayList<Worker> workersList;
    Worker worker;
    int position;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employees_list,
                container, false);
        Bundle bundle = getArguments();
        position = bundle.getInt("specialty_id");
        workersList = new ArrayList<>();
        DBAdapter dbAdapter = new DBAdapter(this.getContext());
        workersList = dbAdapter.getWorkersBySpecialtyId(position);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView workersListView = view.findViewById(R.id.employees_listView);
        EmployeesListAdapter adapter = new EmployeesListAdapter(this.getContext(), R.layout.employees_list,
                workersList);
        workersListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        workersListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        EmployeeDetail employeeDetail = new EmployeeDetail();
                        Bundle bundle = new Bundle();
                        worker = workersList.get(position);
                        if (worker != null) {
                            bundle.putSerializable("worker", worker);
                        } else {
                            Log.e("worker", "is null");
                        }
                        employeeDetail.setArguments(bundle);
                        fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.fragmentContainer, employeeDetail)
                                .commit();
                    }
                }
        );
    }
}
