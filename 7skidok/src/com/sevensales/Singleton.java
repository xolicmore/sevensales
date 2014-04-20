package com.sevensales;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
	private Context appContext;
	private FragmentManager appFragmentManager;
	
	public ArrayList<Sale> sales_list=new ArrayList<Sale>();
	public ArrayList<Sale> temp_sales_list=new ArrayList<Sale>();
	public ArrayList<Shop> shops_list=new ArrayList<Shop>();
	public ArrayList<Category> categories_list=new ArrayList<Category>();
	
	public String keyword = "";
	  
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
	  
	@SuppressWarnings("unchecked")
	public void refreshSalesList(){
			 if (!temp_sales_list.isEmpty()) {
				 // sales_list=temp_sales_list;
				  sales_list.clear();
				  sales_list=(ArrayList<Sale>)temp_sales_list.clone();
				  temp_sales_list.clear();			
			  }
	}
	  
	@SuppressWarnings("unchecked")
	public void downloadSales(){
		  if (!sales_list.isEmpty()) {
			  temp_sales_list = (ArrayList<Sale>) sales_list.clone();			  
			  sales_list.clear();
			  //Log.d("1",String.valueOf(temp_sales_list.size()));
		  }
			  new GetDataSales().execute();		  	  			  
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
}