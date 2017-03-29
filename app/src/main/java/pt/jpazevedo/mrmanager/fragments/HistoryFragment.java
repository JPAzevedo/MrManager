package pt.jpazevedo.mrmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.MainAdapter;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.storage.ManageRequests;

/**
 * Created by joaopedroazevedo11 on 23/02/17.
 */

public class HistoryFragment extends Fragment {

    private RecyclerView rvMain;

    private List<String> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View main_view = inflater.inflate(R.layout.fragment_history,container,false);
        data = new ArrayList<>();
        data.add("Teste");
        data.add("Teste2");
        data.add("Teste3");
        data.add("Teste4");

        rvMain = (RecyclerView) main_view.findViewById(R.id.rvMain);
        MainAdapter mainAdapter = new MainAdapter(this.getActivity(),data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        rvMain.setLayoutManager(mLayoutManager);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.setAdapter(mainAdapter);

        return main_view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ManageRequests manageRequests = new ManageRequests(getActivity());
       // manageRequests.getAllExpenses();

        for(Expense s :manageRequests.getAllExpenses()){
            Log.d("EXPENSES","EXPENSES name: "+s.name);
            Log.d("EXPENSES","EXPENSES description: "+s.description);
        }



    }
}
