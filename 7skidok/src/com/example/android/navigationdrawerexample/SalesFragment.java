package com.example.android.navigationdrawerexample;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.android.navigationdrawerexample.MainActivity.PlanetFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
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

public class SalesFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    
    public ArrayList<Sale> Sales;
    
    public SalesFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	this.Sales=new ArrayList<Sale>();  
    	
    	this.Sales.add(new Sale().Sale_test(1, "mexx" , "desc 1"));   
    	this.Sales.add(new Sale().Sale_test(2, "ret" , "desc 2"));  
    	this.Sales.add(new Sale().Sale_test(3, "fff " , "desc 3")); 
        View v = inflater.inflate(R.layout.sales, container, false);        
       
        
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
				// getting values from selected ListItem
				Fragment fragment = new SaleFragment();
		        Bundle args = new Bundle();
		        args.putInt("position", position);
		        fragment.setArguments(args);
		
		        FragmentManager fragmentManager = getFragmentManager();
		        
		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

			}
		});
        
        getActivity().setTitle("Скидки");
        return v;
    }
}