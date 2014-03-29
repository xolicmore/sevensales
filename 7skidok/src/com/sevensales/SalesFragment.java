package com.sevensales;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.sevensales.R;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SalesFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    
    public ArrayList<Sale> sales;
    
    
    public SalesFragment() {
        // Empty constructor required for fragment subclasses
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View v = inflater.inflate(R.layout.sales, container, false); 
    	//Toast.makeText(getActivity().getApplicationContext()," main view", Toast.LENGTH_LONG).show();
    	
    	//Log.v("SalesFragment", "Main");       
        
        ArrayList<Sale> list =this.sales; 
        

		Collections.sort(list, new Comparator<Sale>(){
		    public int compare(Sale s1, Sale s2) {
		        return String.valueOf(s1.id).compareToIgnoreCase(String.valueOf(s2.id));
		    }
		});
        
        ListAdapter adapter = new SalesListItemAdapter(list, getActivity()); 
        
        ListView listview = (ListView) v.findViewById(R.id.sales_list);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//parent.getChildAt(position).get;
//				String item = ((TextView)view);
//				Log.v("item", );
				// getting values from selected ListItem
				SaleFragment fragment = new SaleFragment();
		        Bundle args = new Bundle();
		        
		        fragment.sale=(Sale) parent.getItemAtPosition(position);
		        
		        fragment.setArguments(args);
		
		        FragmentManager fragmentManager = getFragmentManager();
		        
		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

			}
		});
        
        getActivity().setTitle("Скидки");
        return v;
    }
    
    public void getAllSales(){
    	
    	//Singleton storage=(Singleton) getActivity().getApplication();
    	//sales=storage.sales_list;
    	
    	//Log.v("SalesFragment", "hi");    	
    	//sales=new ArrayList<Sale>();  
    	//Log.v("Act ", test);
//  	sales.add(new Sale().Sale_test(1, "mexx"  , "desc 1"));   
//    	sales.add(new Sale().Sale_test(2, "ret"   , "desc 2"));  
//    	sales.add(new Sale().Sale_test(3, "need " , "desc 4")); 
//    	sales.add(new Sale().Sale_test(4, "need " , "desc 5")); 
//    	sales.add(new Sale().Sale_test(5, "need"  , "desc 6")); 
    //	Singleton storage=(Singleton) c;
//    	sales=storage.getSalesList();
    	//Log.v("Act ", getActivity().toString());
    	
    }
    
    public void getSalesInShop(int id){
    	//Log.v("SalesFragment", "hi");    	
    	sales=new ArrayList<Sale>();  
    	
    	sales.add(new Sale().Sale_test(id, "mexx"  , "desc 1"));   
    	sales.add(new Sale().Sale_test(id, "ret"   , "desc 2")); 
    	
    }
}