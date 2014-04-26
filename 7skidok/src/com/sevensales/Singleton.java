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

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Application;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public final class Singleton extends Application {
	private ProgressDialog pDialog;
	private static String sales_url = "http://7skidok.ru/api/?action=sales";	
	private static String shops_url = "http://7skidok.ru/api/?action=shops";
	private static String categories_url = "http://7skidok.ru/api/?action=sale_categories";
	private static String base_command = "http://7skidok.ru/api/?action=";
	private Context appContext;
	private FragmentManager appFragmentManager;
	
	public ArrayList<Sale> sales_list=new ArrayList<Sale>();
	public ArrayList<Sale> search_sales_list=new ArrayList<Sale>();
	public ArrayList<Shop> shops_list=new ArrayList<Shop>();
	public ArrayList<Category> categories_list=new ArrayList<Category>();
	
	public String keyword = "";
	public String command = "";
	  
	  private int data=200;
	  
	  public void setContext(Context c){
		  appContext=c;
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
				pDialog.setMessage("Идет загрузка данных...");
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