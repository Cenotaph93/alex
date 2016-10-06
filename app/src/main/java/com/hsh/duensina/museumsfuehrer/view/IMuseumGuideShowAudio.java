package com.hsh.duensina.museumsfuehrer.view;

/**
 * Created by alex on 15.07.16.
 */
public interface IMuseumGuideShowAudio {
    public void setPresenter();
    public void onPlayPauseButton();
    public void setPlayPauseButton(boolean playing);
    public void setSeekBar(int progress);
    public void setTextViewStart(String text);
    public void setTextViewLength(String text);

    void setAudioInfoTextView(String text);

    void onAudioLoaded();
}
