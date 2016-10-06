package com.hsh.duensina.museumsfuehrer.view;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsh.duensina.museumsfuehrer.R;

/**
 * This class was meant to be a Preferences Fragment, could not be finished due to lack of time
 */
public class MuseumGuidePreferencesFragment extends PreferenceFragment {
    /*private MultiSelectListPreference interestsSelect;
    private ListPreference ageSelect;
    private CheckBoxPreference saveData;
*/
    public MuseumGuidePreferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //interestsSelect = (MultiSelectListPreference) getPreferenceManager().findPreference("pref_interests");
        //ageSelect = (ListPreference) getPreferenceManager().findPreference("pref_ages");
        //saveData = (CheckBoxPreference) getPreferenceManager().findPreference("pref_save_data");

        return inflater.inflate(R.layout.fragment_museum_guide_preferences, container, false);
    }

}
