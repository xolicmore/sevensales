package com.example.android.navigationdrawerexample;

import android.app.Application;

public final class Singleton extends Application {
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
	  
	  public int getData(){
	     return this.data;
	  }
	 
	  public void setData(int d){
	     this.data=d;
	  }
	   
}