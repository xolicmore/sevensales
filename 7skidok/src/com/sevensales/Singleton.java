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
	private Context appContext;
	private FragmentManager appFragmentManager;
	
	private JSONArray sales = null;
	private JSONObject sale = null;	
	
	private JSONArray shops = null;
	private JSONArray categories = null;
	private JSONObject shop = null;	
	
	public ArrayList<Sale> sales_list=new ArrayList<Sale>();
	public ArrayList<Shop> shops_list=new ArrayList<Shop>();
//	private MyClass m_A = new MyClass();
//	@Override
//	
//	  public void onCreate() {	
//	     super.onCreate();		     	
//	     Resources r = this.getResources(); 
//	  }	
//	 
//	
//	  private MyClass getA() {	
//	    return m_A;	
//	  }
	  
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
		  String device_uuid = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		  String url= sales_url+"&uid="+device_uuid; 
		  //Log.d("your device uuid --> ", url);
		  return url;
	  }
	  
	  public String getShopsUrl(){
		  String device_uuid = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		  String url= shops_url+"&uid="+device_uuid; 
		  //Log.d("your device uuid --> ", url);
		  return url;
	  }
	  
	  public ArrayList<Sale> getSalesList(){	
//		  sales_list.add(new Sale("1","test app1","test","7","shop test","111",
//					"999","111","none","none big"));
//		  sales_list.add(new Sale("9","test app3","test","7","shop test","111",
//					"555","999","none","none big"));
//		  sales_list.add(new Sale("4","test app2","test","7","shop test","111",
//					"111","333","none","none big"));
		  return sales_list;
	  }
	  
	  public ArrayList<Shop> getShopsList(){	
//		  sales_list.add(new Sale("1","test app1","test","7","shop test","111",
//					"999","111","none","none big"));
//		  sales_list.add(new Sale("9","test app3","test","7","shop test","111",
//					"555","999","none","none big"));
//		  sales_list.add(new Sale("4","test app2","test","7","shop test","111",
//					"111","333","none","none big"));
		  return shops_list;
	  }
	  
	  public void downloadSales(){
		  new GetDataSales().execute();			  
	  }
	  public void downloadShops(){
		  new GetDataShops().execute();			  
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
				ServiceHandler sh = new ServiceHandler();
				String jsonStr = sh.makeServiceCall(getSalesUrl(), ServiceHandler.GET);				

				if (jsonStr != null) {
					try {
						
						JSONObject jsonObj = new JSONObject(jsonStr);
						
						if (jsonObj.getString("success")=="true") {
							
							sales=jsonObj.getJSONArray("sales");
							
							for (int i = 0; i < sales.length(); i++) {								
								
								JSONObject obj = new JSONObject();
								
								obj = sales.getJSONObject(i);
								String id = obj.getString("id");
								String name = obj.getString("name");
								String short_name = obj.getString("short_name");
								String shop_id = obj.getString("shop_id");
								String shop_name = obj.getString("shop_name");
								String date_start = obj.getString("date_start");
								String date_end = obj.getString("date_end");
								String left_sec = obj.getString("left_sec");
								String img_small = obj.getString("img_small");
								String img_big = obj.getString("img_big");
								
//								Log.d("Sales_id:" + id,
//										name+" "+
//										short_name+" "+
//										shop_name+" "+
//										shop_id+" "+
//										date_start+" "+
//										date_end+" "+
//										left_sec+" "+
//										img_small+" "+
//										img_big+" ");
								
							    
								sales_list.add(new Sale(id,name,short_name,shop_id,shop_name,date_start,
										date_end,left_sec,img_small,img_big));  
							}	
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("ServiceHandler", "Couldn't get any data from the url");
				}

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
				pDialog = new ProgressDialog(appContext);
				pDialog.setMessage("Идет загрузка данных...");
				pDialog.setCancelable(false);				
				pDialog.show();

			}

			@Override
			protected Void doInBackground(Void... arg0) {				
				ServiceHandler sh = new ServiceHandler();
				String jsonStr = sh.makeServiceCall(getShopsUrl(), ServiceHandler.GET);				

				if (jsonStr != null) {
					try {
						
						JSONObject jsonObj = new JSONObject(jsonStr);
						
						if (jsonObj.getString("success")=="true") {
							
							shops=jsonObj.getJSONArray("shops");
							
							for (int i = 0; i < shops.length(); i++) {								
								
								JSONObject obj = new JSONObject();
								
								obj = shops.getJSONObject(i);
								String s_id = obj.getString("id");
								String s_name = obj.getString("name");
								String s_sales = obj.getString("sales");
								String s_img_url = obj.getString("img_url");
								
								categories= obj.getJSONArray("categories");
								
								Category[] s_categories=new Category[categories.length()];
								
								for (int j = 0; j < categories.length(); j++) {		
									JSONObject c_obj = new JSONObject();
									c_obj=categories.getJSONObject(j);
									String s_c_id = c_obj.getString("id");
									String s_c_name = c_obj.getString("name");									
									s_categories[j]=new Category(s_c_id,s_c_name);
									
									//Log.d("Categories:" + s_id,s_c_id+" "+s_c_name+" ");									
								}
								
//								Log.d("Sales_id:" + id,
//										name+" "+
//										short_name+" "+
//										shop_name+" "+
//										shop_id+" "+
//										date_start+" "+
//										date_end+" "+
//										left_sec+" "+
//										img_small+" "+
//										img_big+" ");
								
							   
								shops_list.add(new Shop(s_id,s_name,s_sales,s_img_url,s_categories));  
							}	
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("ServiceHandler", "Couldn't get any data from the url");
				}

				return null;
			}

			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
				if (pDialog.isShowing())
					pDialog.dismiss();				
						
			}
		}
}