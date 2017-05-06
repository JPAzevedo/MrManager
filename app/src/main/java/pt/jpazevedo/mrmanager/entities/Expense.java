package pt.jpazevedo.mrmanager.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class Expense implements Parcelable{

    public String name;
    public String description;
    public float value;
    public long date;
    public int category_id;
    public long id;

    public Expense(String name, String description, float value, long date, int category_id, long id) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.date = date;
        this.category_id = category_id;
        this.id = id;
    }

    private Expense(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.value = in.readFloat();
        this.category_id = in.readInt();
        this.date = in.readLong();
        this.id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeFloat(value);
        parcel.writeInt(category_id);
        parcel.writeLong(date);
        parcel.writeLong(id);
    }

    public static final Parcelable.Creator<Expense> CREATOR = new Parcelable.Creator<Expense>(){

        @Override
        public Expense createFromParcel(Parcel parcel) {
            return new Expense(parcel);
        }

        @Override
        public Expense[] newArray(int i) {
            return new Expense[i];
        }
    };
}
