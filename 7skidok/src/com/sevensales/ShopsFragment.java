package com.sevensales;



import java.util.ArrayList;
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

public class ShopsFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    
    public ArrayList<Sale> Sales;
    
    public ShopsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View v = inflater.inflate(R.layout.sales, container, false); 
    	//Toast.makeText(getActivity().getApplicationContext()," main view", Toast.LENGTH_LONG).show();
    	
    	//Log.v("SalesFragment", "Main");
    	
    	
    	
               
       
        
        ArrayList<Sale> list =this.Sales; 
        
        ArrayList<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
        Map<String, Object> m;
        
        for (int i = 0; i < Sales.size(); i++) {
            m = new HashMap<String, Object>();
            m.put("shop_id", Sales.get(i).shop_id);
            m.put("shop_name", Sales.get(i).shop_name);
            m.put("description", Sales.get(i).description);
           // m.put(ATTRIBUTE_NAME_IMAGE, R.drawable.ic_launcher);
            data.add(m);
          }
        
        
        String[] from = new String[] { "shop_id" , "shop_name" , "description"  };
        int[] to = new int[] { R.id.shop_id , R.id.shop_name , R.id.description};
        
        SimpleAdapter adapter = new SimpleAdapter(
        		getActivity().getApplicationContext(),
        		data, R.layout.list_item, from, to);        
        
        ListView listview = (ListView) v.findViewById(R.id.sales_list);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				

				SalesFragment fragment = new SalesFragment();
//		       	fragment.getS    
				TextView tv_id = (TextView) view.findViewById(R.id.shop_id);
		        
				fragment.getSalesInShop(Integer.parseInt(tv_id.getText().toString()));
				
		        FragmentManager fragmentManager = getFragmentManager();
		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

			}
		});
        
        getActivity().setTitle("Скидки");
        return v;
    }
    
    public void getAllShops(){
    	//Log.v("SalesFragment", "hi");    	
    	Sales=new ArrayList<Sale>();  
    	
    	Sales.add(new Sale().Sale_test(1, "Amazon"  , "desc 1"));   
    	Sales.add(new Sale().Sale_test(2, "Ebay"   , "desc 2"));  
    	Sales.add(new Sale().Sale_test(3, "Electro " , "desc 4")); 
    	Sales.add(new Sale().Sale_test(4, "OnlineShopping" , "desc 5"));     	 
    	
    }
}