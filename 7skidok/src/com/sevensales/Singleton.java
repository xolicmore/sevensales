package com.sevensales;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Application;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
	
	public ArrayList<Sale> sales_list=new ArrayList<Sale>();
	public ArrayList<Sale> search_sales_list=new ArrayList<Sale>();
	public ArrayList<Shop> shops_list=new ArrayList<Shop>();
	public ArrayList<Category> categories_list=new ArrayList<Category>();
	private ArrayList<Subscribe> subscribes_list=new ArrayList<Subscribe>();
	
	private SharedPerferencesExecutor<ArrayList<Subscribe>> sharedPerferencesExecutor;
	
	public String keyword = "";
	public String command = "";
	  
	  private int data=200;
	  
	  public void setContext(Context c){
		  appContext=c;
		  sharedPerferencesExecutor=new SharedPerferencesExecutor<ArrayList<Subscribe>>(c);
		  pref = c.getApplicationContext().getSharedPreferences("Preferences", 0);
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
		  if (pref.getString("subscribes", null) != null){
			  subscribes_list = sharedPerferencesExecutor.retreive("subscribes", new TypeToken<ArrayList<Subscribe>>(){}.getType());
	      } 		  
		  return subscribes_list;
	  }
	  
	  
	  
	  public void addToSubscribesList(Subscribe s){		  
		  Log.d("qwe", String.valueOf(inSubscribesList(s.keyword)) );
		  if (!inSubscribesList(s.keyword)) {
			  subscribes_list.add(s);
			  saveSubscribeList();
		  }else{
			  Toast.makeText(appContext.getApplicationContext(), "�������� �� ����� ������ ��� ����������", 2).show();
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
	  
	  public void downloadSales(){		 
			  new GetDataSales().execute();		  	  			  
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
	  
	  public void downloadShops(){
		  new GetDataShops().execute();			  
	  }
	  
	  public void downloadCategories(){
		  new GetDataCategories().execute();			  
	  }
	  
	  public void downloadData() {
		  if (sales_list.isEmpty()){
			  downloadCategories();
		      downloadShops();
		      downloadSales();
		  }	  		
	}
	   

		private class GetDataSales extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();				
				pDialog = new ProgressDialog(appContext);
				pDialog.setMessage("���� �������� ������...");
				pDialog.setCancelable(false);				
				pDialog.show();

			}

			@Override
			protected Void doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler(appContext);
				String jsonStr = sh.makeServiceCall(getSalesUrl(), ServiceHandler.GET);	
				sh.getSalesList(jsonStr, sales_list);
				return null;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
				if (pDialog.isShowing())
					pDialog.dismiss();
				
				SalesFragment fragment = new SalesFragment(); 
	    		fragment.sales=getSalesList();	    		
	    		appFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();			
			}
		}
		private class GetDataSearchSales extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();				
				pDialog = new ProgressDialog(appContext);
				pDialog.setMessage("���� �������� ������...");
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
		private class GetDataShops extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler(appContext);
				String jsonStr = sh.makeServiceCall(getShopsUrl(), ServiceHandler.GET);				
				sh.getShopsList(jsonStr, shops_list);
				return null;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
//				if (pDialog.isShowing())
//					pDialog.dismiss();				
//						
			}
		}
		private class GetDataCategories extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler(appContext);
				String jsonStr = sh.makeServiceCall(getCategoriesUrl(), ServiceHandler.GET);				
				sh.getCategoriesList(jsonStr,categories_list);
				return null;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
//				if (pDialog.isShowing())
//					pDialog.dismiss();				
						
			}
		}
		private class SendCommand extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

			}

			@Override
			protected Void doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler(appContext);
				Log.d("command =", command);
				String reply = sh.makeServiceCall(command, ServiceHandler.GET);
				Log.d("reply =", reply);
				
				return null;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);							
							
			}
		}
}