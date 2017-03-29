package pt.jpazevedo.mrmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.jpazevedo.mrmanager.R;

/**
 * Created by joaopedroazevedo11 on 19/03/17.
 */

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.DashbAdapterViewHolder> {

    Context context;

    public AdapterDashboard(Context context){
        this.context = context;
    }

    @Override
    public DashbAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.adapter_dashboard, parent,false);
        DashbAdapterViewHolder mHolder = new DashbAdapterViewHolder(mView);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(DashbAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class DashbAdapterViewHolder extends RecyclerView.ViewHolder {

        public DashbAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
