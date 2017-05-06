package pt.jpazevedo.mrmanager.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joaop on 01/05/2017.
 */

public class AddEditExpense implements Parcelable{

    private  ExpenseList expenseList;

    private Expense expense;

    public AddEditExpense(ExpenseList expenseList, Expense expense) {
        this.expenseList = expenseList;
        this.expense = expense;
    }

    protected AddEditExpense(Parcel in) {
        expenseList = in.readParcelable(ExpenseList.class.getClassLoader());
        expense = in.readParcelable(Expense.class.getClassLoader());
    }

    public static final Creator<AddEditExpense> CREATOR = new Creator<AddEditExpense>() {
        @Override
        public AddEditExpense createFromParcel(Parcel in) {
            return new AddEditExpense(in);
        }

        @Override
        public AddEditExpense[] newArray(int size) {
            return new AddEditExpense[size];
        }
    };

    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(expenseList, i);
        parcel.writeParcelable(expense, i);
    }
}
