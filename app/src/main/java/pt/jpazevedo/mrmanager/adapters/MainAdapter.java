package pt.jpazevedo.mrmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.jpazevedo.mrmanager.util.ExpandAnimation;
import pt.jpazevedo.mrmanager.R;

/**
 * Created by joaopedroazevedo11 on 23/02/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterHolder>{

    private Context context;
    private List<String> arr;
    private ArrayList<String> data_arr;

    public MainAdapter(Context context,List<String> arr){
        this.context = context;
        this.arr = arr;
        data_arr = new ArrayList<>();
        data_arr.addAll(arr);

    }

    @Override
    public MainAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //LayoutInflater my_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater my_inflater = LayoutInflater.from(context);
        View my_view = my_inflater.inflate(R.layout.adapter_history,parent,false);
        MainAdapterHolder holder = new MainAdapterHolder(my_view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainAdapterHolder holder, int position) {
        holder.tvMainAdapter.setText(data_arr.get(position));


        final View child = LayoutInflater.from(context).inflate(R.layout.adapter_listdetail, null);
        View child2 = LayoutInflater.from(context).inflate(R.layout.adapter_listdetail, null);
        View child3 = LayoutInflater.from(context).inflate(R.layout.adapter_listdetail, null);
        holder.llWrapper.addView(child);
        holder.llWrapper.addView(child2);
        holder.llWrapper.addView(child3);
        final LinearLayout llMaster = holder.llWrapper;

        holder.tvMainAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(llMaster.getVisibility() == View.VISIBLE){
                    ExpandAnimation.collapse(llMaster);
                }
                else{
                    ExpandAnimation.expand(llMaster);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return data_arr.size();
    }

    class MainAdapterHolder extends RecyclerView.ViewHolder{

        TextView tvMainAdapter;

        LinearLayout llWrapper;

        public MainAdapterHolder(View itemView) {
            super(itemView);

            tvMainAdapter = (TextView) itemView.findViewById(R.id.tvMainAdapter);
            llWrapper = (LinearLayout) itemView.findViewById(R.id.llWrapper);

        }
    }


}
