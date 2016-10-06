package com.hsh.duensina.museumsfuehrer.model;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import com.hsh.duensina.museumsfuehrer.presenter.IShowAudioPresenter;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by alex on 15.07.16.
 */
public class ShowAudioModel implements IShowAudioModel, MediaPlayer.OnPreparedListener, Serializable{
    private IShowAudioPresenter presenter;
    private transient MediaPlayer mediaPlayer;
    private boolean initialStage = true;
    private boolean playing = false;
    private transient Thread t;
    private boolean running = true;
    private int currentPosition;

    @Override
    public void setPresenter(IShowAudioPresenter presenter) {
        this.presenter = presenter;
        this.mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void initializePlayer(String url) {
        Log.i("PLAYER","Player initialisiert");
        if(initialStage){
            try {
                mediaPlayer.setDataSource(url);
                initialStage = false;
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        Log.i("PLAYER","Fertig");
                        presenter.setPlayPauseButton(false);
                        presenter.resetAudioUI();
                    }
                });
                mediaPlayer.prepareAsync();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onPlayPauseButton() {
        Log.i("PLAYER","OnPlayPause aufgerufen");
        if(!initialStage) {
            if (playing) {
                mediaPlayer.pause();
                playing = false;
                Log.i("PLAYER","Player gestoppt");
            } else {
                mediaPlayer.start();
                playing = true;
                Log.i("PLAYER","Player gestartet");
            }
            return playing;
        } else{
            return false;
        }
    }
    @Override
    public void onPrepared(MediaPlayer player) {
        Log.i("PLAYER","onPrepared aufgerufen");
        presenter.setAudioLength();
        updateAudioProgress();
        presenter.onAudioLoaded();
    }

    @Override
    public String getFullDurationString(){
        return getDurationString(mediaPlayer.getDuration()/1000);
    }

    @Override
    public String getDurationString(int curPos){
        int sec = (curPos%60*1);
        int min = curPos/60;

        return min+":"+String.format("%02d",sec);
    }

    @Override
    public int getCurentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public void updateAudioProgress(){

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(running) {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            currentPosition = getCurentPosition();
                            presenter.updateAudioProgress((int) (getCurentPosition() * 100.0 / getDuration()), getDurationString(getCurentPosition() / 1000));
                            Thread.sleep(1000);
                        }

                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        running = false;
                    } catch(IllegalStateException e){
                        running = false;
                    }
                }
            }
        });
        t.start();
    }

    @Override
    public void setCurrentPosition(int progress){
        mediaPlayer.seekTo(progress*getDuration()/100);
    }

    @Override
    public void closeAudioPlayer(){
        if(mediaPlayer != null && t != null) {
            if(t.isAlive()) {
                Log.i("Thread", "Thread läuft.");
                t.interrupt();
            }
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void pauseAudio() {
        if(mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            } catch (IllegalStateException e){
                mediaPlayer.release();
            }
        }
        playing = false;
    }

}
