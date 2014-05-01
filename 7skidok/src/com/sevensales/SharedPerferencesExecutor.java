package com.sevensales;



import java.lang.reflect.Type;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPerferencesExecutor<T> {	 
	
	private SharedPreferences appSharedPrefs; 
 
	public SharedPerferencesExecutor(Context context) {		
		appSharedPrefs = context.getApplicationContext().getSharedPreferences("Preferences", 0);
	}
 
	public void save(String key, T sharedPerferencesEntry) { 
		   
		  Editor prefsEditor = appSharedPrefs.edit();
		  Gson gson = new Gson();
		  String json = gson.toJson(sharedPerferencesEntry);
		  
		  prefsEditor.putString(key, json);
		  prefsEditor.commit();
		  
 
	}
 
	public T retreive(String key, Type typeOfT) {  
		 Gson gson = new Gson();
		 String json = appSharedPrefs.getString(key, "");
		 return (T) gson.fromJson(json, typeOfT);
	}
}