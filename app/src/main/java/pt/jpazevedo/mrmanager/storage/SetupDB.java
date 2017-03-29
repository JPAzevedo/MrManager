package pt.jpazevedo.mrmanager.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class SetupDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MrManager.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";

    public SetupDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table category " +
                        "(id integer primary key, name text)"
        );
        sqLiteDatabase.execSQL(
                "create table friend " +
                        "(id integer primary key, name text)"
        );
        sqLiteDatabase.execSQL(
                "create table expense " +
                        "(expense_id integer primary key, name text,description text,value real, date integer, category_id integer,friend_id integer, FOREIGN KEY(category_id) REFERENCES category(id),FOREIGN KEY(friend_id) REFERENCES friend(id))"
        );

        insertAllCategories(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS category");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS friend");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS expense");
        onCreate(sqLiteDatabase);
    }

    private boolean insertCategory (SQLiteDatabase db,String name) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert("category", null, contentValues);
        return true;
    }

    private void insertAllCategories(SQLiteDatabase sqLiteDatabase){
        String categories[] = new String[]{"Lunch","Dinner","Health","Groceries","Fitness","Electronics","Clothes","Trasports","Fuel","Vacancies","House's Expenses","House's Rent","Travel","Other"};
        ;
        for(String s: categories){
            insertCategory(sqLiteDatabase,s);
        }
    }

    public boolean insertExpense(String name, int date, int category_id, String description, float value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("date", date);
        contentValues.put("description", description);
        contentValues.put("value", value);
        contentValues.put("category_id", category_id);
        contentValues.put("friend_id", 0);
        db.insert("expense", null, contentValues);
        return true;
    }

    public Cursor getAllExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from expense", null );
        return res;
    }


}
