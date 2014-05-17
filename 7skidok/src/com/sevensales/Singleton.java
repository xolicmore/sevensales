package com.sevensales;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Application;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public final class Singleton extends Application {
	
	private ProgressDialog pDialog;
	private SharedPreferences pref;
	private static String sales_url = "http://7skidok.ru/api/?action=sales";	
	private static String shops_url = "http://7skidok.ru/api/?action=shops";
	private static String categories_url = "http://7skidok.ru/api/?action=sale_categories";
	private static String base_command = "http://7skidok.ru/service/gate.php?action=";
	private Context appContext;
	private FragmentManager appFragmentManager;
	private GoogleCloudMessaging gcm;
	private String regid;
	private static final String SENDER_ID = "384378969768";
	
	public ArrayList<Sale> sales_list=new ArrayList<Sale>();	
	public ArrayList<Sale> search_sales_list=new ArrayList<Sale>();
	public ArrayList<Shop> shops_list=new ArrayList<Shop>();
	public ArrayList<Category> categories_list=new ArrayList<Category>();
	public ArrayList<Subscribe> subscribes_list=new ArrayList<Subscribe>();
	public ArrayList<Sale> push_sales_list=new ArrayList<Sale>();
	
	private SharedPerferencesExecutor<ArrayList<Subscribe>> sharedPerferencesExecutor;
	
	public String keyword = "";
	public String command = "";
	  
	  private int data=200;
	  
	  public void setContext(Context c){
		  appContext=c;
		  sharedPerferencesExecutor=new SharedPerferencesExecutor<ArrayList<Subscribe>>(c);
		  pref = c.getApplicationContext().getSharedPreferences("Preferences", 0);
//		  Toast.makeText(c, pref.getString("notice", "qwe"), 41).show();
//		  Editor editor=pref.edit();
//			editor.putString("is_register", "-1");
//			editor.commit();
		  if (!(pref.getString("is_register", "-1")).equals("1")){		
			  Toast.makeText(appContext, pref.getString("is_register", "-1"), 15).show();
			  registration();
			  
		  }else{
//			  Log.d("reg", "already");
		  }

	  }
	  
	  public void setFragmentManager(FragmentManager f){
		  appFragmentManager=f;
	  }
	  
	  public int getData(){
	     return this.data;
	  }
	 
	  public void setData(int d){
	     this.data=d;
	  }
	  
	  
	  public String getDeviceId(){
		  String device_uuid = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);		  
		  return device_uuid;
	  }
	  
	  public String getSalesUrl(){	
		  String url= sales_url+"&uid="+getDeviceId();	  
		  return url;
	  }
	  
	  public String getSearchSalesUrl(){		  
		  keyword = keyword.replace("%", "%25");
		  String url= sales_url+"&uid="+getDeviceId()+"&keyword="+keyword; 		  
		  return url;
	  }
	  
	  public String getShopsUrl(){		  
		  String url= shops_url+"&uid="+getDeviceId(); 		  
		  return url;
	  }
	  
	  public String getCategoriesUrl(){		 
		  String url= categories_url+"&uid="+getDeviceId();		  
		  return url;
	  }
	  
	  public ArrayList<Sale> getSalesList(){	
		  //Log.d("n", String.valueOf(sales_list.size()));
		  return sales_list;
	  }
	  
	  public ArrayList<Sale> getSearchSalesList(){	
		  return search_sales_list;
	  }
	  
	  public ArrayList<Sale> getSalesListByShop(int id){
		  ArrayList<Sale> templist=new ArrayList<Sale>();
		   for(Sale s : sales_list){
				  if (s.shop_id==id){
					  templist.add(s);	       	          
			   }       
		    }		
		  return templist;
	  }
	
	  public ArrayList<Sale> getSalesListByCategory(int id){
		  ArrayList<Sale> templist=new ArrayList<Sale>();
		   for(Sale s : sales_list){
			   for(Category c : s.categories){
	       	          if (c.id==id){
	       	        	  templist.add(s);
	       	          } 
			   }       
		    }		
		  return templist;
	  }
	  
	  public ArrayList<Shop> getShopsList(){	
		  return shops_list;
	  }
	  
	  private void clearSubscribeList(){
			 subscribes_list.clear();			  
		  }
	  
	  private void saveSubscribeList(){
		  //clearSubscribeList();
		  sharedPerferencesExecutor.save("subscribes", subscribes_list);
	  }
	  
	  public void deleteInSubscribeListByPosition(int position){
		  subscribes_list.remove(position);
		  saveSubscribeList();
	  }

	  public ArrayList<Subscribe> getSubscribesList(){
		  subscribes_list.clear();
		  if (pref.getString("subscribes", "") != ""){
			  subscribes_list = sharedPerferencesExecutor.retreive("subscribes", new TypeToken<ArrayList<Subscribe>>(){}.getType());
	      }
//		  Log.d("appContext", "lolls+"+subscribes_list.toString());
		  return subscribes_list;
	  }
	  
	  public void addToSalesList(String jsonStr){
		  ServiceHandler sh = new ServiceHandler(appContext);		  
		  sh.getSalesList(jsonStr, sales_list);
	  }
	  
	  public void addToSubscribesList(Subscribe s){		  
		  //Log.d("qwe", String.valueOf(inSubscribesList(s.keyword)) );
		  if (!inSubscribesList(s.keyword)) {
			  subscribes_list.add(s);
			  saveSubscribeList();
		  }else{
			  Toast.makeText(appContext.getApplicationContext(), "Подписка на такое словое уже существует", 2).show();
		  }		  
	  }
	  
	  public boolean inSubscribesList(String word){		
		  getSubscribesList();
		  for(Subscribe s : subscribes_list){
   	          if (s.keyword.equals(word)){
   	        	  return true;
   	          }
	    }
		  return false;
	  }
		  
	  public ArrayList<Shop> getShopsListByCategory(int id){
		  ArrayList<Shop> templist=new ArrayList<Shop>();
		   for(Shop s : shops_list){
			   for(Category c : s.categories){
	       	          if (c.id==id){
	       	        	  templist.add(s);
	       	          }
			    }			          
		    }		
		  return templist;
	  }
	  
	  public ArrayList<Category> getCategoriesList(){
		  return categories_list;
	  }
	
	  public void downloadSearchShops(String query){
		  keyword=query;
		  search_sales_list.clear();
		  new GetDataSearchSales().execute();			  
	  }
	  
	  public void sendCommand(String action,Map<String , String> params){
		  command=base_command+action+"&guid="+getDeviceId();
		  
		  for (Map.Entry<String, String> item : params.entrySet()) {
			command+="&"+item.getKey()+"="+item.getValue();
		  }
		  new SendCommand().execute();		  
	  }
	  public void addToCommand(String key,String value){
		  command+="&"+key+"="+value;
	  }
	  
	  public void downloadData() {
		  if (sales_list.isEmpty()){
			  new GetData().execute();
		  }
		  
		  checkPushSales();		  
	  }
	   
	  public void checkPushSales(){
		  String push_sales = pref.getString("push_sales", "-1");
		  if (!push_sales.equals("-1")){
			  push_sales_list=new ArrayList<Sale>();
			  ServiceHandler sh = new ServiceHandler(appContext);			  
			  sh.getSalesList(push_sales, push_sales_list);
			  Editor editor=pref.edit();
			  editor.remove("push_sales");
			  editor.commit();
			  Boolean flag;		  
			  
			  for(Sale ps : push_sales_list){
				  flag=true;
				  for(Sale s : sales_list){
					  if (s.id==ps.id) {
						  //Log.d("id",String.valueOf(s.id));
						  //flag=false;
						  break;
					  }
				  }
				  if (flag){
					  sales_list.add(ps);
				  }else{
					  push_sales_list.remove(ps);
				  }
			  }	
			  
		  }
	  }
	  public void registration(){ 	 
		  Map<String,String> params = new HashMap< String, String>(); 
	      
	      sendCommand("entry",params );
	  }
	  
		private class GetData extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();				
				pDialog = new ProgressDialog(appContext);
				pDialog.setMessage("Идет загрузка данных...");
				pDialog.setCancelable(false);				
				pDialog.show();

			}

			@Override
			protected Void doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler(appContext);
				
				String jsonStr = sh.makeServiceCall(getSalesUrl(), ServiceHandler.GET);	
				sh.getSalesList(jsonStr, sales_list);
				
				jsonStr = sh.makeServiceCall(getShopsUrl(), ServiceHandler.GET);				
				sh.getShopsList(jsonStr, shops_list);
				
				jsonStr = sh.makeServiceCall(getCategoriesUrl(), ServiceHandler.GET);				
				sh.getCategoriesList(jsonStr,categories_list);
				
				return null;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
				if (pDialog.isShowing())
					pDialog.dismiss();
				
				if (push_sales_list.isEmpty()){
					SalesFragment fragment = new SalesFragment(); 
		    		fragment.sales=getSalesList();	    		
		    		appFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();	
				}						
			}
		}
		private class GetDataSearchSales extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();				
				pDialog = new ProgressDialog(appContext);
				pDialog.setMessage("Идет загрузка данных...");
				pDialog.setCancelable(false);				
				pDialog.show();

			}

			@Override
			protected Void doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler(appContext);
				String jsonStr = sh.makeServiceCall(getSearchSalesUrl(), ServiceHandler.GET);
				keyword="";
				sh.getSalesList(jsonStr, search_sales_list);
				return null;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
				if (pDialog.isShowing())
					pDialog.dismiss();
				
				SalesFragment fragment = new SalesFragment(); 
	    		fragment.sales=getSearchSalesList();	    		
	    		appFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();			
			}
		}
		
		private class SendCommand extends AsyncTask<Void, Void, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

			}

			@Override
			protected String doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler(appContext);
				
				if (!(pref.getString("is_register", "-1")).equals("1")){									
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(appContext);
	                }
	                try {
						regid = gcm.register(SENDER_ID);
					} catch (IOException e) {						
					}	                
	                addToCommand("gcm_id", regid);  
				}	
				
				Log.d("command =", command);
				
				String reply = sh.makeServiceCall(command, ServiceHandler.GET);
				Log.d("reply =", reply);
				return reply;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				result=result.replaceAll("[\n\r ]","");
				//Toast.makeText(appContext,  result+" "+"registered", 5000).show();
//				result = "registrated";
				
//				result=result.substring(3);
//				result=new StringBuilder(result).deleteCharAt(0).toString();
//				Boolean b= "registered".equalsIgnoreCase(result);
//				
//				Toast.makeText(appContext, Arrays.toString( result.toCharArray() ) , 5000).show();
//				Toast.makeText(appContext, Boolean.toString(b) , 5000).show();
				
				//Toast.makeText(appContext,"registrated".getBytes().toString() +" "+nr.getBytes().toString() , 5000).show();
				if ("registered".equalsIgnoreCase(result)){
//					Log.d("result", result );
					//Toast.makeText(appContext, "ADSads" , 5000).show();
					Editor editor=pref.edit();
					editor.putString("is_register", "1");
					editor.commit();
					
					
				}
//				Log.d("pref ", pref.getString("is_register", "lol") );
				
			}
		}
}