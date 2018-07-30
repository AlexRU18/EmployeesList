package ru.alexsuvorov.employee_service.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.employee_service.R;
import ru.alexsuvorov.employee_service.model.Worker;

public class EmployeesListAdapter extends ArrayAdapter<Worker> {

    public EmployeesListAdapter(@NonNull Context context, int resource, @NonNull List<Worker> workers) {
        super(context, resource, workers);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Worker worker = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.employees_item, null);
        }
        ((TextView) view.findViewById(R.id.employee_Fname))
                .setText(worker.getF_name());
        ((TextView) view.findViewById(R.id.employee_Lname))
                .setText(worker.getL_name());
        return view;
    }
}
