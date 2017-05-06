package pt.jpazevedo.mrmanager.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.fragments.AddEditExpenseFragment;

/**
 * Created by joaopedroazevedo11 on 05/03/17.
 */

public class AddEditExpenseActivity extends AppCompatActivity{

    private Toolbar tbAddBills;

    private AddEditExpenseFragment addExpenseFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);


        tbAddBills = (Toolbar) findViewById(R.id.tbAddBills);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue_sky,null));
        }

        setSupportActionBar(tbAddBills);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.AddExpenseActivity_Title));

        addExpenseFragment = new AddEditExpenseFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fAddExpense, addExpenseFragment).commit();

    }


    @Override
    protected void onResume() {
        super.onResume();
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
                addExpenseFragment.updateExpense();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
