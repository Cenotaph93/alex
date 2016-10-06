package com.hsh.duensina.museumsfuehrer.presenter;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.model.IShowAudioModel;
import com.hsh.duensina.museumsfuehrer.model.ShowAudioModel;
import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideShowAudio;

/**
 * Created by alex on 15.07.16.
 */
public class ShowAudioPresenter implements IShowAudioPresenter{
    private transient IMuseumGuideShowAudio view;
    private IShowAudioModel model;

    public ShowAudioPresenter(IMuseumGuideShowAudio view){
        this.view = view;
        this.model = new ShowAudioModel();
        model.setPresenter(this);
    }

    @Override
    public void initializePlayer(String url) {
        model.initializePlayer(url);
    }

    @Override
    public void onAudioLoaded(){
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.onAudioLoaded();
            }
        });
    }

    @Override
    public void onPlayPauseButton() {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setPlayPauseButton(model.onPlayPauseButton());
            }
        });
    }

    @Override
    public void setPlayPauseButton(final boolean playing) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setPlayPauseButton(playing);
            }
        });
    }

    @Override
    public void setAudioLength(){
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setTextViewLength(model.getFullDurationString());
            }
        });
    }

    @Override
    public void updateAudioProgress(final int progressSeekBar, final String progressTextView) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setSeekBar(progressSeekBar);
                view.setTextViewStart(progressTextView);
            }
        });
    }

    @Override
    public void resetAudioUI(){
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setSeekBar(0);
                view.setTextViewStart("0:00");
            }
        });
    }

    @Override
    public void setCurrentPosition(int progress){
        model.setCurrentPosition(progress);
    }

    @Override
    public void setAudioInfoText(final String text){
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setAudioInfoTextView(text);
            }
        });
    }

    @Override
    public void closeAudioPlayer(){
        model.closeAudioPlayer();
    }

    @Override
    public IShowAudioModel getModel(){
        return model;
    }

    @Override
    public void setModel(IShowAudioModel model){
        this.model = model;
    }

    @Override
    public void pauseAudio() {
        model.pauseAudio();
        setPlayPauseButton(false);
    }
}
