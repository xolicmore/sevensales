/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sevensales;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.sevensales.R;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends Activity {
    
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mElementsTitles;
    
    public Singleton storage;
    public BroadcastReceiver observer;;
    public final static String BR ="com.sevensales";
    
    public Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = this.getApplication();
        
        storage=(Singleton) this.getApplication();
        storage.setContext(MainActivity.this);
        storage.setFragmentManager(getFragmentManager());
        storage.downloadData();        
//        Toast.makeText(getApplicationContext(), String.valueOf(storage.sales_list.size()), 111).show();
        //registerReceiver(new ConnectivityChangeReceiver(), new IntentFilter("com.sevensales.MainActivity.ConnectivityChangeReceiver"));
//        observer = new ConnectivityChangeReceiver();
//        registerReceiver(observer, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
                     
//        }
//        Map<String,String> test = new HashMap< String, String>(); 
//        test.put("email", "andrsere@yandex.ru");
//        test.put("gcm_id", "none");
//        storage.sendCommand("entry",test );
 
        mTitle = mDrawerTitle = getTitle();
        mElementsTitles = getResources().getStringArray(R.array.left_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mElementsTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
               // getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);        
        
        if (savedInstanceState == null) {
            selectItem(0);
        }

		  if (!storage.push_sales_list.isEmpty()) {
			  selectItem(5);
			  Log.d("why", "ASd'");
		  }
         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
     //   SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_websearch).getActionView();
//        if (null != searchView) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setIconifiedByDefault(false);
//        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {                
                return true;
            }

            public boolean onQueryTextSubmit(String query) {            	          	           	
            	storage.downloadSearchShops(query);
            	SalesFragment fragment = new SalesFragment(); 
        		fragment.sales=storage.getSalesList();    		
        		
        		FragmentManager fragmentManager = getFragmentManager();
    	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            	return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_websearch:
             
//        	  Intent intent = new Intent(Intent.ACTION_SEARCH);
//              intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//            } else {
              //  Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            //}
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
    	getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    	FragmentManager fragmentManager = getFragmentManager();
    	//storage.refreshSalesList();
    	//Log.d("q",String.valueOf(storage.sales_list.size()));
    	
    	//Toast.makeText(this,String.valueOf(getFragmentManager().getBackStackEntryCount()), Toast.LENGTH_LONG).show();getFragmentManager().getBackStackEntryCount();

    	
    	if (0==position) {    		
    		SalesFragment fragment = new SalesFragment();    		
    		fragment.sales=storage.getSalesList();    	
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	        
    	}
    	
    	if (1==position) {    		
    		ShopsFragment fragment = new ShopsFragment();
    		fragment.shops=storage.getShopsList(); 
    		
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	        
    	}
    	
    	if (2==position) {    		
    		CategoriesFragment fragment = new CategoriesFragment();
    		fragment.categories=storage.getCategoriesList();
    		
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	        
    	}
    	
    	if (3==position) {    		
    		SettingsFragment fragment = new SettingsFragment(); 
    		  		
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	       
    	}
    	
    	if (4==position) {    		
    		SubscribesFragment fragment = new SubscribesFragment();
    		fragment.subscribes_list=storage.getSubscribesList();
	  		
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    	}
    	if (5==position){
    		SalesFragment fragment = new SalesFragment(); 
			  fragment.sales=storage.push_sales_list;
			  fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    	}
    	
    	mDrawerList.setItemChecked(position, true);	        
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

//    @Override
//    protected void onResume() {
//       observer = new ConnectivityChangeReceiver();
//       registerReceiver(
//    		   observer, 
//             new IntentFilter(
//                   ConnectivityManager.CONNECTIVITY_ACTION));
//       super.onResume();
//    }
//     
//    @Override
//    protected void onPause() {
//    	unregisterReceiver(observer);
//    	super.onPause();
//    }
//    @Override
//    protected void onDestroy(){
//    	unregisterReceiver(observer);
//    	super.onDestroy();
//    }
    

}