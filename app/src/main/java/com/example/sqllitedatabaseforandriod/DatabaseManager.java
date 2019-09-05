package com.example.sqllitedatabaseforandriod;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EmployeeDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEPT = "department";
    private static final String COLUMN_JOIN_DATE = "joiningdate";
    private static final String COLUMN_SALARY = "salary";

    //Create a Constructor
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //here we will createDatabase
        //to create table first we have to create Query we we can use this Query to creaate our database table
        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_DEPT + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_JOIN_DATE + " datetime NOT NULL,\n" +
                "    " + COLUMN_SALARY + " double NOT NULL\n" +
                ");";

        //now we will execute this Query inside SQLiteDatabase
        //and this execSql() method we should only use to create table
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //to insert new Employee we will create a method
    public boolean addEmployee(String name, String department, String joiningdate, double salary) {

        //to add employee we need writeable Database for that we already have method i.e getWritableDatabase()
        // this method will return the instance of SQLite dataBase
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //now this sqLiteDatabase is a writeable database we can Insert the Value
        ContentValues contentValues = new ContentValues();
        //inside this contentVAlue we will insert all the value we want to insert
        //the first parmeter is columName and second patemeter is value of that coloum
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, department);
        contentValues.put(COLUMN_JOIN_DATE, joiningdate);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        //to add data we use insert()

        //how we will define value is inserted or not
        //this insertMethod will return -1 when nothing is inserted and it will return row id if the insertion was sucessful
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1; // it mean value are inserted sucessfully


    }

    //to ReadEmployee...the read method will return a Cursor Object
    //so the return tupe is Cursor
    public Cursor getEmployeeDetail() {

        //this time we need Readable Database for that we have method i.e getReadableDatabase()
        // this method will return the instance of SQLite dataBase
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //now we can use rawQuery method to read all the employee from Table
        //the second Parameter is the value to bind selectionArgument ... in that we will pass id
        // return sqLiteDatabase.rawQuery("SELECT * FROM employees where id = ? "+TABLE_NAME,null); //here instead of null we will pass id of ?
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);


    }

    //to Update The EmployeeDetail
    //here we need employeId to update
    public boolean updateEmployee(int id, String name, String department, double salary) {

        //to update employee we need writeable Database for that we already have method i.e getWritableDatabase()
        // this method will return the instance of SQLite dataBase
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //inside this contentVAlue we will insert all the value we want to insert
        //the first parmeter is columName and second patemeter is value of that coloum
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, department);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        //with datBase Instance we will call updateMethod
        //this method will return number of row updated ...if it is > 0 that means update is sucessfull
        return sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=? ", new String[]{String.valueOf(id)}) > 0;

    }

    //Delet Employee Detail
    public boolean DeletEmployee(int id) {

        //to Delet employee we need writeable Database for that we already have method i.e getWritableDatabase()
        // this method will return the instance of SQLite dataBase
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //this deletmethod return no row deleted ... if it is > 0 it means row sucessfully deleted
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }
}
