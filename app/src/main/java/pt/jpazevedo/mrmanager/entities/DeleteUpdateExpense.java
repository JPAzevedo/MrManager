package pt.jpazevedo.mrmanager.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joaop on 01/05/2017.
 */

public class DeleteUpdateExpense implements Parcelable{

    private Expense expense;

    private boolean deleted;

    public DeleteUpdateExpense(Expense expense, boolean deleted) {
        this.expense = expense;
        this.deleted = deleted;
    }

    protected DeleteUpdateExpense(Parcel in) {
        expense = in.readParcelable(Expense.class.getClassLoader());
        deleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(expense, flags);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DeleteUpdateExpense> CREATOR = new Creator<DeleteUpdateExpense>() {
        @Override
        public DeleteUpdateExpense createFromParcel(Parcel in) {
            return new DeleteUpdateExpense(in);
        }

        @Override
        public DeleteUpdateExpense[] newArray(int size) {
            return new DeleteUpdateExpense[size];
        }
    };

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
