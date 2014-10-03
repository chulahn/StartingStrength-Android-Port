package com.example.myfirstapplication;

import com.example.myfirstapplication.ExerciseActivity.ExerciseFragment.WeightWatcher;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.os.Build;
import android.preference.PreferenceManager;

public class DeadliftActivity extends ExerciseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deadlift);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new DeadliftFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DeadliftFragment extends ExerciseFragment {

		public DeadliftFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_deadlift,
					container, false);

			//get layout to add things to
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.deadliftLayout);
			EditText workingWeight = (EditText) rootView.findViewById(R.id.deadliftWeight);
			//load value
			final SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
			//was not set, set it to 40
			if( myPreference.getString("deadliftWeight", "0") == "0" ) {
				myPreference.edit().putString("deadliftWeight", Integer.toString(40)).commit();
			}
			workingWeight.setText(myPreference.getString("deadliftWeight", "40"));
			workingWeight.addTextChangedListener(new WeightWatcher());

			for (int i=0; i < 5; i++) {
				//			for (int i=0; i < myPreference.getStringSet("plates", null).size(); i++) {
				//placeholder for the weight,rep,sets for set
				LinearLayout l = new LinearLayout(getActivity());
				l.setOrientation(LinearLayout.HORIZONTAL);
				l.setGravity(Gravity.CENTER_HORIZONTAL);
				
				String key = "weight";
				key = key + Integer.toString(i);
				float deadliftW = Float.parseFloat(myPreference.getString("deadliftWeight", "40"));

				float multiplier = Float.parseFloat(myPreference.getString(key, "0"));
				Log.d("Activity","multiplier is " + multiplier);
				deadliftW = deadliftW * multiplier;
				TextView weight = new TextView(getActivity());
				weight.setId(i);
				weight.setText(Float.toString(deadliftW));
				weight.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				weight.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(weight);
				
				key = "rep";
				key = key + i;
				TextView rep = new TextView(getActivity());
				rep.setText(myPreference.getString(key, "100"));
				rep.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				rep.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(rep);
				
				key = "set";
				key = key + i;
				TextView set = new TextView(getActivity());
				set.setText(myPreference.getString(key, "100"));
				set.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				set.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(set);

				layout.addView(l);
			}
			return rootView;
		}
	}
}
