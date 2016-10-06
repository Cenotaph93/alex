package com.hsh.duensina.museumsfuehrer.model;

import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.presenter.IShowVideoPresenter;

/**
 * Created by alex on 31.07.16.
 */
public class ShowVideoModel implements IShowVideoModel {

    private IShowVideoPresenter presenter;
    private MediaController mediaController;
    private Uri video;

    public ShowVideoModel(IShowVideoPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void initializePlayer(String url, VideoView videoView){
        mediaController = new MediaController(MuseumGuideShowInformationActivity.getActivity());
        mediaController.setAnchorView(videoView);
        video = Uri.parse(url);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        presenter.onPlayerInitialized(videoView);
    }
}
