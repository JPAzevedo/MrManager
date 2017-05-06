package pt.jpazevedo.mrmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pt.jpazevedo.mrmanager.R;
import pt.jpazevedo.mrmanager.util.ExpensesManagement;

/**
 * Created by joaopedroazevedo11 on 05/03/17.
 */

public class AddCategoryAdapter extends BaseAdapter {

    private Context context;

    private List<String> category;

    public AddCategoryAdapter(Context context, List<String> category){
        this.context = context;
        this.category = category;
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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_addcategory,viewGroup,false);
        TextView title = (TextView) view.findViewById(R.id.tvAddCategoryTitle);
        ImageView icon = (ImageView) view.findViewById(R.id.ivAddCategory);
        ExpensesManagement expensesManagement = new ExpensesManagement();
        title.setText(category.get(i));
        icon.setImageResource(expensesManagement.getExpenseSmallDrawable(i+1));
        return view;
    }


}
