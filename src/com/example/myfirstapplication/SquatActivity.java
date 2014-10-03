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

public class SquatActivity extends ExerciseActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_squat);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new SquatFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class SquatFragment extends ExerciseFragment {

		public SquatFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_squat,
					container, false);
			//get layout to add things to
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.squatLayout);
			EditText workingWeight = (EditText) rootView.findViewById(R.id.squatWeight);
			//load value
			final SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());

			if( myPreference.getString("squatWeight", "0") == "" ) {
				//					squatW = myPreference.getFloat("squatWeight", 0);
				myPreference.edit().putString("squatWeight", Integer.toString(30)).commit();
			}
			workingWeight.setText(myPreference.getString("squatWeight", "25"));
			workingWeight.addTextChangedListener(new WeightWatcher());
			for (int i=0; i < 5; i++) {
				//			for (int i=0; i < myPreference.getStringSet("plates", null).size(); i++) {
				//placeholder for the weight,rep,sets for set
				LinearLayout l = new LinearLayout(getActivity());
				l.setOrientation(LinearLayout.HORIZONTAL);
				l.setGravity(Gravity.CENTER_HORIZONTAL);
				
				String key = "weight";
				key = key + Integer.toString(i);
				float squatW = Float.parseFloat(myPreference.getString("squatWeight", "0"));
				float multiplier = Float.parseFloat(myPreference.getString(key, "0"));
				squatW = squatW * multiplier;
				
				TextView weight = new TextView(getActivity());
				weight.setId(i);
				weight.setText(Float.toString(squatW));
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