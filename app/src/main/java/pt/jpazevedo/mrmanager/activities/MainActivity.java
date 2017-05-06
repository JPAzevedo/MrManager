package pt.jpazevedo.mrmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import pt.jpazevedo.mrmanager.C;
import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.CustomViewPagerAdapter;
import pt.jpazevedo.mrmanager.entities.AddEditExpense;
import pt.jpazevedo.mrmanager.entities.DeleteUpdateExpense;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.entities.ExpenseList;
import pt.jpazevedo.mrmanager.fragments.DashboardFragment;
import pt.jpazevedo.mrmanager.fragments.HistoryFragment;

public class MainActivity extends AppCompatActivity{

    ViewPager vpMain;

    Toolbar tbMain;

    TabLayout tlTtabs;

    private HistoryFragment mHistory;

    private DashboardFragment mDashboard;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue_sky,null));
        }


        vpMain = (ViewPager) findViewById(R.id.vpMain);
        tbMain = (Toolbar) findViewById(R.id.tbMain);
        tlTtabs = (TabLayout) findViewById(R.id.tlTtabs);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseList expenseList = mHistory.getExpenseList();
                AddEditExpense mAddEditExpense = new AddEditExpense(expenseList,null);
                Intent addBillIntent = new Intent(MainActivity.this,AddEditExpenseActivity.class);
                Bundle b = new Bundle();
                b.putParcelable(C.EXPENSE_PARCELABLE_EXTRANAME,mAddEditExpense);
                addBillIntent.putExtras(b);
                addBillIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(addBillIntent,C.SAVE_DATA_RESULT);
            }
        });

        tbMain.setLogo(R.drawable.icon_toolbar);
        setSupportActionBar(tbMain);
        setupViewPagerAdapter();

    }

    private void setupViewPagerAdapter(){

        mDashboard = new DashboardFragment();
        mHistory = new HistoryFragment();
        //FriendsFragment mFriends = new FriendsFragment();
        //ProfileFragment mProfile = new ProfileFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomViewPagerAdapter mAdapter = new CustomViewPagerAdapter(fragmentManager);
        mAdapter.addFragements(mDashboard,getResources().getString(R.string.tab_title_dashboard));
        mAdapter.addFragements(mHistory,getResources().getString(R.string.tab_title_history));
     // mAdapter.addFragements(mFriends,getResources().getString(R.string.tab_title_friends));
     // mAdapter.addFragements(mProfile,getResources().getString(R.string.tab_title_profile));

        tlTtabs.setupWithViewPager(vpMain);
        vpMain.setAdapter(mAdapter);
        vpMain.setOffscreenPageLimit(3);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == C.SAVE_DATA_RESULT && resultCode == Activity.RESULT_OK) {
            ExpenseList expList = data.getParcelableExtra(C.EXPENSE_PARCELABLE_EXTRANAME);
            updateFragmentData(expList,null,false);
            Snackbar.make(vpMain, getString(R.string.MainActivity_AddedSuccessfully), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
        else if (requestCode == C.DELETE_UPDATE_DATA_RESULT && resultCode == Activity.RESULT_OK){
            DeleteUpdateExpense mDataObj = data.getParcelableExtra(C.EXPENSE_PARCELABLE_EXTRANAME);
            if(mDataObj!=null) {
                updateFragmentData(null, mDataObj.getExpense(), mDataObj.isDeleted());
                if(mDataObj.isDeleted()) {
                    Snackbar.make(vpMain, getString(R.string.MainActivity_RemovedSuccessfully), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }
    }

    private void updateFragmentData(ExpenseList expList, Expense expense, boolean remove){
        if(expList!=null && expList.getExpenses() != null) {
            mHistory.updateData(expList.getExpenses());
        }
        else if(expense!=null){
            mHistory.dealWithExpensesArray(expense,remove);
        }
        mDashboard.updateUI();

    }

    public Fragment getDashboardFragment(){
        return mDashboard;
    }

}
