package com.sevensales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sevensales.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SaleFragment extends Fragment {
//	public static final String id = "shop_id";
//	public static final String name = "shop_name";
//	public static final String description = "description";
	
	public Sale sale;
	public String id;
	public String shop_id;
	public String name;
	public String short_name;	
	public String shop_name;
	public String date_start;
	public String date_end;
	public String left_sec;
	public String img_small;
	public String img_big;
	public TextView tv_left_sec;
	 
    public SaleFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	 View v = inflater.inflate(R.layout.sale, container, false);  
    	 
    	 
    	 TextView tv_name=(TextView) v.findViewById(R.id.name);
    	 
    	 TextView tv_shop_name=(TextView) v.findViewById(R.id.shop_name);
    	 
    	 TextView tv_date_start=(TextView) v.findViewById(R.id.date_start);
    	 TextView tv_date_end=(TextView) v.findViewById(R.id.date_end);
    	 tv_left_sec=(TextView) v.findViewById(R.id.left_sec);
    	 
    	 new CountDownTimer(Integer.parseInt(sale.left_sec)*1000, 1000) {
    		 public void onTick(long millisUntilFinished) {
    			 int leftsec=(int)millisUntilFinished/1000;
    			 
    			 if ((leftsec<604800)&&(leftsec>0)){
    				 int days=(int) (leftsec/(3600*24));
    				 int hours=(int) (leftsec/3600%24);
        			 int min=(int) (leftsec/60%60);
        			 int sec=(int)(leftsec%60);         			         	
        			 
        			 tv_left_sec.setText("До окончания: ");
        			 
        			 if (days!=0){
        				 tv_left_sec.append(days+" дней - ");
        			 }           			 
        			
        			 tv_left_sec.append(getTimeUnit(hours)+":"+getTimeUnit(min)+":"+getTimeUnit(sec));        			 
        		 }
    			
    			 if (leftsec>0){
    				 leftsec--;
    				 sale.left_sec=String.valueOf(leftsec);    				 
    			 }else{
    				 tv_left_sec.setText("Срок действия скидки окончен");
    			 }
    			 
    			 
    		 }
    		 public void onFinish() {
    			 tv_left_sec.setText("Действие скидки окончено!");
    		 }
    		}.start();
    	 tv_name.setText(sale.name);
    	 
    	 tv_shop_name.append(sale.shop_name);
    	 
    	 tv_date_start.append(sale.date_start);
    	 tv_date_end.append(sale.date_end);
    	 tv_left_sec.setText(sale.left_sec);
    	 
    	 
        getActivity().setTitle("Скидка");        
        //Singleton s = (Singleton)this.getActivity().getApplicationContext();
        //s.getSalesUrl();

        return v;
    }
    
    private String getTimeUnit(int n){
    	if (n<10){
    		return "0"+n;
    	}else
    	return String.valueOf(n);
    }
    
    @Override
    public String toString() {
        return this.shop_id + " " + this.shop_name + " ";
    }
    
    
   
}