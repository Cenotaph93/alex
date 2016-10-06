package com.hsh.duensina.museumsfuehrer.presenter;

import com.hsh.duensina.museumsfuehrer.model.ISendInitialContextModel;

import java.util.ArrayList;

/**
 * Created by alex on 25.05.16.
 */
public interface IMuseumGuidePresenter {
    void sendInitialContext(int ageNum, String age, ArrayList<Integer> infoTypesInt, ArrayList<String> infoTypes, boolean checked);

    public void startMonitoring();
    public void setIntlConModel(ISendInitialContextModel model);
}
