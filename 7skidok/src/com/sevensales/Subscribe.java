package com.sevensales;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class Subscribe {
	public String keyword;
	public String type;
	
	public Subscribe(String k,String t){
		keyword=k;
		type=t;		
	}
	
	public void submit(Context c){
		Singleton storage=(Singleton) c.getApplicationContext();
		Map<String,String> test = new HashMap< String, String>(); 
      	test.put("keyword", keyword);
      	test.put(type, "1");
      	storage.sendCommand("subscribe",test );
	}

}
