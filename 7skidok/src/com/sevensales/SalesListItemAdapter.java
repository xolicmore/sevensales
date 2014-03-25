package com.sevensales;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

// Custom list item class for menu items
public class SalesListItemAdapter extends BaseAdapter {

    private ArrayList<Sale> items;
    private Context context;
    private int numItems = 0;

    public SalesListItemAdapter(final ArrayList<Sale> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
    }
 
    public int getCount() {
        return numItems;
    }

    public Sale getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       
        final Sale item = items.get(position);
        final LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
       
        //ImageView imgIcon = (ImageView) itemLayout.findViewById(R.id.imgIcon);
        // imgIcon.setImageDrawable(item.getPicture());        
        
        TextView tv_shop_id = (TextView) itemLayout.findViewById(R.id.shop_id);
        tv_shop_id.setText(String.valueOf(item.shop_id));
        
        TextView tv_shop_name = (TextView) itemLayout.findViewById(R.id.shop_name);
        tv_shop_name.setText(item.shop_name);
        
        TextView tv_shop_description = (TextView) itemLayout.findViewById(R.id.description);
        tv_shop_description.setText(item.description);

          return itemLayout;
        
    }
 
}