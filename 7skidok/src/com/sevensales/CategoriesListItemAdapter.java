package com.sevensales;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoriesListItemAdapter extends BaseAdapter {
	private ArrayList<Category> items;
    private Context context;
    private int numItems = 0;

    public CategoriesListItemAdapter(final ArrayList<Category> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
    }
 
    public int getCount() {
        return numItems;
    }

    public Category getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {       
        final Category item = items.get(position);
        final LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.categories_list_item, parent, false);
         
        TextView tv_id = (TextView) itemLayout.findViewById(R.id.id_c);
        tv_id.setText(String.valueOf(item.id));
        
        TextView tv_name = (TextView) itemLayout.findViewById(R.id.name);
        tv_name.setText(item.name);
        return itemLayout;        
    }
}
