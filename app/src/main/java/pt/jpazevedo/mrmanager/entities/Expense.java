package pt.jpazevedo.mrmanager.entities;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class Expense {
    public String name;
    public String description;
    public float value;
    public int date;
    public int category_id;

    public Expense(String name, String description, float value, int date, int category_id) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.date = date;
        this.category_id = category_id;
    }
}
