package com.hsh.duensina.museumsfuehrer.presenter;

import android.widget.VideoView;

/**
 * Created by alex on 31.07.16.
 */
public interface IShowVideoPresenter {
    void initializePlayer(String url, VideoView videoView);
    void setText(String text);
    void onPlayerInitialized(VideoView videoView);
}
