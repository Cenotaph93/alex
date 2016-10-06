package com.hsh.duensina.museumsfuehrer.entity;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hsh.duensina.museumsfuehrer.presenter.IShowAudioPresenter;

import org.w3c.dom.Text;

/**
 * Created by alex on 30.07.16.
 */
public class AudioFragmentEntity {

    private static IShowAudioPresenter presenter;
    private static boolean playPauseButton;
    private static int audioSeekBar;
    private static String audioStartTextView;
    private static String audioLengthTextView;
    private static String audioInfoTextView;

    public static void saveStatus(IShowAudioPresenter pres, boolean playPause, int seekbar, String startTextView, String lengthTextView, String infoTextView){
        presenter = pres;
        playPauseButton = playPause;
        audioSeekBar = seekbar;
        audioStartTextView = startTextView;
        audioLengthTextView = lengthTextView;
        audioInfoTextView = infoTextView;
    }

    public static void resetStatus(){
        presenter = null;
        playPauseButton = false;
        audioSeekBar = 0;
        audioStartTextView = null;
        audioLengthTextView = null;
        audioInfoTextView = null;
    }

    public static IShowAudioPresenter getPresenter() {
        return presenter;
    }

    public static void setPresenter(IShowAudioPresenter presenter) {
        AudioFragmentEntity.presenter = presenter;
    }

    public static boolean isPlayPauseButton() {
        return playPauseButton;
    }

    public static void setPlayPauseButton(boolean playPauseButton) {
        AudioFragmentEntity.playPauseButton = playPauseButton;
    }

    public static int getAudioSeekBar() {
        return audioSeekBar;
    }

    public static void setAudioSeekBar(int audioSeekBar) {
        AudioFragmentEntity.audioSeekBar = audioSeekBar;
    }

    public static String getAudioStartTextView() {
        return audioStartTextView;
    }

    public static void setAudioStartTextView(String audioStartTextView) {
        AudioFragmentEntity.audioStartTextView = audioStartTextView;
    }

    public static String getAudioLengthTextView() {
        return audioLengthTextView;
    }

    public static void setAudioLengthTextView(String audioLengthTextView) {
        AudioFragmentEntity.audioLengthTextView = audioLengthTextView;
    }

    public static String getAudioInfoTextView() {
        return audioInfoTextView;
    }

    public static void setAudioInfoTextView(String audioInfoTextView) {
        AudioFragmentEntity.audioInfoTextView = audioInfoTextView;
    }
}
