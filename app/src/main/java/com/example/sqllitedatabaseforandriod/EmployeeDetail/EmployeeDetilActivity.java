package com.example.sqllitedatabaseforandriod.EmployeeDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sqllitedatabaseforandriod.DatabaseManager;
import com.example.sqllitedatabaseforandriod.MainActivity;
import com.example.sqllitedatabaseforandriod.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDetilActivity extends AppCompatActivity {

    DatabaseManager mDataBase;
    List<EmployeeDetailModel> employeeList;
    ListView listViewEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detil);

        //Callin DatabaseManage Instance
        mDataBase = new DatabaseManager(this);

        employeeList = new ArrayList<>();
        listViewEmployees = (ListView) findViewById(R.id.listViewEmployees);


        //we will call method to get EmployeeDetail
        loadEmployeeFromDataBase();
    }

    //now we have to fetch the Employee Detail from Database so we creatre a method
    private void loadEmployeeFromDataBase() {

      Cursor cursor = mDataBase.getEmployeeDetail();
        if (cursor.moveToFirst()) {
            //if cursor is able to move to its first record that means it contain data and we will fetch data
            //now cursor is already in his first position and we want to get data one by one

            do {
                //now we have multiple Employee so we will display data in Custom List View we can use Recycler View Also
                //Create CustsomListView and Employee model class
                //Create CustomAdapter for list View
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

            EmployeCustomAdapter adapter = new EmployeCustomAdapter(this, R.layout.employee_custom_lsitview, employeeList,mDataBase);
            listViewEmployees.setAdapter(adapter);
        }


    }
}
