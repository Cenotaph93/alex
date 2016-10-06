package com.hsh.duensina.museumsfuehrer.model;

import com.hsh.duensina.museumsfuehrer.presenter.IShowAudioPresenter;

import java.io.Serializable;

/**
 * Created by alex on 15.07.16.
 */
public interface IShowAudioModel extends Serializable {
    public void setPresenter(IShowAudioPresenter presenter);
    public void initializePlayer(String url);
    public boolean onPlayPauseButton();
    public String getFullDurationString();
    public String getDurationString(int curPos);
    public int getCurentPosition();
    public int getDuration();

    void setCurrentPosition(int progress);

    void closeAudioPlayer();

    void pauseAudio();
}
