package com.hsh.duensina.museumsfuehrer.presenter;

import android.widget.VideoView;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.model.IShowVideoModel;
import com.hsh.duensina.museumsfuehrer.model.ShowVideoModel;
import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideShowVideo;

/**
 * Created by alex on 31.07.16.
 */
public class ShowVideoPresenter implements IShowVideoPresenter{
    private IMuseumGuideShowVideo view;
    private IShowVideoModel model;

    public ShowVideoPresenter(IMuseumGuideShowVideo view){
        this.view = view;
        model = new ShowVideoModel(this);
    }

    @Override
    public void initializePlayer(String url, final VideoView videoView) {
        model.initializePlayer(url, videoView);

    }

    @Override
    public void setText(final String text) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVideoTextView(text);
            }
        });
    }

    @Override
    public void onPlayerInitialized(final VideoView videoView) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVideoView(videoView);

            }
        });
    }
}
