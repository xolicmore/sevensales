package com.sevensales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SubscribesListItemAdapter extends BaseAdapter {
	
	private ArrayList<Subscribe> items;
    private Context context;
    private int numItems = 0;
    private SubscribesListItemAdapter self;
    private ListView listview;
    private Singleton singleton;
   
    
	public SubscribesListItemAdapter(final ArrayList<Subscribe> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
        this.self=this;
    }
	
	public SubscribesListItemAdapter(final ArrayList<Subscribe> items, Context context,ListView listview) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
        this.self=this;
        this.listview=listview;       
        this.singleton= (Singleton) context.getApplicationContext();;
    }
	
	public int getCount() {
        return numItems;
    }
	
	public void updateContent(ArrayList<Subscribe> newlist) {
		items.clear();
		items.addAll(newlist);
	    this.notifyDataSetChanged();
	    this.notifyDataSetInvalidated();
    }
	
	 public Subscribe getItem(int position) {
	        return items.get(position);
	 }

	 public long getItemId(int position) {
	        return 0;
	 }
	 
	 public View getView(int position, View convertView, ViewGroup parent) {       
	        final Subscribe item = items.get(position);
	        final int i = position;
	        final LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.subscribes_list_item, parent, false);
	               
	        TextView tv_keyword = (TextView) itemLayout.findViewById(R.id.keyword);
	        tv_keyword.setText(item.keyword);
	        
	        TextView delete = (TextView) itemLayout.findViewById(R.id.delete);
	        delete.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {   
	            	
	            	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	              	builder.setTitle("Подтверждение");          	
	              	final TextView notice = new TextView(context);          	
	              	notice.setText("Вы уверены, что точно хотите удалить эту подписку?");
	              	notice.setPadding(15,15, 15, 15);
	              	builder.setView(notice);
	              	
	              	builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() { 
	              	    @Override
	              	    public void onClick(DialogInterface dialog, int which) {
	              	    	item.submit(context.getApplicationContext());
	              	    	//Toast.makeText(context, String.valueOf(items.size()), 1).show();
	              	    	singleton.deleteInSubscribeListByPosition(i);	              	    	
	    	            	listview.setAdapter(new SubscribesListItemAdapter(items, context,listview));
	              	    }
	              	});
	              	builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
	              	    @Override
	              	    public void onClick(DialogInterface dialog, int which) {
	              	        dialog.cancel();
	              	    }
	              	});

	              	builder.show();
	              	
	            	//items.remove(i);
	            	
	              	//Log.d("asd",  String.valueOf(i));
	            	//arrayAdapter.notifyDataSetChanged();
	            }
	        }); 
	        
	        return itemLayout;        
	    }
}
