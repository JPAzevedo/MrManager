package pt.jpazevedo.mrmanager.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pt.jpazevedo.mrmanager.entities.Expense;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class SetupDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MrManager.db";
    public static final String TABLE_NAME = "expense";
    private static final String EXPENSE_ROW_ID = "expense_id";

    public SetupDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table expense " +
                        "(expense_id integer primary key AUTOINCREMENT NOT NULL, name text,description text,value real, date integer, category_id integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS expense");
        onCreate(sqLiteDatabase);
    }

    public long insertExpense(SQLiteDatabase db, String name, long date, int category_id, String description, float value){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("date", date);
        contentValues.put("description", description);
        contentValues.put("value", value);
        contentValues.put("category_id", category_id);
        long id = db.insertOrThrow("expense", null, contentValues);
        return id;
    }

    public Cursor getAllExpenses(SQLiteDatabase db) {
        Cursor res =  db.rawQuery( "select * from expense order by date desc", null );
        return res;
    }

    public Cursor getMonthExpenses(long ms,SQLiteDatabase db) {
        Cursor res =  db.rawQuery( "select * from expense where date >= "+ ms +" order by date desc", null );
        return res;
    }

    public boolean deleteExpenseByID(SQLiteDatabase db, long id){
        return db.delete(TABLE_NAME, EXPENSE_ROW_ID +  "=" + id, null) > 0;
    }

    public void updateExpenseByID(SQLiteDatabase db, Expense expense, long id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", expense.name);
        contentValues.put("date", expense.date);
        contentValues.put("description", expense.description);
        contentValues.put("value", expense.value);
        contentValues.put("category_id", expense.category_id);
        db.update(TABLE_NAME,contentValues,EXPENSE_ROW_ID + "=" +id,null);
    }

}
