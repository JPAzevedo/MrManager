package pt.jpazevedo.mrmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.jpazevedo.mrmanager.C;
import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.adapters.AddCategoryAdapter;
import pt.jpazevedo.mrmanager.adapters.CalcAdapter;
import pt.jpazevedo.mrmanager.entities.AddEditExpense;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.entities.ExpenseList;
import pt.jpazevedo.mrmanager.entities.ExpensesCategories;
import pt.jpazevedo.mrmanager.storage.ManageLocalRequests;
import pt.jpazevedo.mrmanager.util.DateUtilities;
import pt.jpazevedo.mrmanager.util.ExpandAnimation;
import pt.jpazevedo.mrmanager.util.ExpensesManagement;

/**
 * Created by joaop on 26/04/2017.
 */

public class AddEditExpenseFragment extends Fragment {

    private EditText etDate;

    private EditText edBillName;

    private EditText edBillDescription;

    private View lValue;

    private TextView tvValue;

    private TextView tvBillValue;

    private TextView tvCategory;

    private View lCategory;

    private ScrollView svAddBill;

    private LinearLayout llAddBillScrollChild;

    private GridView gvAddBill;

    private List<String> category;

    private CalcAdapter mCalc;

    private  ExpenseList mList;

    private int categoryID = 14;

    private long expenseID = 0;

    private static boolean catOpen = false;

    private Expense editExpense;

    public AddEditExpenseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View expensedataview = inflater.inflate(R.layout.fragment_expensedata, container, false);

        catOpen = false;
        edBillName = (EditText) expensedataview.findViewById(R.id.edBillName);
        edBillDescription = (EditText) expensedataview.findViewById(R.id.edBillDescription);
        svAddBill = (ScrollView) expensedataview.findViewById(R.id.svAddBill);
        llAddBillScrollChild = (LinearLayout) expensedataview.findViewById(R.id.llAddBillScrollChild);
        etDate = (EditText) expensedataview.findViewById(R.id.etDate);

