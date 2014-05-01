package com.sevensales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
        	  
          	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
          	builder.setTitle("����� ��������");          	
          	final EditText input = new EditText(getActivity());          	
          	input.setInputType(InputType.TYPE_CLASS_TEXT);
          	builder.setView(input);
          	
//          	CheckBox feature1 = new CheckBox(getActivity());
//          	builder.create().
          	
          	builder.setPositiveButton("��������", new DialogInterface.OnClickListener() { 
          	    @Override
          	    public void onClick(DialogInterface dialog, int which) {
          	    	Singleton storage=(Singleton) getActivity().getApplicationContext();
          	    	
          	    	if (!input.getText().toString().isEmpty()){
          	    		Subscribe new_item=new Subscribe(input.getText().toString(), "android");
              	    	storage.addToSubscribesList(new_item);
              	    	adapter = new SubscribesListItemAdapter(storage.getSubscribesList(), getActivity(),listview);
                    	listview.setAdapter(adapter);
          	    	}else{
          	    		Toast.makeText(getActivity().getApplicationContext(), "�������� ����������", 2).show();
          	    	}
          	    	
          	    	
          	    }
          	});
          	builder.setNegativeButton("������", new DialogInterface.OnClickListener() {
          	    @Override
          	    public void onClick(DialogInterface dialog, int which) {
          	        dialog.cancel();
          	    }
          	});

          	builder.show();
//          	Map<String,String> test = new HashMap< String, String>(); 
//          	test.put("id", String.valueOf(sale.id));
//          	test.put("type", "android");
//          	storage.sendCommand("subscribe",test );
//          	notifyUser("�������� ���������");
          }
      });     
        
        getActivity().setTitle("��� ��������");
        return v;
    }
}
