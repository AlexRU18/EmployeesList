package ru.alexsuvorov.employee_service.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ru.alexsuvorov.employee_service.R;
import ru.alexsuvorov.employee_service.db.DBAdapter;

public class SpecialtyListFragment extends Fragment {

    private FragmentManager fragmentManager;
    private DBAdapter dbAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.specialty_list,
                container, false);
        dbAdapter.getAllSpecialty();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView specialtyListView = view.findViewById(R.id.spec_list);

        specialtyListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        EmployeesListFragment employeesListFragment = new EmployeesListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("specialty_position", position);
                        employeesListFragment.setArguments(bundle);
                        fragmentManager = getFragmentManager();  //Support?
                        fragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .add(R.id.fragmentContainer, employeesListFragment)
                                .commit();
                    }
                }
        );
    }
}
