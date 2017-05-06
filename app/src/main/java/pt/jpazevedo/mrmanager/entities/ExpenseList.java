package pt.jpazevedo.mrmanager.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaop on 02/04/2017.
 */

public class ExpenseList implements Parcelable {

    private List<Expense> expenses;

    public ExpenseList(List<Expense> expenses){
        this.expenses = expenses;
    }

    private ExpenseList(Parcel in) {
        expenses = new ArrayList<Expense>();
        in.readTypedList(expenses,Expense.CREATOR);
    }

    public List<Expense> getExpenses(){
        return expenses;
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
    }

    public static final Creator<ExpenseList> CREATOR = new Creator<ExpenseList>() {
        @Override
        public ExpenseList createFromParcel(Parcel in) {
            return new ExpenseList(in);
        }

        @Override
        public ExpenseList[] newArray(int size) {
            return new ExpenseList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(expenses);
    }
}
