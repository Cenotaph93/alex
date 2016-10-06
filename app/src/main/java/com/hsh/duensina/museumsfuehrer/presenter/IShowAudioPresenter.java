package com.hsh.duensina.museumsfuehrer.presenter;

import com.hsh.duensina.museumsfuehrer.model.IShowAudioModel;
import com.hsh.duensina.museumsfuehrer.model.ShowAudioModel;

import java.io.Serializable;

/**
 * Created by alex on 15.07.16.
 */
public interface IShowAudioPresenter extends Serializable{

    void initializePlayer(String url);

    void onAudioLoaded();

    void onPlayPauseButton();

    void setPlayPauseButton(boolean playing);

    void setAudioLength();

    void updateAudioProgress(int progressSeekBar, String progressTextView);

    void resetAudioUI();

    void setCurrentPosition(int progress);

    void setAudioInfoText(String text);

    void closeAudioPlayer();

    IShowAudioModel getModel();

    void setModel(IShowAudioModel model);

    void pauseAudio();
}
