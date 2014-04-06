package com.sevensales;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShopsListItemAdapter extends BaseAdapter{
	private ArrayList<Shop> items;
    private Context context;
    private int numItems = 0;
    private ImageLoader imgLoader;

    public ShopsListItemAdapter(final ArrayList<Shop> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
        this.imgLoader = new ImageLoader(context);
    }
 
    public int getCount() {
        return numItems;
    }

    public Shop getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       
        final Shop item = items.get(position);
        final LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.shop_list_item, parent, false);
       
        //ImageView imgIcon = (ImageView) itemLayout.findViewById(R.id.imgIcon);
        // imgIcon.setImageDrawable(item.getPicture());        
        
        TextView tv_id = (TextView) itemLayout.findViewById(R.id.id);
        tv_id.setText(String.valueOf(item.id));
        
        TextView tv_name = (TextView) itemLayout.findViewById(R.id.name);
        tv_name.setText(item.name);

        TextView tv_sales = (TextView) itemLayout.findViewById(R.id.sales);
        tv_sales.setText(String.valueOf(item.sales));
        
        TextView tv_img_url = (TextView) itemLayout.findViewById(R.id.img_url);
        tv_img_url.setText(item.img_url);
        
        ImageView iv_image = (ImageView) itemLayout.findViewById(R.id.image);
        
		imgLoader.DisplayImage(item.img_url, 1, iv_image);
        
        
        
        
        
          return itemLayout;
        
    }
}
