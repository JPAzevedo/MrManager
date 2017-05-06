package pt.jpazevedo.mrmanager.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.entities.ExpenseList;
import pt.jpazevedo.mrmanager.storage.ManageLocalRequests;

/**
 * Created by joaop on 02/04/2017.
 */

public class ExpensesManagement {

    public List<String> getExpensesTitles(ExpenseList expense){
        List<String> title = new ArrayList<>();
        List<Expense> expenses = expense.getExpenses();

        for(Expense e: expenses){
            String expenseTitle =  getExpenseTitle(e);
            if(!title.contains(expenseTitle)){
                title.add(expenseTitle);
            }
        }
        return title;
    }

    public Map<String,List<Expense>> getMonthDetail(ExpenseList expenses){
        Map<String,List<Expense>> details = new HashMap<>();

        for(Expense e: expenses.getExpenses()){
            String expenseTitle = getExpenseTitle(e);

            if(details!=null && details.containsKey(expenseTitle)){
                List<Expense> exp = details.get(expenseTitle);
                exp.add(e);
                details.remove(expenseTitle);
                details.put(expenseTitle,exp);
            }
            else{
                List<Expense> eList = new ArrayList();
                eList.add(e);
                details.put(expenseTitle,eList);
            }

        }

        return details;

    }

    private String getExpenseTitle(Expense e){
        DateUtilities dateUtilities = new DateUtilities();
        int y = dateUtilities.getCurrenYear(e.date);
        int m = dateUtilities.getCurrenMonth(e.date);
        String monthString = dateUtilities.getCurrentMonth(m);
        return monthString+" "+String.valueOf(y);
    }

    public List<Expense> getMonthExpenses(Context context){
        DateUtilities dateUtilities = new DateUtilities();
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        long ms = dateUtilities.stringDateToLong("01-"+(month+1)+"-"+year);
        ManageLocalRequests manageRequests = new ManageLocalRequests(context);
        return manageRequests.getMonthExpenses(ms);
    }

    public float getTotalMonthValue(List<Expense> expenses){
        float total = 0;
        for(Expense exp: expenses){
            total = total + exp.value;
        }
        return  total;
    }


    public Map<Integer,Float> getCategoriesTotal(List<Expense> expenses){
        Map<Integer,Float> total = new HashMap<>();
        float aux = 0;

        for(Expense exp: expenses){
            aux = 0;
            if(total.containsKey(exp.category_id)){
                aux = total.get(exp.category_id);
            }

            total.put(exp.category_id,(aux+exp.value));
        }

        return total;
    }

    public int getExpenseDrawable(int category){
        switch (category){
            case 1:
                return R.drawable.icon_food;
            case 2:
                return R.drawable.icon_food;
            case 3:
                return R.drawable.icon_health;
            case 4:
                return R.drawable.icon_groceries;
            case 5:
                return R.drawable.icon_fitness;
            case 6:
                return R.drawable.icon_electronics;
            case 7:
                return R.drawable.icon_transport;
            case 8:
                return R.drawable.icon_fuel;
            case 9:
                return R.drawable.icon_houseexpenses;
            case 10:
                return R.drawable.icon_rent;
            case 11:
                return R.drawable.icon_vacancies;
            case 12:
                return R.drawable.icon_travel;
            case 13:
                return R.drawable.icon_clothes;
            case 14:
                return R.drawable.icon_other;
            default:
                return R.drawable.icon_total;
        }
    }

    public int getExpenseSmallDrawable(int category){
        switch (category){
            case 1:
                return R.drawable.icon_food_small;
            case 2:
                return R.drawable.icon_food_small;
            case 3:
                return R.drawable.icon_health_small;
            case 4:
                return R.drawable.icon_groceries_small;
            case 5:
                return R.drawable.icon_fitness_small;
            case 6:
                return R.drawable.icon_electronics_small;
            case 7:
                return R.drawable.icon_transport_small;
            case 8:
                return R.drawable.icon_fuel_small;
            case 9:
                return R.drawable.icon_houseexpenses_small;
            case 10:
                return R.drawable.icon_rent_small;
            case 11:
                return R.drawable.icon_vacancies_small;
            case 12:
                return R.drawable.icon_travel_small;
            case 13:
                return R.drawable.icon_clothes_small;
            case 14:
                return R.drawable.icon_other_small;
            default:
                return R.drawable.icon_other_small;
        }
    }

    public float getExpenseVal(String value) {
        float fvalue = 0;
        String num = "";

        if (value.length() > 2) {
            for (int i = 0; i < (value.length() - 2); i++) {
                num = num.concat(value.substring(i, i + 1));
            }
        }

        if (num.length() > 0) {
            fvalue = Float.parseFloat(num);
        }

        return fvalue;

    }


}
