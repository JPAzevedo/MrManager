package pt.jpazevedo.mrmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import pt.jpazevedo.mrmanager.R;

/**
 * Created by joaopedroazevedo11 on 05/03/17.
 */

public class AddCategoryAdapter extends BaseAdapter {

    private Context context;

    private List<String> category;

    private List<Integer> resources;

    public AddCategoryAdapter(Context context, List<String> category, List<Integer> resources){
        this.context = context;
        this.category = category;
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_addcategory,viewGroup,false);
        return view;
    }
}
