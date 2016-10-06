package com.hsh.duensina.museumsfuehrer.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.hsh.duensina.museumsfuehrer.MuseumGuideStartActivity;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideStart;
import com.hsh.duensina.museumsfuehrer.model.ISendInitialContextModel;
import com.hsh.duensina.museumsfuehrer.model.SendInitialContextModel;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by alex on 25.05.16.
 */
public class MuseumGuidePresenter implements IMuseumGuidePresenter{

    IMuseumGuideStart viewGuideStart;
    ISendInitialContextModel intlConModel;

    public MuseumGuidePresenter(IMuseumGuideStart view){
        this.viewGuideStart = view;

        setIntlConModel(new SendInitialContextModel());
    }

    @Override
    public void sendInitialContext(int ageNum, String age, ArrayList<Integer> infoTypesInt, ArrayList<String>infoTypes, boolean checked) {
        HashSet<String> interestSet = new HashSet<>();

        for(Integer s : infoTypesInt){
            interestSet.add(""+s);
        }

        SharedPreferences sharedPref = MuseumGuideStartActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(MuseumGuideStartActivity.getActivity().getString(R.string.savedataset),checked);
        editor.putInt(MuseumGuideStartActivity.getActivity().getString(R.string.age_string),ageNum);
        editor.putStringSet(MuseumGuideStartActivity.getActivity().getString(R.string.interests_strings), interestSet);
        editor.commit();



        intlConModel.sendInitialContext(age, infoTypes);

    }

    @Override
    public void startMonitoring() {

    }

    public void setIntlConModel(ISendInitialContextModel model){
        intlConModel = model;
        intlConModel.setPresenter(this);
    }
}
