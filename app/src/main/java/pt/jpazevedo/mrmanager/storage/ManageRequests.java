package pt.jpazevedo.mrmanager.storage;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pt.jpazevedo.mrmanager.entities.Expense;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class ManageRequests {

    static String EXPENSE_COLUMN_NAME = "name";
    static String EXPENSE_COLUMN_DESCRIPTION = "description";
    static String EXPENSE_COLUMN_VALUE = "value";
    static String EXPENSE_COLUMN_DATE = "date";
    static String EXPENSE_COLUMN_CATEGORYID = "category_id";

    private  Context context;

    public ManageRequests(Context context){
        this.context = context;
    }

    public List<Expense> getAllExpenses(){
        SetupDB db = new SetupDB(context);
        Cursor cursor = db.getAllExpenses();
        String name;
        String value;
        String description;
        String date;
        String category_id;
        List<Expense> expenses = new ArrayList<>();


        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                name = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_NAME));
                date = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_DATE));
                value = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_VALUE));
                description = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_DESCRIPTION));
                category_id = cursor.getString(cursor.getColumnIndex(EXPENSE_COLUMN_CATEGORYID));
                expenses.add(new Expense(name,description,Float.valueOf(value),Integer.parseInt(date),Integer.parseInt(category_id)));
            }
        }

        return expenses;
    }

    public void insertExpense(String name, int date, int category_id, String description, float value){
        SetupDB db = new SetupDB(context);
        db.insertExpense(name,date,category_id,description,value);
    }

}
