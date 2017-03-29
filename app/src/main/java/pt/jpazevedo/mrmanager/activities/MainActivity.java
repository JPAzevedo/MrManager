package pt.jpazevedo.mrmanager.activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.CustomViewPagerAdapter;
import pt.jpazevedo.mrmanager.fragments.DashboardFragment;
import pt.jpazevedo.mrmanager.fragments.FriendsFragment;
import pt.jpazevedo.mrmanager.fragments.HistoryFragment;
import pt.jpazevedo.mrmanager.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity{

    ViewPager vpMain;

    Toolbar tbMain;

    TabLayout tlTtabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_dark,null));
        }


        vpMain = (ViewPager) findViewById(R.id.vpMain);
        tbMain = (Toolbar) findViewById(R.id.tbMain);
        tlTtabs = (TabLayout) findViewById(R.id.tlTtabs);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent addBillIntent = new Intent(MainActivity.this,AddBillActivity.class);
                addBillIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(addBillIntent);
            }
        });

        setSupportActionBar(tbMain);
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPagerAdapter();

    }

    private void setupViewPagerAdapter(){
        DashboardFragment mDashboard = new DashboardFragment();
        HistoryFragment mHistory = new HistoryFragment();
        FriendsFragment mFriends = new FriendsFragment();
        ProfileFragment mProfile = new ProfileFragment();

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

}
