package com.groganlabs.mishmash;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MishMashSettings extends PreferenceActivity {
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	 }
}
