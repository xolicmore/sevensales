package com.sevensales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SubscribesFragment extends Fragment {
	
	private static final String KEY = "Subscribe";
	private SharedPerferencesExecutor<Subscribe> sharedPerferencesExecutor;
	private SubscribesListItemAdapter adapter;
	private ListView listview;
	
	public ArrayList<Subscribe> subscribes_list;
	
	public SubscribesFragment() {
        
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View v = inflater.inflate(R.layout.subscribe, container, false);     
    	
//        ArrayList<Subscribe> list =this.subscribes_list;
//        
//        Collections.sort(subscribes_list, new Comparator<Subscribe>(){
//		    public int compare(Subscribe s1,Subscribe  s2) {
//		        return s1.keyword.compareToIgnoreCase(s2.keyword);
//		    }
//		});
        
        adapter = new SubscribesListItemAdapter(subscribes_list, getActivity(),listview);        
        
        listview = (ListView) v.findViewById(R.id.subscribes_list);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				

//				SalesFragment fragment = new SalesFragment(); 
//				TextView tv_id = (TextView) view.findViewById(R.id.id_c);
//				
//				Singleton storage=(Singleton) getActivity().getApplication();
//				fragment.sales=storage.getSalesListByCategory(Integer.parseInt(tv_id.getText().toString()));
//				
//		        FragmentManager fragmentManager = getFragmentManager();
//		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

			}
		});
        
      Button subscribeOnAndroid = (Button) v.findViewById(R.id.subscribeOnAndroid);
      subscribeOnAndroid.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
          	Singleton storage=(Singleton) getActivity().getApplicationContext();
          	Subscribe q=new Subscribe("qqq", "t");
          	storage.addToSubscribesList(q);  

          	adapter = new SubscribesListItemAdapter(storage.getSubscribesList(), getActivity(),listview);
          	listview.setAdapter(adapter);
//          	listview.refreshDrawableState();
//          	listview.invalidateViews();
          
          	
//          	Map<String,String> test = new HashMap< String, String>(); 
//          	test.put("id", String.valueOf(sale.id));
//          	test.put("type", "android");
//          	storage.sendCommand("subscribe",test );
//          	notifyUser("Подписка оформлена");
          }
      });     
        
        getActivity().setTitle("Мои подписки");
        return v;
    }
}
