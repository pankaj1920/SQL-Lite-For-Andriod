package com.example.sqllitedatabaseforandriod.EmployeeDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sqllitedatabaseforandriod.MainActivity;
import com.example.sqllitedatabaseforandriod.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDetilActivity extends AppCompatActivity {

    SQLiteDatabase mDataBase;
    List<EmployeeDetailModel> employeeList;
    ListView listViewEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detil);

        employeeList = new ArrayList<>();
        listViewEmployees = (ListView) findViewById(R.id.listViewEmployees);

        //now we have to get databse name from main activity
        //it will take Three parameter 1- dataBaseName, 2-Mode ,3-CursorFactory
        // this Method will open the dataBase created Main Activity
        // here The DATABASE_NAME is in main Acivity so in MAinActivity we created Dtabase name public static so we can call in other classes
        mDataBase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        //we will call method to get EmployeeDetail
        loadEmployeeFromDataBase();
    }

    //now we have to fetch the Employee Detail from Database so we creatre a method
    private void loadEmployeeFromDataBase() {

        //in this method we need to fetch EmployeeDetail so we need to make SQL Query
        //we want to fetch all the value from our dataBase
        //for that we use SELECT * FROM DataBase Name i.e employee
        String sql = "SELECT * FROM employees";
        //now we have to execute the query but we can't use execSQL() bcz it is used only for write and update
        //so to fetch data we use rawQuery() it return a cursor and in cursor we have all the data so we create cursor object
        Cursor cursor = mDataBase.rawQuery(sql, null); // now this cursor is having all the data

        //there maye be a condition that this cursor doesn't have any data so, we have to check weather it contain data or not
        // otherwie it will exception

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
