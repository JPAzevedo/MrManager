package pt.jpazevedo.mrmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.entities.ExpensesCategories;
import pt.jpazevedo.mrmanager.util.DateUtilities;
import pt.jpazevedo.mrmanager.util.ExpensesManagement;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashbAdapterViewHolder> {

    private Context context;

    private List<Integer> cat;

    private Map<Integer,Float> totalByCategory;

    private float total;

    public DashboardAdapter(Context context, List<Integer> cat, Map<Integer,Float> totalByCategory,float total) {
        this.context = context;
        this.cat = cat;
        this.total = total;
        this.totalByCategory = totalByCategory;

    }

    public void updateData(List<Integer> cat, Map<Integer,Float> totalByCategory,float total){
        this.cat = cat;
        this.total = total;
        this.totalByCategory = totalByCategory;
        notifyDataSetChanged();
    }

    @Override
    public DashbAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.adapter_dashboard, parent, false);
        DashbAdapterViewHolder mHolder = new DashbAdapterViewHolder(mView);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(DashbAdapterViewHolder holder, int position) {
        int pos = position;
        String catLeft = "";
        String catRight = "";
        String totalLeft = "";
        String totalRight = "";

        ExpensesManagement expensesManagement = new ExpensesManagement();
        holder.rlDashboardItemColor.setBackgroundResource(R.color.amber_200_transp);
        int size = (cat.size()+1)/2;

        if( size == position){
            if(size%2 != 0){
                holder.rlDashboardItemColorRight.setVisibility(View.INVISIBLE);
            }
        }
        else{
            holder.rlDashboardItemColorRight.setVisibility(View.VISIBLE);
        }

        if (pos == 0) {
            DateUtilities dateUtilities = new DateUtilities();
            catLeft = ""+ dateUtilities.getCurrentMonth();
            totalLeft = ""+total+context.getString(R.string.Expense_Default_DigitLeft);
            holder.rlDashboardItemColor.setBackgroundResource(R.color.amber_200);
            holder.ivDashboardIconLeft.setBackgroundResource(expensesManagement.getExpenseDrawable(-1));
        } else {
            pos = pos*2-1;
            if(pos < cat.size()) {
                int id = cat.get(pos);
                catLeft = ExpensesCategories.getCategoryString(context, ExpensesCategories.getExpenseCategoryById(id));

                if(totalByCategory.get((cat.get(pos)))!=null){
                    totalLeft = ""+totalByCategory.get(cat.get(pos))+context.getString(R.string.Expense_Default_DigitLeft);
                }
                else{
                    totalLeft = context.getString(R.string.Expense_Default_Value);
                }


            }

            holder.ivDashboardIconLeft.setBackgroundResource(expensesManagement.getExpenseDrawable(cat.get(pos)));
        }

        pos = position*2;
        if(pos < cat.size()){
            int id = cat.get(pos);
            catRight = ExpensesCategories.getCategoryString(context, ExpensesCategories.getExpenseCategoryById(id));

            if(totalByCategory.get((cat.get(pos)))!=null){
                totalRight = ""+totalByCategory.get(cat.get(pos))+context.getString(R.string.Expense_Default_DigitLeft);
            }
            else{
                totalRight = context.getString(R.string.Expense_Default_Value);
            }

            holder.ivDashboardIconRight.setBackgroundResource(expensesManagement.getExpenseDrawable(cat.get(pos)));
        }

        holder.tvDashboardCategoryLeft.setText(catLeft);
        holder.tvDashboardTotalLeft.setText(totalLeft);
        holder.tvDashboardCategoryRight.setText(catRight);
        holder.tvDashboardTotalRight.setText(totalRight);
    }

    @Override
    public int getItemCount() {

        int size = (cat.size()+1)/2;

        if((cat.size()+1)%2!=0){
            size=size+1;
        }

        return size;
    }

    class DashbAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvDashboardCategoryLeft;

        TextView tvDashboardCategoryRight;

        TextView tvDashboardTotalLeft;

        TextView tvDashboardTotalRight;

        RelativeLayout rlDashboardItemColor;

        RelativeLayout rlDashboardItemColorRight;

        ImageView ivDashboardIconLeft;

        ImageView ivDashboardIconRight;

        public DashbAdapterViewHolder(View itemView) {
            super(itemView);
            tvDashboardCategoryLeft = (TextView) itemView.findViewById(R.id.tvDashboardCategoryLeft);
            tvDashboardCategoryRight = (TextView) itemView.findViewById(R.id.tvDashboardCategoryRight);
            tvDashboardTotalLeft = (TextView) itemView.findViewById(R.id.tvDashboardTotalLeft);
            tvDashboardTotalRight = (TextView) itemView.findViewById(R.id.tvDashboardTotalRight);
            rlDashboardItemColor = (RelativeLayout) itemView.findViewById(R.id.rlDashboardItemColor);
            rlDashboardItemColorRight = (RelativeLayout) itemView.findViewById(R.id.rlDashboardItemColorRight);
            ivDashboardIconLeft = (ImageView) itemView.findViewById(R.id.ivDashboardIconLeft);
            ivDashboardIconRight = (ImageView) itemView.findViewById(R.id.ivDashboardIconRight);
        }
    }
}
