package com.example.myfirstapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	private String[] values = new String[] {"0","5","2","40","5","1","60","5","1","80","5","1","100","5","1"};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		PreferenceManager.setDefaultValues(this, R.layout.warmup_preference, false);
		SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(this);
		for (int i=0; i<values.length; i++) {
			String num = Integer.toString(i/3);
			String key = "";
			if(i%3==0) {
				key = "weight";
			}
			if(i%3==1) {
				key = "rep";
			}
			if(i%3==2) {
				key = "set";
			}
			key+=num;
			
			if (myPreference.contains(key) == false) { 
				myPreference.edit().putString(key, values[i]).commit();
			}
			
		}
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
//	        case R.id.action_search:
//	            openSearch();
//	            return true;
	        case R.id.action_settings:
	            gotoSettings(this.getCurrentFocus());
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	public void gotoSettings(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	public void squat(View view) {
		Intent intent = new Intent(this, SquatActivity.class);
		startActivity(intent);
	}
	
	public void deadlift(View view) {
		Intent intent = new Intent(this, DeadliftActivity.class);
		startActivity(intent);
	}
	
	public void bench(View view)
	{
		Intent intent = new Intent(this, BenchActivity.class);
		startActivity(intent);
	}
}
