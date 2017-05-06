package pt.jpazevedo.mrmanager.entities;

import android.content.Context;

import pt.jpazevedo.mrmanager.R;

/**
 * Created by joaop on 09/04/2017.
 */

public class ExpensesCategories {


    public static enum ExpenseCategory{
        Lunch,
        Diner,
        Health,
        Groceries,
        Fitness,
        Electronics,
        Transports,
        Fuel,
        HousesExpenses,
        HouseRent,
        Vacancies,
        Travel,
        Clothes,
        Other
    }

    public static int getCategoryDrawable(ExpenseCategory category){

        switch (category){
            case Lunch:
                return R.mipmap.ic_launcher;
            case Diner:
                return R.mipmap.ic_launcher;
            case Health:
                return R.mipmap.ic_launcher;
            case Groceries:
                return R.mipmap.ic_launcher;
            case Fitness:
                return R.mipmap.ic_launcher;
            case Electronics:
                return R.mipmap.ic_launcher;
            case Transports:
                return R.mipmap.ic_launcher;
            case Fuel:
                return R.mipmap.ic_launcher;
            case HousesExpenses:
                return R.mipmap.ic_launcher;
            case HouseRent:
                return R.mipmap.ic_launcher;
            case Vacancies:
                return R.mipmap.ic_launcher;
            case Travel:
                return R.mipmap.ic_launcher;
            case Clothes:
                return R.mipmap.ic_launcher;
            case Other:
                return R.mipmap.ic_launcher;
            default:
                return R.mipmap.ic_launcher;
        }

    }

    public static int getCategoryId(ExpenseCategory category){

        switch (category){
            case Lunch:
                return 1;
            case Diner:
                return 2;
            case Health:
                return 3;
            case Groceries:
                return 4;
            case Fitness:
                return 5;
            case Electronics:
                return 6;
            case Transports:
                return 7;
            case Fuel:
                return 8;
            case HousesExpenses:
                return 9;
            case HouseRent:
                return 10;
            case Vacancies:
                return 11;
            case Travel:
                return 12;
            case Clothes:
                return 13;
            case Other:
                return 14;
            default:
                return 0;
        }

    }


    public static String getCategoryString(Context context, ExpenseCategory category){

        switch (category){
            case Lunch:
                return context.getString(R.string.ExpenseCategory_Lunch);
            case Diner:
                return context.getString(R.string.ExpenseCategory_Diner);
            case Health:
                return context.getString(R.string.ExpenseCategory_Health);
            case Groceries:
                return context.getString(R.string.ExpenseCategory_Groceries);
            case Fitness:
                return context.getString(R.string.ExpenseCategory_Fitness);
            case Electronics:
                return context.getString(R.string.ExpenseCategory_Electronics);
            case Transports:
                return context.getString(R.string.ExpenseCategory_Transports);
            case Fuel:
                return context.getString(R.string.ExpenseCategory_Fuel);
            case HousesExpenses:
                return context.getString(R.string.ExpenseCategory_HouseExpenses);
            case HouseRent:
                return context.getString(R.string.ExpenseCategory_HouseRent);
            case Vacancies:
                return context.getString(R.string.ExpenseCategory_Vacancies);
            case Travel:
                return context.getString(R.string.ExpenseCategory_Travel);
            case Clothes:
                return context.getString(R.string.ExpenseCategory_Clothes);
            case Other:
                return context.getString(R.string.ExpenseCategory_Other);
            default:
                return "";
        }

    }

    public static ExpenseCategory getExpenseCategoryById(int id){

        switch (id){
            case 1:
                return ExpenseCategory.Lunch;
            case 2:
                return ExpenseCategory.Diner;
            case 3:
                return ExpenseCategory.Health;
            case 4:
                return ExpenseCategory.Groceries;
            case 5:
                return ExpenseCategory.Fitness;
            case 6:
                return ExpenseCategory.Electronics;
            case 7:
                return ExpenseCategory.Transports;
            case 8:
                return ExpenseCategory.Fuel;
            case 9:
                return ExpenseCategory.HousesExpenses;
            case 10:
                return ExpenseCategory.HouseRent;
            case 11:
                return ExpenseCategory.Vacancies;
            case 12:
                return ExpenseCategory.Travel;
            case 13:
                return ExpenseCategory.Clothes;
            case 14:
                return ExpenseCategory.Other;
            default:
                return ExpenseCategory.Other;
        }

    }

}
