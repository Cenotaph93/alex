package com.hsh.duensina.museumsfuehrer.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.hsh.duensina.museumsfuehrer.MuseumGuideStartActivity;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.presenter.IMuseumGuidePresenter;
import com.hsh.duensina.museumsfuehrer.presenter.MuseumGuidePresenter;
import com.hsh.duensina.museumsfuehrer.viewutil.MultiSpinner;

import java.util.ArrayList;

/**
 * Created by alex on 01.06.16.
 */
public class MuseumGuideStartFragment extends Fragment implements IMuseumGuideStart{
    private Spinner ageSpinner;
    private MultiSpinner interestsSpinner;
    private Button confirmButton;
    private IMuseumGuidePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialization of the UI elements
        presenter = new MuseumGuidePresenter(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        setAgeSpinner();
        setInfoSpinner();
        confirmButton = (Button) getView().findViewById(R.id.confirmButton);


        // Set a listener to pass on the input of the user
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send the user input to the presenter for further processing
                presenter.sendInitialContext(ageSpinner.getSelectedItemPosition(), (String) ageSpinner.getSelectedItem(), interestsSpinner.getSelectedItemPositions(), interestsSpinner.getSelectedItems(), true);

                // load "All done" Fragment into this Activity (will also start the beacon service)
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MuseumGuideStartScanFragment allDoneFragment = new MuseumGuideStartScanFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, allDoneFragment, "all Done");
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_start, container, false);
    }

    /**
     * This method creates spinners for stating the age of the user.
     */
    private void setAgeSpinner(){
        ageSpinner = (Spinner) getView().findViewById(R.id.ageSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                MuseumGuideStartActivity.getContext(), R.array.ages, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(spinnerAdapter);
    }

    /**
     * This method lets the user choose his personal interests
     */
    private void setInfoSpinner(){
        ArrayList<String> infoList = new ArrayList<>();
        for(int i=0;i<getResources().getStringArray(R.array.interestsForm).length;i++){
            infoList.add(getResources().getStringArray(R.array.interestsForm)[i]);
        }

        interestsSpinner = (MultiSpinner) getView().findViewById(R.id.infoSpinner);
        interestsSpinner.setItems(infoList, "Bitte auswählen...", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
            }
        });
    }

}
