package com.sevensales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import com.sevensales.R;
import com.sevensales.MainActivity.PlanetFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData.Item;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CategoriesFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    
    public ArrayList<Category> categories;
    
    
    public CategoriesFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View v = inflater.inflate(R.layout.category, container, false);     
    	
        ArrayList<Category> list =this.categories;
        
        Collections.sort(list, new Comparator<Category>(){
		    public int compare(Category s1,Category  s2) {
		        return s1.name.compareToIgnoreCase(s2.name);
		    }
		});
        
        ListAdapter adapter = new CategoriesListItemAdapter(list, getActivity());       
        
        ListView listview = (ListView) v.findViewById(R.id.categories_list);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				

				SalesFragment fragment = new SalesFragment(); 
				TextView tv_id = (TextView) view.findViewById(R.id.id);
				
				Singleton storage=(Singleton) getActivity().getApplication();
				fragment.sales=storage.getSalesListByCategory(Integer.parseInt(tv_id.getText().toString()));
				
		        FragmentManager fragmentManager = getFragmentManager();
		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

			}
		});
        
        getActivity().setTitle("���������");
        return v;
    }
    
    public void getAllShops(){
//    	//Log.v("SalesFragment", "hi");    	
//    	Sales=new ArrayList<Sale>();  
//    	
//    	Sales.add(new Sale().Sale_test(1, "Amazon"  , "desc 1"));   
//    	Sales.add(new Sale().Sale_test(2, "Ebay"   , "desc 2"));  
//    	Sales.add(new Sale().Sale_test(3, "Electro " , "desc 4")); 
//    	Sales.add(new Sale().Sale_test(4, "OnlineShopping" , "desc 5"));     	 
    	
    }
}