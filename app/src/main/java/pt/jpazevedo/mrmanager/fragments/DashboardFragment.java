package pt.jpazevedo.mrmanager.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.DashboardAdapter;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.entities.ExpensesCategories;
import pt.jpazevedo.mrmanager.util.DateUtilities;
import pt.jpazevedo.mrmanager.util.ExpensesManagement;

/**
 * Created by joaopedroazevedo11 on 28/02/17.
 */

public class DashboardFragment extends Fragment {

    private RecyclerView rvDashdetail;

    private ProgressBar pbDashboard;

    private List<Integer> categoryid;

    private Map<Integer,Float> totalByCategory;

    float totalValue;

    List<Expense> expenses;

    DashboardAdapter mDashadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View dashview = inflater.inflate(R.layout.fragment_dashboard,container,false);
        rvDashdetail = (RecyclerView) dashview.findViewById(R.id.rvDashdetail);
        pbDashboard = (ProgressBar) dashview.findViewById(R.id.pbDashboard);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvDashdetail.setLayoutManager(mLayoutManager);
        rvDashdetail.setItemAnimator(new DefaultItemAnimator());

        UpdateData mUpdateData = new UpdateData(this.getActivity());
        mUpdateData.execute();

        return dashview;
    }

    private List<Integer> getCategories(){
        setUpCategoriesArray();
        return  categoryid;
    }

    private void setUpCategoriesArray(){
        DateUtilities dateUtilities = new DateUtilities();
        categoryid = new ArrayList<Integer>();
        for(ExpensesCategories.ExpenseCategory cat: ExpensesCategories.ExpenseCategory.values()){
            categoryid.add(ExpensesCategories.getCategoryId(cat));
        }
    }

    /*******************************************************************
     *                              Set up data
     *******************************************************************/

    public void updateUI(){
        UpdateData mUpdateData = new UpdateData(this.getActivity());
        mUpdateData.execute();
    }

    /*******************************************************************
     #                  Async task to populate dashboard
     *******************************************************************/
    class UpdateData extends AsyncTask<Void,Void,Void>{

        Context context;

        public UpdateData(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExpensesManagement expensesManagement = new ExpensesManagement();
            expenses =  expensesManagement.getMonthExpenses(getActivity());
            totalByCategory = expensesManagement.getCategoriesTotal(expenses);
            totalValue = expensesManagement.getTotalMonthValue(expenses);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mDashadapter==null) {
                mDashadapter = new DashboardAdapter(getActivity(), getCategories(), totalByCategory, totalValue);
                rvDashdetail.setAdapter(mDashadapter);
            }
            else{
                mDashadapter.updateData(getCategories(),totalByCategory,totalValue);
            }
            pbDashboard.setVisibility(View.INVISIBLE);
            rvDashdetail.setVisibility(View.VISIBLE);
        }
    }

}
