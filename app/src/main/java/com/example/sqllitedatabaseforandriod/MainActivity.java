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

import com.example.sqllitedatabaseforandriod.EmployeeDetail.EmployeeDetilActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //we need the name for our dataBase and we created Dtabase name public static so we can call dataBase name in other classes
    public static final String DATABASE_NAME = "mydatabase";
    DatabaseManager mDataBase;
    EditText editTextName, editTextSalary;
    Spinner spinnerDepartment;
    Button buttonAddEmployee;
    TextView textViewViewEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Callin DatabaseManage Instance
        mDataBase = new DatabaseManager(this);


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

                Intent intent = new Intent(MainActivity.this, EmployeeDetilActivity.class);
                startActivity(intent);

            }
        });

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
       if (mDataBase.addEmployee(name,department,joiningDate,Double.parseDouble(salary))) {
           Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
       }else {
           Toast.makeText(this, "Employee Not Added", Toast.LENGTH_SHORT).show();
       }

    }

}
