package com.example.myfirstapplication;

import java.util.Arrays;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toast;

public class ExerciseActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_exercise);
		setupActionBar();
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//			.add(R.id.container, new ExerciseFragment()).commit();
//		}	
	}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise, menu);

		return true;
	}
	
	public void gotoSettings(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
            gotoSettings(this.getCurrentFocus());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ExerciseFragment extends Fragment {

		public ExerciseFragment() {
		}
		
		public class WeightWatcher implements TextWatcher {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				String exercise = getActivity().getClass().getSimpleName().toLowerCase().replace("activity","Weight");
				Toast.makeText(getActivity(), exercise, Toast.LENGTH_LONG).show();
				final SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
				//error handling
				if (s.length() == 0 || s.length() >= 10) {
					myPreference.edit().putString(exercise, Integer.toString(0)).commit();
				}

				else {
					//if last char is a .
					if (s.charAt(s.length()-1) == ".".toCharArray()[0]) {
						myPreference.edit().putString(exercise, s.toString().substring(0,s.length()-1)).commit();
					}
					else {
					myPreference.edit().putString(exercise, s.toString()).commit();
					}
				}
				
				changeWeight();	
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
			
		}

		@Override 
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_exercise,
					container, false);
			//get layout to add things to
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.squatLayout);
			EditText workingWeight = (EditText) rootView.findViewById(R.id.squatWeight);
			//load value
			final SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
			workingWeight.setText(myPreference.getString("squatWeight", ""));
			workingWeight.addTextChangedListener(new WeightWatcher());
			for (int i=0; i < getResources().getStringArray(R.array.Plates).length; i++) {
				//			for (int i=0; i < myPreference.getStringSet("plates", null).size(); i++) {
				//placeholder for the weight,rep,sets for set
				LinearLayout l = new LinearLayout(getActivity());
				l.setOrientation(LinearLayout.HORIZONTAL);
				l.setGravity(Gravity.CENTER_HORIZONTAL);
				TextView plate = new TextView(getActivity());
				plate.setText(getResources().getStringArray(R.array.Plates)[i]);
				plate.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				plate.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(plate);

				TextView rep = new TextView(getActivity());
				rep.setText(myPreference.getString("bodyweight", "100"));
				rep.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				rep.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(rep);

				TextView set = new TextView(getActivity());
				set.setId(i);
				float squatW = Integer.parseInt(myPreference.getString("squatWeight", ""));
				squatW = squatW * i;
				set.setText(Float.toString(squatW));
				set.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
				set.setGravity(Gravity.CENTER_HORIZONTAL);
				l.addView(set);

				layout.addView(l);
			}
			return rootView;
		}

		public void changeWeight(){
			final SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
			
			String exercise = this.getClass().getSimpleName().toLowerCase().replace("fragment","Weight");
			//change 
			for (int i=0; i<5; i++) {
				TextView t = (TextView) getView().findViewById(i);
				String setKey = "set" + Integer.toString(i);
				float weight = Float.parseFloat(myPreference.getString(exercise, ""));
				String key = "weight";
				key = key + Integer.toString(i);
				float multiplier = Float.parseFloat(myPreference.getString(key, "0"));
				weight = weight * multiplier;
				t.setText(Float.toString(weight) + numToPlate(weight));
			}
		}
		
		public String numToPlate(float weight) {
			float orig = weight;
			String returnValue = "(";
			final SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
			Set<String> plates = myPreference.getStringSet("plates", null);
			String[] p = plates.toArray(new String[plates.size()]);
			float[] plate = new float[p.length];
			for (int i=0; i<plates.size(); i++) {
				plate[i] = Float.parseFloat(p[i]);
			}
			Arrays.sort(plate);
			int[] outPlates = new int[plates.size()];
			for (int i=0; i<outPlates.length; i++) {
				outPlates[i] = -1;
			}

			if (weight > plate[plate.length-1]) {
				weight = (weight-plate[plate.length-1])/2;
			
				for (int j=plate.length-1; j>-1; j--) {
					if (weight / plate[j]  >= 1) {
						outPlates[j] = (int) Math.floor(weight/plate[j]);
						weight = weight - (outPlates[j] * plate[j]);
					}
				}
				
				for (int k=plate.length-1; k>-1; k--) {
					if (outPlates[k] != -1) {
						if (Float.toString(plate[k]).charAt(Float.toString(plate[k]).length()-1) == "0".toCharArray()[0]) {
						returnValue += (int)plate[k] +"x"+outPlates[k] + " ";
						}
						else {
							returnValue += plate[k] +"x"+outPlates[k] + " ";
						}
					}
				}
				
				if (returnValue.length() > 1) {
					returnValue= returnValue.substring(0, returnValue.length()-1);
				}
			}
			if (orig < 2*plate[0] + plate[plate.length-1]) {
				returnValue += "Bar";
			}
			returnValue += ")";
		
			return returnValue;
		}
		
	}
}
