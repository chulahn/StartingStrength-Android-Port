package com.example.myfirstapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class WarmupPreference extends EditTextPreference {

	private EditText EditWeight,EditRep,EditSet,EditWeight1,EditRep1,EditSet1,EditWeight2,EditRep2,EditSet2,EditWeight3,EditRep3,EditSet3,EditWeight4,EditRep4,EditSet4;
	private String[] values = new String[] {"0","5","2","40","5","1","60","5","1","80","5","1","100","5","1"};
	private EditText[] editTexts;


	public WarmupPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public WarmupPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("Activity","constructor 2");

		setDialogLayoutResource(R.layout.warmup_preference);
		//no need to set id
		EditWeight = new EditText(context, attrs);
		EditRep = new EditText(context, attrs);
		EditSet = new EditText(context, attrs);
		EditWeight1 = new EditText(context, attrs);
		EditRep1 = new EditText(context, attrs);
		EditSet1 = new EditText(context, attrs);
		EditWeight2 = new EditText(context, attrs);
		EditRep2 = new EditText(context, attrs);
		EditSet2 = new EditText(context, attrs);
		EditWeight3 = new EditText(context, attrs);
		EditRep3 = new EditText(context, attrs);
		EditSet3 = new EditText(context, attrs);
		EditWeight4 = new EditText(context, attrs);
		EditRep4 = new EditText(context, attrs);
		EditSet4 = new EditText(context, attrs);
		EditRep.setId(R.id.Rep0);
		EditWeight.setId(R.id.Weight0);
		EditSet.setId(R.id.Set0);
		EditRep1.setId(R.id.Rep1);
		EditWeight1.setId(R.id.Weight1);
		EditSet1.setId(R.id.Set1);
		EditRep2.setId(R.id.Rep2);
		EditWeight2.setId(R.id.Weight2);
		EditSet2.setId(R.id.Set2);
		EditRep3.setId(R.id.Rep3);
		EditWeight3.setId(R.id.Weight3);
		EditSet3.setId(R.id.Set3);
		EditRep4.setId(R.id.Rep4);
		EditWeight4.setId(R.id.Weight4);
		EditSet4.setId(R.id.Set4);
		editTexts = new EditText[]{EditWeight,EditRep, EditSet,EditWeight1,EditRep1,EditSet1,EditWeight2,EditRep2,EditSet2,EditWeight3,EditRep3,EditSet3,EditWeight4,EditRep4,EditSet4 };
//		EditWeight.setId(R.id.Weight1);
		

		EditSet = new EditText(context, attrs);
		EditWeight.setEnabled(true);
		Log.d("Activity", "EditWeight " + EditWeight.getText().toString());
		EditRep.setEnabled(true);
		EditSet.setEnabled(false);

		setPositiveButtonText("True");
		setNegativeButtonText("CANCEL");
		setDialogIcon(R.drawable.ic_action_search);

	}

	public WarmupPreference(Context context) {
		super(context);

	}

	public class TL implements TextWatcher {
		EditText textedit;
		
		public TL (EditText name) {
			textedit = name;
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onTextChanged(CharSequence s, int start,
				int before, int count) {
			Log.d("Activity", "Here " + textedit+ " " + s.toString());
			textedit.setText(s.toString());
		}
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}		

	}

	//method is called when settings is open
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		Log.d("Activity","onsetInitialvalue");
		super.setText(restoreValue ? getPersistedString(null) : (String) defaultValue);
		Toast.makeText(this.getContext(), "setText", Toast.LENGTH_LONG).show();
	}

	@Override
	protected View onCreateView( ViewGroup parent) {
		Log.d("Activity","created view");
		View v = super.onCreateView(parent);

		//
		//		  LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return v;
	}


	protected View onCreateDialogView() {
		Log.d("Activity","created dialog view");
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(R.layout.warmup_preference, null);
		SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

		for (int i=0; i<editTexts.length; i++) {
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
			EditText e = ((EditText) view.findViewById(editTexts[i].getId()));
			e.addTextChangedListener(new TL(editTexts[i]));
			if (myPreference.contains(key)) { 
				e.setText(myPreference.getString(key, ""));
			}
			else {
				Toast.makeText(getContext(), "Here", Toast.LENGTH_LONG).show();
				e.setText(myPreference.getString(key, values[i]));
			}
			
		}
		
		return view;
	}

	protected void onBindDialogView(View view) {
		Log.d("Activity", "onBindDialogView");
		super.onBindDialogView(view);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		Log.d("Activity","closed");
		SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
		if (positiveResult) {
			
			for (int i=0; i<editTexts.length; i++) {
				if (editTexts[i].getText().toString() == "") {
					Log.d("Activity", " " + (i/3));
				}
				String value =editTexts[i].getText().toString();
//				if (value == "") {
//					value = editTexts[i].;
//				}
				Log.d("Activity", value);
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
				key = key+num;
				myPreference.edit().putString(key, value).commit();
				Log.d("Activity", key + " " + myPreference.getString(key, ""));

			}
			Log.d("Activity", "here");
//			Log.d("Activity", myPreference.getString("set0", ""));
			//			String value = null;
//			Log.d("Activity", "edit Text " + EditWeight);
//			Log.d("Activity", "value is " + value);
//			if (callChangeListener(value)) {
//				Log.d("Activity","here");
//				setText(value);
//			}
		}

	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Log.d("Activity","saveInstance");
		super.onSaveInstanceState();
		final Parcelable superState = super.onSaveInstanceState();

		return superState;
	}



}
