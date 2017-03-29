package pt.jpazevedo.mrmanager.activities;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.AddCategoryAdapter;
import pt.jpazevedo.mrmanager.adapters.CalcAdapter;
import pt.jpazevedo.mrmanager.storage.ManageRequests;
import pt.jpazevedo.mrmanager.util.ExpandAnimation;

/**
 * Created by joaopedroazevedo11 on 05/03/17.
 */

public class AddBillActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar tbAddBills;

    private EditText etDate;

    private View lValue;

    private TextView tvValue;

    private TextView tvBillValue;

    private TextView tvCategory;

    private View lCategory;

    private ScrollView svAddBill;

    private GridView gvAddBill;

    private List<String> category;

    private CalcAdapter mCalc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_dark,null));
        }

        svAddBill = (ScrollView) findViewById(R.id.svAddBill);
        tbAddBills = (Toolbar) findViewById(R.id.tbAddBills);
        setSupportActionBar(tbAddBills);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Bill");

        etDate = (EditText) findViewById(R.id.etDate);
        setupInitialDate(etDate);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDatePicker(etDate);
            }
        });


        lValue = (View) findViewById(R.id.lValue);
        tvValue = (TextView) findViewById(R.id.tvValue);
        tvBillValue = (TextView) findViewById(R.id.tvBillValue);

        tvValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lValue.getVisibility() == View.VISIBLE){
                    ExpandAnimation.collapse(lValue);
                }
                else{
                    ExpandAnimation.expand(lValue);

                    svAddBill.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svAddBill.smoothScrollBy(0, lValue.getHeight());
                        }
                    }, 500);

                }
            }
        });

        lCategory = (View) findViewById(R.id.lCategory);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        gvAddBill = (GridView) findViewById(R.id.gvAddBill);
        category = new ArrayList<String>();
        category.add("Lunch");
        category.add("Dinner");
        category.add("Health");
        category.add("Groceries");
        category.add("Fitness");
        category.add("Electronics");
        category.add("Transports");
        category.add("Fuel");
        category.add("House Expenses");
        category.add("House Rent");
        category.add("Vacancies");
        category.add("Travel");
        category.add("Clothes");
        category.add("Other");

        AddCategoryAdapter mCategoryAdapter = new AddCategoryAdapter(this,category,null);
        gvAddBill.setAdapter(mCategoryAdapter);

        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(lCategory.getVisibility() == View.VISIBLE){
                    ExpandAnimation.collapse(lCategory);
                 }
                 else{
                     ExpandAnimation.expand(lCategory);

                     svAddBill.postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             svAddBill.smoothScrollBy(0, lCategory.getHeight());
                         }
                     }, 500);

                 }
            }
        });

        mCalc = new CalcAdapter(this,tvBillValue);
        mCalc.initUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // do what you want to be done on home button click event
                finish();
                return true;
            case R.id.action_save:
                saveBill();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void handleDatePicker(final EditText et){

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                           // et.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            setDate(et,dayOfMonth,monthOfYear,year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


    }

   private void setupInitialDate(EditText et){
       // Get Current Date
       final Calendar c = Calendar.getInstance();
       int mYear = c.get(Calendar.YEAR);
       int mMonth = c.get(Calendar.MONTH);
       int mDay = c.get(Calendar.DAY_OF_MONTH);

       setDate(et,mDay,mMonth,mYear);
   }

    private void setDate(EditText et, int mDay, int mMonth, int mYear){

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


    private void saveBill(){
        ManageRequests manageRequests = new ManageRequests(this);
        manageRequests.insertExpense("Teste",324234234,1,"xiripiti",90);

        String value = tvValue.getText().toString();
        String date = etDate.getText().toString();

        //if()

    }


    @Override
    public void onClick(View view) {
        mCalc.onClick(view);
    }
}
