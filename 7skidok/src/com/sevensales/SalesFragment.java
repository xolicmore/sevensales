package com.sevensales;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sevensales.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
    	
    	View v = inflater.inflate(R.layout.sales, container, false); 
    	//Toast.makeText(getActivity().getApplicationContext()," main view", Toast.LENGTH_LONG).show();
    	
    	Log.v("SalesFragment", "Main");
    	
    	
    	
               
       
        
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
				
				//parent.getChildAt(position).get;
//				String item = ((TextView)view);
//				Log.v("item", );
				// getting values from selected ListItem
				SaleFragment fragment = new SaleFragment();
		        Bundle args = new Bundle();
		        
		        //parent.getItemAtPosition(position).
		        
		        TextView tv_id = (TextView) view.findViewById(R.id.shop_id);
		        TextView tv_name = (TextView) view.findViewById(R.id.shop_name);
		        TextView tv_description = (TextView) view.findViewById(R.id.description);	
		        
		        fragment.shop_id=tv_id.getText().toString();
		        fragment.shop_name=tv_name.getText().toString();
		        fragment.shop_desription=tv_description.getText().toString();
		        
		        fragment.setArguments(args);
		
		        FragmentManager fragmentManager = getFragmentManager();
		        
		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

			}
		});
        
        getActivity().setTitle("Скидки");
        return v;
    }
    
    public void getAllSales(){
    	//Log.v("SalesFragment", "hi");    	
    	Sales=new ArrayList<Sale>();  
    	
    	Sales.add(new Sale().Sale_test(1, "mexx"  , "desc 1"));   
    	Sales.add(new Sale().Sale_test(2, "ret"   , "desc 2"));  
    	Sales.add(new Sale().Sale_test(3, "need " , "desc 4")); 
    	Sales.add(new Sale().Sale_test(4, "need " , "desc 5")); 
    	Sales.add(new Sale().Sale_test(5, "need"  , "desc 6")); 
    	
    }
    
    public void getSalesInShop(int id){
    	//Log.v("SalesFragment", "hi");    	
    	Sales=new ArrayList<Sale>();  
    	
    	Sales.add(new Sale().Sale_test(id, "mexx"  , "desc 1"));   
    	Sales.add(new Sale().Sale_test(id, "ret"   , "desc 2")); 
    	
    }
}