package pt.jpazevedo.mrmanager.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pt.jpazevedo.mrmanager.entities.Expense;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class ManageLocalRequests {

    static String EXPENSE_COLUMN_NAME = "name";
    static String EXPENSE_COLUMN_DESCRIPTION = "description";
    static String EXPENSE_COLUMN_VALUE = "value";
    static String EXPENSE_COLUMN_DATE = "date";
    static String EXPENSE_COLUMN_CATEGORYID = "category_id";
    static String EXPENSE_COLUMN_ID = "expense_id";

    private  Context context;

    public ManageLocalRequests(Context context){
        this.context = context;
        new SetupDB(context);
    }

    public List<Expense> getAllExpenses(){
        SetupDB dbHelper = new SetupDB(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getAllExpenses(database);
        String name;
        String value;
        String description;
        String date;
        String category_id;
        String id;
        List<Expense> expenses = new ArrayList<>();


        try {
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    name = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_NAME));
                    date = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_DATE));
                    value = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_VALUE));
                    description = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_DESCRIPTION));
                    category_id = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_CATEGORYID));
                    id = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_ID));
                    expenses.add(new Expense(name,description,Float.valueOf(value),Long.parseLong(date),Integer.parseInt(category_id),Long.parseLong(id)));
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }


        closeDB(database,dbHelper);
        return expenses;
    }

    public long insertExpense(String name, long date, int category_id, String description, float value){
        SetupDB dbHelper = new SetupDB(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long id = dbHelper.insertExpense(database,name,date,category_id,description,value);
        closeDB(database,dbHelper);
        return id;
    }

    public List<Expense> getMonthExpenses(long date_ms){
        SetupDB dbHelper = new SetupDB(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getMonthExpenses(date_ms,database);
        String name;
        String value;
        String description;
        String date;
        String category_id;
        String id;
        List<Expense> expenses = new ArrayList<>();


        try {
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    name = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_NAME));
                    date = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_DATE));
                    value = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_VALUE));
                    description = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_DESCRIPTION));
                    category_id = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_CATEGORYID));
                    id = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_ID));
                    expenses.add(new Expense(name,description,Float.valueOf(value),Long.parseLong(date),Integer.parseInt(category_id),Long.parseLong(id)));
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }

        closeDB(database,dbHelper);

        return expenses;
    }

    public void deleteExpenseById(long id){
        SetupDB dbHelper = new SetupDB(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.deleteExpenseByID(database,id);
        closeDB(database,dbHelper);
    }

    public void updateExpenseByID(long id,Expense expense){
        SetupDB dbHelper = new SetupDB(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.updateExpenseByID(database,expense,id);
        closeDB(database,dbHelper);
    }

    private void closeDB(SQLiteDatabase db, SetupDB dbHelper){
        if(db!=null){
            db.close();
        }

        if(dbHelper!=null){
            dbHelper.close();
        }
    }

}
