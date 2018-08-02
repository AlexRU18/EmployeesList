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
import ru.alexsuvorov.employee_service.model.Employee;

public class EmployeesListAdapter extends ArrayAdapter<Employee> {

    public EmployeesListAdapter(@NonNull Context context, int resource, @NonNull List<Employee> employees) {
        super(context, resource, employees);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        Employee employee = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.employees_item, null);
        }
        ((TextView) view.findViewById(R.id.employee_Fname))
                .setText(employee.getF_name());
        ((TextView) view.findViewById(R.id.employee_Lname))
                .setText(employee.getL_name());
        if (employee.getAge() == 0) {
            ((TextView) view.findViewById(R.id.employee_age)).setText("-");
        } else {
            ((TextView) view.findViewById(R.id.employee_age)).setText(String.valueOf(employee.getAge()));
        }
        return view;
    }
}
