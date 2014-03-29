package com.sevensales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sevensales.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SaleFragment extends Fragment {
//	public static final String id = "shop_id";
//	public static final String name = "shop_name";
//	public static final String description = "description";
	
	public Sale sale;
	public String id;
	public String shop_id;
	public String name;
	public String short_name;	
	public String shop_name;
	public String date_start;
	public String date_end;
	public String left_sec;
	public String img_small;
	public String img_big;
    
    public SaleFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	 View v = inflater.inflate(R.layout.sale, container, false);  
    	 
    	 TextView tv_id=(TextView) v.findViewById(R.id.id);
    	 TextView tv_name=(TextView) v.findViewById(R.id.name);
    	 TextView tv_short_name=(TextView) v.findViewById(R.id.short_name);
    	 TextView tv_shop_name=(TextView) v.findViewById(R.id.shop_name);
    	 TextView tv_shop_id=(TextView) v.findViewById(R.id.shop_id);
    	 TextView tv_date_start=(TextView) v.findViewById(R.id.date_start);
    	 TextView tv_date_end=(TextView) v.findViewById(R.id.date_end);
    	 TextView tv_left_sec=(TextView) v.findViewById(R.id.left_sec);
    	 
    	 tv_id.setText(String.valueOf(sale.id));
    	 tv_name.setText(sale.name);
    	 tv_short_name.setText(sale.short_name);
    	 tv_shop_name.setText(sale.shop_name);
    	 tv_shop_id.setText(String.valueOf(shop_id));
    	 tv_date_start.setText(sale.date_start);
    	 tv_date_end.setText(sale.date_end);
    	 tv_left_sec.setText(sale.left_sec);
    	 
    	 
        getActivity().setTitle("Скидка");        
        //Singleton s = (Singleton)this.getActivity().getApplicationContext();
        //s.getSalesUrl();

        return v;
    }
    
    @Override
    public String toString() {
        return this.shop_id + " " + this.shop_name + " ";
    }
    
    
   
}