package com.hsh.duensina.museumsfuehrer.view;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.hsh.duensina.museumsfuehrer.MuseumGuideStartActivity;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.viewutil.MultiSpinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class MuseumGuideSettingsFragment extends Fragment {
    private Spinner ageSpinner;
    private MultiSpinner infoSpinner;
    private Button confirmButton;
    private Switch saveDataSwitch;
    private Set<String> interests;
    private int age;
    private Boolean saveDataSet;

    public MuseumGuideSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart(){
        super.onStart();
        setAgeSpinner();
        setInfoSpinner();
        //saveDataSwitch = (Switch) getView().findViewById(R.id.saveDataSwitch);
        confirmButton = (Button) getView().findViewById(R.id.confirmButton);
        confirmButton.setText("Speichern");

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        saveDataSet = prefs.getBoolean(getString(R.string.savedataset), false);
        age = prefs.getInt(getString(R.string.age_string),0);
        interests = prefs.getStringSet(getString(R.string.interests_strings),new HashSet<String>());

        ArrayList<Integer> interestInt = new ArrayList<>();

        for (Iterator<String> it = interests.iterator(); it.hasNext(); ) {
            String interestString = it.next();
            interestInt.add(Integer.parseInt(interestString));
        }

        ageSpinner.setSelection(age);
        saveDataSwitch.setChecked(saveDataSet);
        infoSpinner.setSelectedPositions(interestInt);

        Log.i("SHAREDPREFERENCES","Alter = "+age+" Interessen: "+interests+" saveData = "+saveDataSet);

        // Set a listener to pass on the input of the user
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_settings, container, false);
    }

    private void setAgeSpinner(){
        ageSpinner = (Spinner) getView().findViewById(R.id.ageSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                MuseumGuideStartActivity.getContext(), R.array.ages, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(spinnerAdapter);
    }

    /**
     * This method lets the user choose the type information he receives.
     */
    private void setInfoSpinner(){
        ArrayList<String> infoList = new ArrayList<>();
        for(int i=0;i<getResources().getStringArray(R.array.interestsForm).length;i++){
            infoList.add(getResources().getStringArray(R.array.interestsForm)[i]);
        }

        infoSpinner = (MultiSpinner) getView().findViewById(R.id.infoSpinner);
        infoSpinner.setItems(infoList, "Bitte auswählen...", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
            }
        });
    }

}
