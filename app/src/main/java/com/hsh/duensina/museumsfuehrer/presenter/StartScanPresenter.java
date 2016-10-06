package com.hsh.duensina.museumsfuehrer.presenter;

import android.content.Intent;

import com.hsh.duensina.museumsfuehrer.MuseumGuideStartActivity;
import com.hsh.duensina.museumsfuehrer.model.SendCurrentContextService;
import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideStartScan;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideStartScanFragment;

/**
 * Created by alex on 01.06.16.
 */
public class StartScanPresenter implements IStartScanPresenter{
    IMuseumGuideStartScan view;

    @Override
    public void sendCurrentContext(){
        
    }

    @Override
    public void startCurrentContextService() {
        MuseumGuideStartActivity.getContext().startService(new Intent(MuseumGuideStartActivity.getContext(), SendCurrentContextService.class));
    }
}
