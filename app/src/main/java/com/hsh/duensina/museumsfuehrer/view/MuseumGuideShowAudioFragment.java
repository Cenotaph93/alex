package com.hsh.duensina.museumsfuehrer.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.entity.AudioFragmentEntity;
import com.hsh.duensina.museumsfuehrer.presenter.IShowAudioPresenter;
import com.hsh.duensina.museumsfuehrer.presenter.ShowAudioPresenter;

/**
 * This Fragment displays the UI for the presentation of an audio file and initializes the download of that file.
 */

public class MuseumGuideShowAudioFragment extends Fragment implements IMuseumGuideShowAudio, IMuseumGuideView{

    private IShowAudioPresenter presenter;
    private ImageButton playPauseButton;
    private SeekBar audioSeekBar;
    private TextView audioStartTextView;
    private TextView audioLengthTextView;
    private TextView audioInfoTextView;
    private Button closeButton;
    private ProgressDialog progress;
    private boolean hasStarted = false;


    public MuseumGuideShowAudioFragment() {
    }

    public static MuseumGuideShowAudioFragment newInstance() {
        MuseumGuideShowAudioFragment fragment = new MuseumGuideShowAudioFragment();

        return fragment;
    }

    @Override
    public void onStop(){
        Log.i("AUDIO","onStop() aufgerufen.");
        super.onStop();
    }

    @Override
    public void onPause(){
        Log.i("AUDIO","onPause() aufgerufen.");
        super.onPause();
        presenter.pauseAudio();
        saveStatus();
    }

    @Override
    public void onStart(){
        super.onStart();
            //Initialize all UI Elements
            closeButton = (Button) getView().findViewById(R.id.closeAudioButton);
            playPauseButton = (ImageButton) getView().findViewById(R.id.playPauseButton);
            playPauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPlayPauseButton();
                }
            });
            audioSeekBar = (SeekBar) getView().findViewById(R.id.audioSeekBar);

            // Set the current Audio position via the presenter
            audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        presenter.setCurrentPosition(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeView();
                }
            });


            audioStartTextView = (TextView) getView().findViewById(R.id.audioStartTextView);
            audioLengthTextView = (TextView) getView().findViewById(R.id.audioLengthTextView);
            audioInfoTextView = (TextView) getView().findViewById(R.id.audioTextView);
            audioInfoTextView.setMovementMethod(new ScrollingMovementMethod());


            setPresenter();
            presenter.setAudioInfoText(getArguments().getString("text"));
        if(!hasStarted) {
            presenter.initializePlayer(getArguments().getString("url"));
            hasStarted = true;
            Log.i("AUDIO","hasStarted = "+hasStarted);
            progress = new ProgressDialog(MuseumGuideShowInformationActivity.getActivity());
            progress.setTitle("Lade Audio");
            progress.setMessage("Bitte haben Sie einen Moment Geduld...");
            progress.show();
        } else {
            restoreStatus();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i("AUDIO","onCreate() von Audio aufgerufen.");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_show_audio, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        Log.i("AUDIO","onSaveInstanceState() aufgerufen.");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPresenter(){
        this.presenter = new ShowAudioPresenter(this);
    }

    @Override
    public void onPlayPauseButton() {
        presenter.onPlayPauseButton();
    }

    @Override
    public void setPlayPauseButton(boolean playing){
        if(playing){
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else{
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    @Override
    public void setSeekBar(int progress) {
        audioSeekBar.setProgress(progress);
    }

    @Override
    public void setTextViewStart(String text) {
        audioStartTextView.setText(text);
    }

    @Override
    public void setTextViewLength(String text) {
        audioLengthTextView.setText(text);
    }

    @Override
    public void setAudioInfoTextView(String text){
        audioInfoTextView.setText(text);
    }

    @Override
    public void onAudioLoaded(){
        progress.dismiss();
    }

    @Override
    public void saveStatus(){
        AudioFragmentEntity.saveStatus(presenter, false, audioSeekBar.getProgress(), audioStartTextView.getText().toString(), audioLengthTextView.getText().toString(), audioInfoTextView.getText().toString());
    }

    @Override
    public void restoreStatus(){
        presenter = AudioFragmentEntity.getPresenter();
        setPlayPauseButton(AudioFragmentEntity.isPlayPauseButton());
        audioSeekBar.setProgress(AudioFragmentEntity.getAudioSeekBar());
        audioStartTextView.setText(AudioFragmentEntity.getAudioStartTextView());
        audioLengthTextView.setText(AudioFragmentEntity.getAudioLengthTextView());
        audioInfoTextView.setText(AudioFragmentEntity.getAudioInfoTextView());
    }

    @Override
    public void closeView() {
        Log.i("FRAGMENT","closeView() aufgerufen");

        presenter.closeAudioPlayer();
        hasStarted = false;
        AudioFragmentEntity.resetStatus();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public boolean hasStarted() {
        return hasStarted;
    }
}
