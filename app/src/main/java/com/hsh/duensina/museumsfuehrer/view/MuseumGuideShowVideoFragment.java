package com.hsh.duensina.museumsfuehrer.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.entity.VideoFragmentEntity;
import com.hsh.duensina.museumsfuehrer.presenter.IShowVideoPresenter;
import com.hsh.duensina.museumsfuehrer.presenter.ShowVideoPresenter;

public class MuseumGuideShowVideoFragment extends Fragment implements IMuseumGuideShowVideo, IMuseumGuideView{

    private VideoView videoView;
    private Button closeButton;
    private TextView videoTextView;
    private IShowVideoPresenter presenter;
    private static boolean hasStarted = false;

    public MuseumGuideShowVideoFragment() {
        // Required empty public constructor
    }

    public static MuseumGuideShowVideoFragment newInstance() {
        MuseumGuideShowVideoFragment fragment = new MuseumGuideShowVideoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("VIDEO","Wird jetzt abgespielt.");
        videoView = (VideoView) getView().findViewById(R.id.videoView);

        closeButton = (Button) getView().findViewById(R.id.closeVideoButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeView();
            }
        });

        videoTextView = (TextView) getView().findViewById(R.id.videoTextView);

        if(!hasStarted){
            setPresenter();
            presenter.setText(getArguments().getString("text"));
            presenter.initializePlayer(getArguments().getString("url"), videoView);
            hasStarted = true;
        } else {
            restoreStatus();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        saveStatus();
    }

    @Override
    public void setPresenter() {
        presenter = new ShowVideoPresenter(this);
        presenter.initializePlayer(getArguments().getString("url"), videoView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_show_video, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public VideoView getVideoView(){
        return videoView;
    }

    @Override
    public void setVideoView(VideoView videoView){
        this.videoView = videoView;
        videoView.start();
    }

    @Override
    public void setVideoTextView(String text){
        this.videoTextView.setText(text);
    }

    @Override
    public void closeView() {
        hasStarted = false;
        VideoFragmentEntity.resetStatus();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void saveStatus() {
        VideoFragmentEntity.saveStatus(videoView, videoTextView.getText().toString(), videoView.getCurrentPosition(), presenter);
    }

    @Override
    public void restoreStatus() {
        presenter = VideoFragmentEntity.getPresenter();
        //videoView = VideoFragmentEntity.getVideoView();
        presenter.initializePlayer(getArguments().getString("url"), videoView);
        setVideoTextView(VideoFragmentEntity.getVideoText().toString());
        videoView.seekTo(VideoFragmentEntity.getVideoPosition());
    }

    @Override
    public boolean hasStarted() {
        return hasStarted;
    }
}
