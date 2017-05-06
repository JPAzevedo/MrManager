package pt.jpazevedo.mrmanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.jpazevedo.mrmanager.C;
import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.HistoryAdapter;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.entities.ExpenseList;
import pt.jpazevedo.mrmanager.storage.ManageLocalRequests;
import pt.jpazevedo.mrmanager.util.ExpensesManagement;

/**
 * Created by joaopedroazevedo11 on 23/02/17.
 */

public class HistoryFragment extends Fragment {

    private RecyclerView rvMain;

    private ExpenseList mList;

    private HistoryAdapter mainAdapter;

    private ProgressBar pbHistory;

    private TextView tvHistoryFrag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View main_view = inflater.inflate(R.layout.fragment_history,container,false);

        rvMain = (RecyclerView) main_view.findViewById(R.id.rvMain);
        pbHistory = (ProgressBar) main_view.findViewById(R.id.pbHistory);
        tvHistoryFrag = (TextView) main_view.findViewById(R.id.tvHistoryFrag);
        mainAdapter = new HistoryAdapter(this.getActivity(),null,null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        rvMain.setLayoutManager(mLayoutManager);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.setAdapter(mainAdapter);

        return main_view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mList = getHistoryData();

        if(mList==null){
            if(C.DEBUG)
                Log.d(C.DEBUG_DATA,"Data was not persisted");
            mList = getDBHistoryData();
        }
        else{
            dataHasChanged(mList);
            if(C.DEBUG)
                Log.d(C.DEBUG_DATA,"Data was persisted: "+mList.getExpenses().size());
        }

        updateUI();

    }

    /******************************************************************
     *                  Update UI
     *****************************************************************/

    private void updateUI(){
        if(mList.getExpenses().size()==0){
            rvMain.setVisibility(View.INVISIBLE);
            tvHistoryFrag.setVisibility(View.VISIBLE);
        }
        else{
            tvHistoryFrag.setVisibility(View.INVISIBLE);
            rvMain.setVisibility(View.VISIBLE);
        }

        pbHistory.setVisibility(View.INVISIBLE);
    }

    /******************************************************************
     *                  Get expenses data
     *****************************************************************/

    private ExpenseList getDBHistoryData(){

        GetDbData mGetDbData = new GetDbData(getActivity());
        mGetDbData.execute();

        ManageLocalRequests manageRequests = new ManageLocalRequests(getActivity());
        mList = new ExpenseList(manageRequests.getAllExpenses());

        Intent intent = getActivity().getIntent();
        intent.putExtra(C.EXPENSE_PARCELABLE_EXTRANAME,mList);
        return mList;
    }

    private ExpenseList getHistoryData(){

        if(mList!=null)
            return  mList;

        Intent intent = getActivity().getIntent();

        if(intent== null)
            return null;

        ExpenseList mList = intent.getParcelableExtra(C.EXPENSE_PARCELABLE_EXTRANAME);
        return mList;
    }

    public ExpenseList getExpenseList(){
        return getHistoryData();
    }

    /******************************************************************
     *                          Update data
     *****************************************************************/

    public void updateData(List<Expense> exps){
        mList.getExpenses().clear();
        mList.getExpenses().addAll(exps);
        dataHasChanged(mList);
    }

    public void dealWithExpensesArray(Expense exp, boolean remove){

        mList = manageArrayData(exp,mList,remove);
        for(Expense a: mList.getExpenses()){
            Log.d(C.DEBUG_DATA,"NAME: "+a.name+" id: "+a.id);
        }
        dataHasChanged(mList);
    }

    private ExpenseList manageArrayData(Expense exp, ExpenseList expList, boolean remove){
        ExpenseList aux = new ExpenseList(new ArrayList<Expense>());
        if(expList!=null && expList.getExpenses()!=null && expList.getExpenses().size() > 0){
            for(int i=0; i < expList.getExpenses().size(); i++){
                if(expList.getExpenses().get(i).id==exp.id){
                    if(!remove){
                        aux.addExpense(exp);
                    }
                }
                else{
                    aux.addExpense(expList.getExpenses().get(i));
                }
            }
        }

        for(Expense a: aux.getExpenses()){
            Log.d(C.DEBUG_DATA,"NAME: "+a.name+" id: "+a.id);
        }

        return aux;
    }


    private void dataHasChanged(ExpenseList expenses){
        if(expenses!=null) {
            ExpensesManagement expMgm = new ExpensesManagement();
            List<String> title = expMgm.getExpensesTitles(expenses);
            Map<String, List<Expense>> details = expMgm.getMonthDetail(expenses);
            mainAdapter.notifyDataHasChanged(title, details);
        }
    }

    /******************************************************************
     *          Async task to populate history list
     *****************************************************************/

    class GetDbData extends AsyncTask<Void,Void,Void>{

        Activity context;

        public GetDbData(Activity context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ManageLocalRequests manageRequests = new ManageLocalRequests(getActivity());
            mList = new ExpenseList(manageRequests.getAllExpenses());

            Intent intent = context.getIntent();
            intent.putExtra(C.EXPENSE_PARCELABLE_EXTRANAME,mList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataHasChanged(mList);
            updateUI();
        }
    }
}