        final DateUtilities mDateUtilities = new DateUtilities();
        mDateUtilities.setupInitialDate(etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDateUtilities.handleDatePicker(etDate,getActivity());
            }
        });

        edBillDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.edBillDescription && !hasFocus) {
                    hideKeyboard(v);

                }
            }
        });

        edBillName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.edBillName && !hasFocus) {
                        hideKeyboard(v);
                }
            }
        });

        dealWithValue(expensedataview);
        dealWithCategories(expensedataview);
        manageBundleValues();

        gvAddBill.setSelection(categoryID);
        return expensedataview;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (gvAddBill != null) {
            gvAddBill.setSelection(categoryID);
        }
    }

    public void updateExpense() {
        if(editExpense==null) {
            SaveData mSaveData = new SaveData();
            mSaveData.execute();
        }
        else{
            EditData mEditData = new EditData();
            mEditData.execute();
        }
    }

    /******************************************************************
     *                  Get bundled values
     ******************************************************************/
    private void manageBundleValues(){
        Bundle mB = getActivity().getIntent().getExtras();
        AddEditExpense mAddEditExpense = mB.getParcelable(C.EXPENSE_PARCELABLE_EXTRANAME);
        if(mAddEditExpense!=null){
            if(mAddEditExpense.getExpenseList()!=null) {
                mList = mAddEditExpense.getExpenseList();
            }

            if(mAddEditExpense.getExpense()!=null){
                editExpense = mAddEditExpense.getExpense();
                setDefaultValues(editExpense);
            }

        }
    }

    /******************************************************************
     *                  Deal with values
     ******************************************************************/

    private void dealWithValue(View view) {
        lValue = (View) view.findViewById(R.id.lValue);
        tvValue = (TextView) view.findViewById(R.id.tvValue);
        tvBillValue = (TextView) view.findViewById(R.id.tvBillValue);

        mCalc = new CalcAdapter(view, tvBillValue);
        mCalc.initUI();

        tvValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edBillDescription.clearFocus();
                edBillName.clearFocus();

                if (lValue.getVisibility() == View.VISIBLE) {
                    ExpandAnimation.collapse(lValue);
                } else {
                    ExpandAnimation.expandWithouLag(lValue);

                    int dif = (llAddBillScrollChild.getBottom() - (svAddBill.getHeight() + svAddBill.getScrollY()));

                    if ((dif >= (lCategory.getHeight() * 0.6) && catOpen) || !catOpen) {

                        svAddBill.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                svAddBill.smoothScrollBy(0, lValue.getHeight());
                            }
                        }, 500);

                    }

                }
            }
        });
    }

    /******************************************************************
     *                  Deal with Categories
     ******************************************************************/
    private void dealWithCategories(View view) {
        lCategory = (View) view.findViewById(R.id.lCategory);
        tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        gvAddBill = (GridView) view.findViewById(R.id.gvAddBill);
        addCategoriesToArray();

        AddCategoryAdapter mCategoryAdapter = new AddCategoryAdapter(getActivity(), category);
        gvAddBill.setAdapter(mCategoryAdapter);

        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edBillDescription.clearFocus();
                edBillName.clearFocus();

                if (lCategory.getVisibility() == View.VISIBLE) {
                    ExpandAnimation.collapse(lCategory);
                    catOpen = false;
                } else {
                    ExpandAnimation.expandWithouLag(lCategory);

                    svAddBill.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            catOpen = true;
                            svAddBill.smoothScrollBy(0, lCategory.getHeight());
                        }
                    }, 500);

                }
            }
        });

        gvAddBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setCategoryID(i);
                view.setPressed(true);
            }
        });
    }

    private void addCategoriesToArray() {
        category = new ArrayList<String>();
        for (ExpensesCategories.ExpenseCategory cat : ExpensesCategories.ExpenseCategory.values()) {
            category.add(ExpensesCategories.getCategoryString(getActivity(), cat));
        }
    }

    /******************************************************************
     *                              Util
     ******************************************************************/

    private void setCategoryID(int pos) {
        categoryID = pos + 1;
    }

    public ExpenseList getExpenseList() {
        return mList;
    }

    private Expense getExpenseObject(long id) {
        String name = edBillName.getText().toString();
        String description = edBillDescription.getText().toString();
        String value = tvBillValue.getText().toString();
        float fvalue = getExpenseVal(value);
        String date = etDate.getText().toString();
        long actualdate = new DateUtilities().stringDateToLong(date);

        if(id>0){
            this.expenseID = id;
        }

        return new Expense(name, description, fvalue, actualdate, categoryID,expenseID);
    }

    private float getExpenseVal(String value){
        return new ExpensesManagement().getExpenseVal(value);
    }

    private void hideKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /******************************************************************
     *                  Update on edit expense
     ******************************************************************/

    private void setDefaultValues(Expense expense) {
        tvBillValue.setText("" + expense.value);
        categoryID = expense.category_id;
        edBillName.setText(expense.name);
        edBillDescription.setText(expense.description);
        DateUtilities mDateUtilities = new DateUtilities();
        etDate.setText(mDateUtilities.millisecondToString(expense.date));
    }

    /******************************************************************
     *                  Save data async task
     ******************************************************************/

    class SaveData extends AsyncTask<Void, Long, Long> {

        private boolean run = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(edBillName.getText().toString().isEmpty()){
                edBillName.setError(getString(R.string.AddExpenseActivity_NameIsMissing));
                run = false;
            }

        }

        @Override
        protected Long doInBackground(Void... voids) {
            if(run) {
                Expense expense = getExpenseObject(-1);
                ManageLocalRequests manageRequests = new ManageLocalRequests(getActivity());
                long id = manageRequests.insertExpense(expense.name, expense.date, expense.category_id, expense.description, expense.value);
                return id;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long id) {
            if(id!=null) {
                super.onPostExecute(id);
                mList.addExpense(getExpenseObject(id.longValue()));
                finishActivity(null);
            }
        }
    }

    /************************************************************************
     *                          Edit Data
     ************************************************************************/
    class EditData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ManageLocalRequests manageRequests = new ManageLocalRequests(getActivity());
            Expense exp = getExpenseObject(-1);
            manageRequests.updateExpenseByID(editExpense.id,exp);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            finishActivity(getExpenseObject(editExpense.id));
        }
    }

    /************************************************************************
     *                          Finish activity
     ************************************************************************/
    private void finishActivity(Expense exp){
        if(exp==null) {
            getActivity().setResult(Activity.RESULT_OK, new Intent()
                    .putExtra(C.EXPENSE_PARCELABLE_EXTRANAME, getExpenseList()));
        }
        else{
            getActivity().setResult(Activity.RESULT_OK, new Intent()
                    .putExtra(C.EXPENSE_PARCELABLE_EXTRANAME, exp));
        }
        getActivity().finish();
    }

}
