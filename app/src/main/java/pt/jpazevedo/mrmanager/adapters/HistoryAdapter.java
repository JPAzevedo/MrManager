package pt.jpazevedo.mrmanager.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.jpazevedo.mrmanager.C;
import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.activities.ExpenseDetailActivity;
import pt.jpazevedo.mrmanager.entities.Expense;
import pt.jpazevedo.mrmanager.util.DateUtilities;
import pt.jpazevedo.mrmanager.util.ExpandAnimation;
import pt.jpazevedo.mrmanager.util.ExpensesManagement;

/**
 * Created by joaopedroazevedo11 on 23/02/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MainAdapterHolder> implements View.OnClickListener{

    private Activity context;
    private List<String> arr;
    private ArrayList<String> data_arr;
    private Map<String,List<Expense>> details;
    private HashMap<String,List<Expense>> detailsHash;

    public HistoryAdapter(Activity context, List<String> arr, Map<String,List<Expense>> details){
        this.context = context;
        this.arr = arr;
        this.details = details;
        addValues();
    }

    public void notifyDataHasChanged(List<String> arr,Map<String,List<Expense>> details){
        this.arr = arr;
        this.details = details;
        addValues();
        notifyDataSetChanged();
    }

    private void addValues(){
        data_arr = new ArrayList<>();
        detailsHash = new HashMap<>();

        if(arr !=null)
            data_arr.addAll(arr);

        if(details!=null)
            detailsHash.putAll(details);
    }

    @Override
    public MainAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater my_inflater = LayoutInflater.from(context);
        View my_view = my_inflater.inflate(R.layout.adapter_history,parent,false);
        MainAdapterHolder holder = new MainAdapterHolder(my_view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainAdapterHolder holder, int position) {
        List<Expense> expList = detailsHash.get(arr.get(position));
        holder.tvHistoryMonth.setText(data_arr.get(position));
        holder.tvTotalBillsNumber.setText(""+expList.size());
        holder.tvTotalValue.setText(getTotalValue(expList)+context.getString(R.string.Expense_Default_Currency));
        holder.llWrapper.removeAllViews();

        ExpensesManagement expensesManagement = new ExpensesManagement();
        final int arraySize = expList.size();
        DateUtilities dateUtilities = new DateUtilities();

        for(final Expense exp: expList){

            View child = LayoutInflater.from(context).inflate(R.layout.adapter_listdetail, null);
            TextView tvHistoryDetail = (TextView) child.findViewById(R.id.tvHistoryDetail);
            TextView tvHistoryDate = (TextView) child.findViewById(R.id.tvHistoryDate);
            TextView tvHistoryDetailValue = (TextView) child.findViewById(R.id.tvHistoryDetailValue);
            ImageView ivListDetailIcon = (ImageView) child.findViewById(R.id.ivListDetailIcon);
            tvHistoryDetail.setText(exp.name);
            tvHistoryDate.setText(dateUtilities.millisecondToString(exp.date));
            tvHistoryDetailValue.setText(""+exp.value+" "+context.getString(R.string.Expense_Default_Currency));
            ivListDetailIcon.setBackgroundResource(expensesManagement.getExpenseSmallDrawable(exp.category_id));
            holder.llWrapper.addView(child);

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startDetailActivity(exp);
                }
            });
        }

        final LinearLayout llMaster = holder.llWrapper;
        holder.rlHeaderMonthHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(llMaster.getVisibility() == View.VISIBLE){
                    ExpandAnimation.collapseWithouLag(llMaster);
                }
                else{
                    final int heightSize = (int) (context.getResources().getDimension(R.dimen.history_list_height)*arraySize);
                    ExpandAnimation.expandWithouLag(llMaster,heightSize);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        if(data_arr!=null)
            return data_arr.size();

        return 0;
    }

    @Override
    public void onClick(View view) {

    }

    class MainAdapterHolder extends RecyclerView.ViewHolder{

        private TextView tvHistoryMonth;

        private TextView tvTotalBillsNumber;

        private LinearLayout llWrapper;

        private RelativeLayout rlHeaderMonthHistory;

        private TextView tvTotalValue;


        public MainAdapterHolder(View itemView) {
            super(itemView);

            tvHistoryMonth = (TextView) itemView.findViewById(R.id.tvHistoryMonth);
            tvTotalBillsNumber = (TextView) itemView.findViewById(R.id.tvTotalBillsNumber);
            tvTotalValue = (TextView) itemView.findViewById(R.id.tvTotalValue);
            llWrapper = (LinearLayout) itemView.findViewById(R.id.llWrapper);
            rlHeaderMonthHistory = (RelativeLayout) itemView.findViewById(R.id.rlHeaderMonthHistory);


        }
    }

    private String getTotalValue(List<Expense> expList){
        float total = 0;

        for(Expense e: expList){
            total = total + e.value;
        }

        return Float.toString(total);
    }


    /*************************************************************************************
     *                          Start detail Activity
     *************************************************************************************/

    private void startDetailActivity(Expense exp){
        Bundle b = new Bundle();
        b.putParcelable(C.EXPENSE_PARCELABLE_EXTRANAME,exp);
        Intent mIntent = new Intent(context, ExpenseDetailActivity.class);
        mIntent.putExtras(b);
        context.startActivityForResult(mIntent,C.DELETE_UPDATE_DATA_RESULT);

    }

}
