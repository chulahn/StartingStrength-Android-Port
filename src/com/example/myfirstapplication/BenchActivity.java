package com.example.myfirstapplication;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BenchActivity extends ExerciseActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bench);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new BenchFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class BenchFragment extends ExerciseFragment {

		public BenchFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_bench,
					container, false);
			//get layout of BenchPage to add things to
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.benchLayout);
			//input for Working Weight
			EditText workingWeight = (EditText) rootView.findViewById(R.id.benchWeight);
			//Used to get values based on key
			final SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
			//if no benchWeight, automatically put 30 in key
			if( myPreference.getString("benchWeight", "0") == "" ) {
				myPreference.edit().putString("benchWeight", Integer.toString(30)).commit();
			}
			//get benchWeight key and set TextEdit text to it, if it wasnt there it should be 30 from previous line
			workingWeight.setText(myPreference.getString("benchWeight", "30"));
			workingWeight.addTextChangedListener(new WeightWatcher());
			for (int i=0; i < 5; i++) {
				//			for (int i=0; i < myPreference.getStringSet("plates", null).size(); i++) {
				//placeholder for the weight,rep,sets for set
				//main LinearLayout for page
				LinearLayout l = new LinearLayout(getActivity());
				l.setOrientation(LinearLayout.HORIZONTAL);
				l.setGravity(Gravity.CENTER_HORIZONTAL);
				
				//key to be used for getPreference for Bench weight
				String key = "weight";
				key = key + Integer.toString(i);
				float benchW = Float.parseFloat(myPreference.getString("benchWeight", "0"));
				float multiplier = Float.parseFloat(myPreference.getString(key, "0"));
				benchW = benchW * multiplier;
				TextView weight = new TextView(getActivity());
				weight.setId(i);
				weight.setText(Float.toString(benchW));
				weight.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				weight.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(weight);
				
				//Get the number of refs using a string and number from for loop, set text and add it to main layout
				key = "rep";
				key = key + i;
				TextView rep = new TextView(getActivity());
				rep.setText(myPreference.getString(key, "100"));
				rep.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				rep.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(rep);
				
				//Get number of sets and add
				key = "set";
				key = key + i;
				TextView set = new TextView(getActivity());
				set.setText(myPreference.getString(key, "100"));
				set.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				set.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(set);
				
				//error handling, if benchWeight > 10 chars, put in 0
				if (myPreference.getString("benchWeight", "0").length() >= 10) {
					myPreference.edit().putString("benchWeight", Integer.toString(0)).commit();
				}
				//if the last char was a . , put 0 in key
				if (myPreference.getString("benchWeight", "0").toCharArray()[myPreference.getString("benchWeight", "0").length()-1] == ".".toCharArray()[0]) {
					myPreference.edit().putString("benchWeight", Integer.toString(0)).commit();
				}
				

				//everything that was added to layout, add to main page layout
				layout.addView(l);
			}
			return rootView;
		}

	}
}