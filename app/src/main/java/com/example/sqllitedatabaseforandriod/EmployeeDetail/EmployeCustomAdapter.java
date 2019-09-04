package com.example.sqllitedatabaseforandriod.EmployeeDetail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sqllitedatabaseforandriod.R;

import java.util.List;

public class EmployeCustomAdapter extends ArrayAdapter<EmployeeDetailModel> {

    Context context;
    int layoutResource;
    List<EmployeeDetailModel> employeeList;
    SQLiteDatabase mDatabase;

    public EmployeCustomAdapter(@NonNull Context context, int layoutResource, @NonNull List<EmployeeDetailModel> employeeList, SQLiteDatabase mDatabase) {
        super(context, layoutResource, employeeList);
        this.context = context;
        this.layoutResource = layoutResource;
        this.employeeList = employeeList;
        this.mDatabase = mDatabase;
    }

    //overRide getView method

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //here we will bind the data to our view and we will return the view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResource, null);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDepartment = view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);
        Button buttonEditEmployee = (Button) view.findViewById(R.id.buttonEditEmployee);
        Button buttonDeleteEmployee = (Button) view.findViewById(R.id.buttonDeleteEmployee);

        final EmployeeDetailModel employeeDetail = employeeList.get(position);

        textViewName.setText(employeeDetail.getName());
        textViewDepartment.setText(employeeDetail.getDepartment());
        textViewSalary.setText(String.valueOf(employeeDetail.getSalary()));
        textViewJoiningDate.setText(employeeDetail.getJoiningdate());


        buttonEditEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here we will call new method which will update employee
                updateEmployee(employeeDetail);
            }
        });

        buttonDeleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletEmployee(employeeDetail);
            }
        });

        return view;
    }


    // This method to Update Employee
    private void updateEmployee(final EmployeeDetailModel employeeDetail) {

        //Create layout for alert Dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_detail, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //we have to putt current value to the editText of custom dialogbox
        final EditText updateName = (EditText) view.findViewById(R.id.updateName);
        final EditText updateSalary = (EditText) view.findViewById(R.id.updateSalary);
        final Spinner updatespinnerDepartment = (Spinner) view.findViewById(R.id.updatespinnerDepartment);
        Button btnUpdate = (Button) view.findViewById(R.id.btnUpdate);

        //Now we will set the current value whic we will get from employee object
        updateName.setText(employeeDetail.getName());
        updateSalary.setText(String.valueOf(employeeDetail.getSalary()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = updateName.getText().toString().trim();
                String salary = updateSalary.getText().toString().trim();
                String department = updatespinnerDepartment.getSelectedItem().toString().trim();
                String id = String.valueOf(employeeDetail.getId()); //here we have id inside employee object

                if (name.isEmpty()) {
                    updateName.setError("Enter the Name");
                    updateName.requestFocus();
                    return;
                }

                if (salary.isEmpty()) {
                    updateSalary.setError("Enter the Salary");
                    updateSalary.requestFocus();
                    return;
                }

                //afte validation we need to update Database so we will create a Query
                String sql = "UPDATE employees SET name = ? ,department = ?, salary = ? WHERE id = ? ";

                //execute the Query
                mDatabase.execSQL(sql, new String[]{name, department, salary, id});
                Toast.makeText(context, "Updated Sucessfully", Toast.LENGTH_SHORT).show();

                //this method will call dataBase again as we update the list
                LoadEmployessFromDAtabaseAgain();

                alertDialog.dismiss();

            }
        });
    }

    //This method to loadEmployee List again after Updaating
    private void LoadEmployessFromDAtabaseAgain() {


        //in this method we need to fetch Updated EmployeeDetail so we need to make SQL Query
        //we want to fetch all the value from our dataBase
        //for that we use SELECT * FROM DataBase Name i.e employee
        String sql = "SELECT * FROM employees";
        // to fetch data we use rawQuery() it return a cursor and in cursor we have all the data so we create cursor object
        Cursor cursor = mDatabase.rawQuery(sql, null); // now this cursor is having all the data

        //there maye be a condition that this cursor doesn't have any data so, we have to check weather it contain data or not
        // otherwie it will exception
        if (cursor.moveToFirst()) {
            //here we will first clear the employe list
            employeeList.clear();
        }
        //if cursor is able to move to its first record that means it contain data and we will fetch data
        //now cursor is already in his first position and we want to get data one by one

        do {
            //heer we will get updated values
            employeeList.add(new EmployeeDetailModel(
                    //here we are getting data from Database and we have to write in sequence
                    cursor.getInt(0), //for id
                    cursor.getString(1),// for name
                    cursor.getString(2),// for department
                    cursor.getString(3),// for joinindate
                    cursor.getDouble(4) // for salary
            ));

        } while (cursor.moveToNext());
        //this loop will run until cursor.moveToNext is true

        notifyDataSetChanged();
    }


    //this method to Delet Employee
    private void deletEmployee(final EmployeeDetailModel employeeDetail) {
        //here before deteling the Employee we will show alertDialog to confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Are You Sure");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //when user click on yes we need to delet employee from database for that we need Qery
                String sql = "DELETE FROM employees WHERE id = ? ";
                mDatabase.execSQL(sql, new String[]{String.valueOf(employeeDetail.getId())});
                // after deteting Employee we need to load database again
                LoadEmployessFromDAtabaseAgain();

            }
        });


        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
