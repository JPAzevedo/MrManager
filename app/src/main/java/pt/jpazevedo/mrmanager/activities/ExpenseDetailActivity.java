package pt.jpazevedo.mrmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import pt.jpazevedo.mrmanager.C;
import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.entities.AddEditExpense;
import pt.jpazevedo.mrmanager.entities.DeleteUpdateExpense;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.entities.ExpensesCategories;
import pt.jpazevedo.mrmanager.storage.ManageLocalRequests;
import pt.jpazevedo.mrmanager.util.DateUtilities;
import pt.jpazevedo.mrmanager.util.ExpensesManagement;

/**
 * Created by joaop on 09/04/2017.
 */

public class ExpenseDetailActivity extends AppCompatActivity {

    private Toolbar tbExpenseDetail;

    private ImageView ivIconExpenseDetail;

    private TextView tvExpenseDetailName;

    private TextView tvExpenseDetailDescription;

    private TextView tvExpenseDetailDate;

    private TextView tvExpenseDetailValue;

    private TextView tvExpenseDetailCategory;

    private Expense exp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensedetail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue_sky,null));
        }

        tbExpenseDetail = (Toolbar) findViewById(R.id.tbExpenseDetail);
        ivIconExpenseDetail = (ImageView) findViewById(R.id.ivIconExpenseDetail);
        tvExpenseDetailName = (TextView) findViewById(R.id.tvExpenseDetailName);
        tvExpenseDetailDescription = (TextView) findViewById(R.id.tvExpenseDetailDescription);
        tvExpenseDetailDate = (TextView) findViewById(R.id.tvExpenseDetailDate);
        tvExpenseDetailValue = (TextView) findViewById(R.id.tvExpenseDetailValue);
        tvExpenseDetailCategory = (TextView) findViewById(R.id.tvExpenseDetailCategory);

        setSupportActionBar(tbExpenseDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.ExpenseDetailActivity_Title));

        Bundle mB = getIntent().getExtras();
        exp = mB.getParcelable(C.EXPENSE_PARCELABLE_EXTRANAME);

        if(exp!=null){
            setUpUI(exp);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // do what you want to be done on home button click event
                DeleteUpdateExpense mDeleteUpdateExpense = new DeleteUpdateExpense(exp,false);
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra(C.EXPENSE_PARCELABLE_EXTRANAME, mDeleteUpdateExpense));
                finish();
                return true;
            case R.id.action_edit:
                AddEditExpense mAddEditExpense = new AddEditExpense(null,exp);
                Intent addBillIntent = new Intent(ExpenseDetailActivity.this,AddEditExpenseActivity.class);
                Bundle b = new Bundle();
                b.putParcelable(C.EXPENSE_PARCELABLE_EXTRANAME,mAddEditExpense);
                addBillIntent.putExtras(b);
                addBillIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(addBillIntent,C.UPDATE_DATA_RESULT);
                return true;
            case R.id.action_delete:
                DeleteData mD = new DeleteData();
                mD.execute(exp.id);
                DeleteUpdateExpense mDeleteUpdateExpense2 = new DeleteUpdateExpense(exp,true);
                setResult(Activity.RESULT_OK,new Intent().putExtra(C.EXPENSE_PARCELABLE_EXTRANAME,mDeleteUpdateExpense2));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**********************************************************************
    *                       Set up UI values
    ***********************************************************************/
    private void setUpUI(Expense expense){
        DateUtilities dateUtilities = new DateUtilities();
        ExpensesManagement expensesManagement = new ExpensesManagement();
        tvExpenseDetailName.setText(expense.name);
        tvExpenseDetailDescription.setText(expense.description);
        tvExpenseDetailDate.setText(dateUtilities.millisecondToString(expense.date));
        tvExpenseDetailValue.setText(""+expense.value+" "+getString(R.string.Expense_Default_Currency));
        ivIconExpenseDetail.setBackgroundResource(expensesManagement.getExpenseDrawable(expense.category_id));
        tvExpenseDetailCategory.setText(ExpensesCategories.getCategoryString(this,ExpensesCategories.getExpenseCategoryById(expense.category_id)));
    }

    /***********************************************************************
                                Delete Expense
    ************************************************************************/
    class DeleteData extends AsyncTask<Long,Void,Void>{
        @Override
        protected Void doInBackground(Long... integers) {
            long id = integers[0];
            ManageLocalRequests manageRequests = new ManageLocalRequests(ExpenseDetailActivity.this);
            manageRequests.deleteExpenseById(id);
            return null;
        }
    }

    /***********************************************************************
     *                      Update Expense
     *************************************************************************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == C.UPDATE_DATA_RESULT && resultCode == Activity.RESULT_OK){
            Expense expense = data.getParcelableExtra(C.EXPENSE_PARCELABLE_EXTRANAME);
            exp = expense;
            setUpUI(exp);
            Snackbar.make(tbExpenseDetail, getString(R.string.MainActivity_UpdatedSuccessfully), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}
