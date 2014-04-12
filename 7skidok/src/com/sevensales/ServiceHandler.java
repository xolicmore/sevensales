package com.sevensales;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

public class ServiceHandler {

	static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;
	
	Context appContext;

	public ServiceHandler(Context c) {
		this.appContext=c;
	}

	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * */
	public String makeServiceCall(String url, int method) {
		return this.makeServiceCall(url, method, null);
	}

	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */
	public String makeServiceCall(String url, int method,
			List<NameValuePair> params) {
		try {
			// http client
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;
			
			// Checking http request method type
			if (method == POST) {
				HttpPost httpPost = new HttpPost(url);
				// adding post params
				if (params != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				}

				httpResponse = httpClient.execute(httpPost);

			} else if (method == GET) {
				// appending params to url
				if (params != null) {
					String paramString = URLEncodedUtils
							.format(params, "utf-8");
					url += "?" + paramString;
				}
				HttpGet httpGet = new HttpGet(url);

				httpResponse = httpClient.execute(httpGet);

			}
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;

	}
	
	public void getSalesList(String jsonStr, ArrayList<Sale> temp){
		
		JSONArray sales = null;
		JSONArray s_categories = null;
		
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
						
						s_categories= obj.getJSONArray("categories");
						
						Category[] temp_categories=new Category[s_categories.length()];
						
						for (int j = 0; j < s_categories.length(); j++) {		
							JSONObject c_obj = new JSONObject();
							c_obj=s_categories.getJSONObject(j);
							String s_c_id = c_obj.getString("id");
							String s_c_name = c_obj.getString("name");									
							temp_categories[j]=new Category(s_c_id,s_c_name);															
						}
					    
						temp.add(new Sale(id,name,short_name,shop_id,shop_name,date_start,
								date_end,left_sec,img_small,img_big,temp_categories));  
					}	
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}
		
	}
	
	public void getShopsList(String jsonStr, ArrayList<Shop> temp){
		
		JSONArray shops = null;
		JSONArray s_categories = null;
		
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
						s_categories= obj.getJSONArray("categories");
						
						Category[] temp_categories=new Category[s_categories.length()];
						
						for (int j = 0; j < s_categories.length(); j++) {		
							JSONObject c_obj = new JSONObject();
							c_obj=s_categories.getJSONObject(j);
							String s_c_id = c_obj.getString("id");
							String s_c_name = c_obj.getString("name");									
							temp_categories[j]=new Category(s_c_id,s_c_name);															
						}
					   
						temp.add(new Shop(s_id,s_name,s_sales,s_img_url,temp_categories));
						loadImage(s_img_url);
						
					}	
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}
		
	}
	
	public void getCategoriesList(String jsonStr, ArrayList<Category> temp){
		
		JSONArray categories = null;
		
		if (jsonStr != null) {
			try {
				
				JSONObject jsonObj = new JSONObject(jsonStr);
				
				if (jsonObj.getString("success")=="true") {
					
					categories=jsonObj.getJSONArray("categories");
					
					for (int i = 0; i < categories.length(); i++) {								
						
						JSONObject obj = new JSONObject();
						
						obj = categories.getJSONObject(i);
						String c_id = obj.getString("id");
						String c_name = obj.getString("name");	
						
						//Log.d("Cate:" + c_id,c_name+" ");
						
					   
						temp.add(new Category(c_id,c_name));  
					}	
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}	
	}
	
	private void loadImage(String url){
			ImageLoader imgLoader=new ImageLoader(appContext);
			imgLoader.getImageFromUrl(url);
	}
}

