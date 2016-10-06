package com.hsh.duensina.museumsfuehrer.view;

import android.widget.VideoView;

/**
 * Created by alex on 28.07.16.
 */
public interface IMuseumGuideShowVideo {
    void setPresenter();

    VideoView getVideoView();

    void setVideoView(VideoView videoView);

    void setVideoTextView(String text);
}
