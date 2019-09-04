package com.example.sqllitedatabaseforandriod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //we need the name for our dataBase and we created Dtabase name public static so we can call dataBase name in other classes
    public static final String DATABASE_NAME = "mydatabase";
    SQLiteDatabase mDataBase;
    EditText editTextName, editTextSalary;
    Spinner spinnerDepartment;
    Button buttonAddEmployee;
    TextView textViewViewEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSalary = (EditText) findViewById(R.id.editTextSalary);
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);
        buttonAddEmployee = (Button) findViewById(R.id.buttonAddEmployee);
        textViewViewEmployees = (TextView) findViewById(R.id.textViewViewEmployees);

        buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addEmployee();
            }
        });

        textViewViewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,EmployeeDetilActivity.class);
                startActivity(intent);

            }
        });

        //now we will create dataBase ... to create Database we Call a method
        //it will take Three parameter 1- dataBaseName, 2-Mode ,3-CursorFactory
        // this Method will create database with " mydatabase name" and if dataBase already Exist with this name it will open the dataBase
        mDataBase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        //Call merhod to create Table
        CreateTable();

        // now we have to insert value in this table that we will get from editText

    }

    //Now we have to Create Table for that we are creating seprate method
    private void CreateTable() {
        //to create table first we have to create Query we we can use this Query to creaate our database table
        String sql = "CREATE TABLE IF NOT EXISTS employees (\n" +
                "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    name varchar(200) NOT NULL,\n" +
                "    department varchar(200) NOT NULL,\n" +
                "    joiningdate datetime NOT NULL,\n" +
                "    salary double NOT NULL\n" +
                ");";

        //to Execute the Query and this Query will make table in our datBase with name employee
        mDataBase.execSQL(sql);
    }

    private void addEmployee() {

        String name = editTextName.getText().toString().trim();
        String salary = editTextSalary.getText().toString().trim();
        String department = spinnerDepartment.getSelectedItem().toString();

        if (name.isEmpty()) {
            editTextName.setError("Enter the Name");
            editTextName.requestFocus();
            return;
        }

        if (salary.isEmpty()) {
            editTextSalary.setError("Enter the Salary");
            editTextSalary.requestFocus();
            return;
        }

        //Getting CurrentDate in DateFormat
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = simpleDateFormat.format(calendar.getTime());


        //after vaildation is complete now we can save the value in database for that again we need to create SQL Query
        //here we defines colom inside () parentheses we don't need id bcz id is auto increment from above query
        //so in parenthese we will pass name , department joiningdate,salary from above query
        //to Inset we use INSERT INTO TableName i.e employee
        String sql = "INSERT INTO employees(name,department,joiningdate,salary)" +
                "VALUES (?,?,?,?)"; // here 4 ? bcz we need to bind four values

        //here we will take execSQL with bindArgs do that we can bind data to values
        mDataBase.execSQL(sql, new String[]{name, department, joiningDate, salary});
        Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();

    }

}
