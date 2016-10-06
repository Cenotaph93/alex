package com.hsh.duensina.museumsfuehrer.model;

import com.hsh.duensina.museumsfuehrer.presenter.MuseumGuidePresenter;

import java.util.ArrayList;

/**
 * Created by alex on 25.05.16.
 */
public interface ISendInitialContextModel {
    public void sendInitialContext(String age, ArrayList<String> infoType);
    public void setPresenter(MuseumGuidePresenter presenter);
}
