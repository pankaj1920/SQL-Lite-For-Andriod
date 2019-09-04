package com.example.sqllitedatabaseforandriod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EmployeCustomAdapter extends ArrayAdapter<EmployeeDetailModel> {

    Context context;
    int layoutResource;
    List<EmployeeDetailModel> employeeList;

    public EmployeCustomAdapter(@NonNull Context context, int layoutResource, @NonNull List<EmployeeDetailModel> employeeList) {
        super(context, layoutResource, employeeList);
        this.context = context;
        this.layoutResource = layoutResource;
        this.employeeList = employeeList;
    }

    //overRide getView method

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //here we will bind the data to our view and we will return the view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResource,null);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDepartment = view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);

        EmployeeDetailModel employeeDetail = employeeList.get(position);

        textViewName.setText(employeeDetail.getName());
        textViewDepartment.setText(employeeDetail.getDepartment());
        textViewSalary.setText(String.valueOf(employeeDetail.getSalary()));
        textViewJoiningDate.setText(employeeDetail.getJoiningdate());

        return view;
    }
}
