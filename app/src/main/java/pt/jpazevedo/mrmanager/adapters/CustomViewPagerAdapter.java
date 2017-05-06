package pt.jpazevedo.mrmanager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaopedroazevedo11 on 26/02/17.
 */

public class CustomViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;
    private List<String> titles;

    public CustomViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragements(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);

    }
}
