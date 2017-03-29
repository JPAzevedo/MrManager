package pt.jpazevedo.mrmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.AdapterDashboard;
import pt.jpazevedo.mrmanager.util.PieChart;

/**
 * Created by joaopedroazevedo11 on 28/02/17.
 */

public class DashboardFragment extends Fragment {


    //LinearLayout pcDashboard;

    //float values[]={300,400,100,500};

    RecyclerView rvDashdetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View dashview = inflater.inflate(R.layout.fragment_dashboard,container,false);
        rvDashdetail = (RecyclerView) dashview.findViewById(R.id.rvDashdetail);

        AdapterDashboard mDashadapter = new AdapterDashboard(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        rvDashdetail.setLayoutManager(mLayoutManager);
        rvDashdetail.setItemAnimator(new DefaultItemAnimator());
        rvDashdetail.setAdapter(mDashadapter);
        //pcDashboard = (LinearLayout) dashview.findViewById(R.id.pcDashboard);
        //values=calculateData(values);
        //PieChart mPieChart = new PieChart(getActivity(),values);
        //pcDashboard.addView(mPieChart);
        // pb.setProgress(10);

        return dashview;
    }

}
