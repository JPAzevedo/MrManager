package pt.jpazevedo.mrmanager.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pt.jpazevedo.mrmanager.C;

/**
 * Created by joaop on 01/04/2017.
 */

public class DateUtilities {

    public String millisecondToString(long ms){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.format(new Date(ms)).toString();
        } catch (Exception e) {
           e.getStackTrace();
        }

        return "";
    }

    public long stringDateToLong(String stringdate){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = null;
        long actualdate = 0;

        try {
            parsedDate = dateFormat.parse(stringdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(parsedDate!=null){
            actualdate = parsedDate.getTime();
        }
        return actualdate;
    }

    public int getCurrenMonth(long date){
        Date dateObj = new Date(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dateObj);
        return c.get(Calendar.MONTH);
    }

    public int getCurrenYear(long date){
        Date dateObj = new Date(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dateObj);
        return c.get(Calendar.YEAR);
    }

    public String getCurrentMonth(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        return getMonth(month);
    }

    public String getCurrentMonth(int month){
        return getMonth(month);
    }

    private String getMonth(int month){
        switch ((month+1)){
            case 1:
               return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "<Month>";
        }
    }

    public void setupInitialDate(EditText et){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        setDate(et,mDay,mMonth,mYear);
    }

    public void setDate(EditText et, int mDay, int mMonth, int mYear){

        String month = Integer.toString(mMonth+1);
        if((mMonth+1) < 10){
            month = "0".concat(month);
        }

        String day = Integer.toString(mDay);
        if((mDay) < 10){
            day = "0".concat(day);
        }

        et.setText(day + "-" + month + "-" + mYear);
    }

    public void handleDatePicker(final EditText et, Context context) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // et.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        setDate(et, dayOfMonth, monthOfYear, year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
