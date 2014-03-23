package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SaleFragment extends Fragment {
	public static final String id = "shop_id";
	public static final String name = "shop_name";
	public static final String description = "description";
	
	public String shop_id;
	public String shop_name;
	public String shop_desription;
    
    public SaleFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	 View v = inflater.inflate(R.layout.sale, container, false);
    	
    	 //int i = getArguments().getInt("position");
    	
    	 TextView tv_shop_id=(TextView) v.findViewById(R.id.shop_id);
    	 TextView tv_shop_name=(TextView) v.findViewById(R.id.shop_name);
    	 TextView tv_description=(TextView) v.findViewById(R.id.description);
    	 
    	 tv_shop_id.setText(shop_id);
    	 tv_shop_name.setText(shop_name);
    	 tv_description.setText(shop_desription);
//    	 Toast toast = Toast.makeText(getActivity().getApplicationContext(),
//    			String.valueOf(i), Toast.LENGTH_LONG);
//    	 toast.show();
        
        getActivity().setTitle("Скидка");
        return v;
    }
    
    
   
}