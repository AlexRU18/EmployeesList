package ru.alexsuvorov.employeeslist.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.alexsuvorov.employeeslist.R;
import ru.alexsuvorov.employeeslist.model.Specialty;

public class SpecialtyListAdapter extends ArrayAdapter<Specialty> {

    private ArrayList<Specialty> specialtyList;
    private Resources res = getContext().getResources();

    public SpecialtyListAdapter(@NonNull Context context, int layout, @NonNull ArrayList<Specialty> specialtyList) {
        super(context, layout, specialtyList);
        this.specialtyList = specialtyList;
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert inflater != null;
            view = inflater.inflate(R.layout.specialty_item, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Specialty specialty = specialtyList.get(position);

        holder.specId = view.findViewById(R.id.specialty_id);
        holder.specName = view.findViewById(R.id.specialty_name);

        if (holder.specId != null) {
            holder.specId.setText(String.format(res.getString(R.string.spec_id_prefix), String.valueOf((specialty.getSpecId()))));
        }
        if (holder.specName != null && null != specialty.getSpecName()) {
            holder.specName.setText(specialty.getSpecName());
        }
        return view;
    }


    private class ViewHolder {
        private TextView specId, specName;
    }
}