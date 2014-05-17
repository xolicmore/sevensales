package com.sevensales;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment{
	private SharedPreferences pref;
	private Editor editor;
	public TextView tv_email;
	public Switch s_switch;
	
	private static final String email="email";
	private static final String notice="notice";
	 
    public SettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.settings, container, false); 
    	pref = getActivity().getApplicationContext().getSharedPreferences("Preferences", Context.MODE_WORLD_WRITEABLE);
    	tv_email=(TextView) v.findViewById(R.id.email); 
    	s_switch = (Switch) v.findViewById(R.id.switch_notice);
    	    	 //Toast.makeText(getActivity().getApplicationContext(), pref.getString(notice, "asd") , 11).show();
    	 tv_email.setText(pref.getString(email, "")); 
    	 if (pref.getString("notice", "1").equals("1")){
    		 s_switch.setChecked(true);
    	 }
        return v;
    }    
    
    @Override
    public void onPause() {
    	pref = getActivity().getApplicationContext().getSharedPreferences("Preferences", Context.MODE_WORLD_WRITEABLE);
        editor=pref.edit();
    	editor.putString(email,tv_email.getText().toString());  
    	
    	if (s_switch.isChecked()){
    		editor.putString(notice,"1"); 
    	}else{
    		editor.putString(notice,"-1");
    	}
    	
        editor.commit();    	
        super.onPause();
    }  
}
