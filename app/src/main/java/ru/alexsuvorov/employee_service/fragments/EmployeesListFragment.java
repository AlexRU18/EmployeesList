package ru.alexsuvorov.employee_service.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.alexsuvorov.employee_service.R;

public class EmployeesListFragment extends Fragment {

    private ListView workersList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employees_list,
                container, false);
        //Bundle bundle


        //workersList = getActivity().getResources().getStringArray(R.array.test);
        workersList = view.findViewById(R.id.employees_listView);

        /*workersList.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1 , items));*/

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*ArrayAdapter<Worker> adapter = new EmployeesListAdapter(this.getContext(), );
        setListAdapter(adapter);*/
    }
}
