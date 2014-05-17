package com.sevensales;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class ConnectivityChangeReceiver  extends BroadcastReceiver{		
	 private SharedPreferences pref;
	 @Override
	 public void onReceive (Context context, Intent intent) {
//		 Log.d("zzz", "/./.,m/.,m");
		   if (intent.getAction().equals("newSale")){
			   try{
				   String s=intent.getExtras().get("json").toString();
				   pref = context.getApplicationContext().getSharedPreferences("Preferences", 0);
				   Editor editor=pref.edit();
				   editor.putString("push_sales", s);
				   editor.commit();
				   
				   //Log.d("n", String.valueOf(storage.sales_list.size()));
//				   Log.d("zzz", s);
				   //Toast.makeText(context, String.valueOf(storage.sales_list.size()), 50);
			      // storage.addToSalesList(intent.getExtras().get("json").toString());
			       //Log.d("n", String.valueOf(storage.sales_list.size()));
				   Intent i=new Intent(context,MainActivity.class);	
				   context.startActivity(new Intent(context,MainActivity.class).setFlags(i.FLAG_ACTIVITY_CLEAR_TASK|i.FLAG_ACTIVITY_NEW_TASK));
				   
				   
			   }catch(RuntimeException e){
				   
			   }				   
		   }
		   //String x= intent.getExtras().getString("json");
		   //Log.d("zzz", intent.getExtras().getString("json"));
	//    Toast.makeText(context, "Sdasasd'", 1000).show();
	 }

}
