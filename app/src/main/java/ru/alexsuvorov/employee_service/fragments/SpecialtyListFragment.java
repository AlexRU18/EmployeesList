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

import java.util.ArrayList;

import ru.alexsuvorov.employee_service.R;
import ru.alexsuvorov.employee_service.adapters.SpecialtyListAdapter;
import ru.alexsuvorov.employee_service.db.DBAdapter;
import ru.alexsuvorov.employee_service.model.Specialty;

public class SpecialtyListFragment extends Fragment {

    private FragmentManager fragmentManager;
    ArrayList<Specialty> specialtyList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.specialty_list,
                container, false);
        specialtyList = new ArrayList<>();
        DBAdapter dbAdapter = new DBAdapter(this.getContext());
        specialtyList = dbAdapter.getAllSpecialty();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final ListView specialtyListView = view.findViewById(R.id.spec_list);
        SpecialtyListAdapter adapter = new SpecialtyListAdapter(this.getContext(), R.layout.specialty_list,
                specialtyList);
        specialtyListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        specialtyListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        EmployeesListFragment employeesListFragment = new EmployeesListFragment();
                        Bundle bundle = new Bundle();
                        int specialtyId = specialtyList.get(position).getSpecId();
                        bundle.putInt("specialty_id", specialtyId);
                        employeesListFragment.setArguments(bundle);
                        fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.fragmentContainer, employeesListFragment)
                                .commit();
                    }
                }
        );
    }
}
