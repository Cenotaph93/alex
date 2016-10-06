package com.hsh.duensina.museumsfuehrer.entity;

import android.widget.VideoView;

import com.hsh.duensina.museumsfuehrer.presenter.IShowVideoPresenter;

/**
 * Created by alex on 31.07.16.
 */
public class VideoFragmentEntity {
    private static VideoView videoView;
    private static String videoText;
    private static int videoPosition;
    private static IShowVideoPresenter presenter;

    public static void saveStatus(VideoView view, String text, int pos, IShowVideoPresenter pres){
        videoView = view;
        videoText = text;
        videoPosition = pos;
        presenter = pres;
    }

    public static void resetStatus(){
        videoView = null;
        videoText = null;
        videoPosition = 0;
        presenter = null;
    }

    public static VideoView getVideoView() {
        return videoView;
    }

    public static void setVideoView(VideoView videoView) {
        VideoFragmentEntity.videoView = videoView;
    }

    public static String getVideoText() {
        return videoText;
    }

    public static void setVideoText(String videoText) {
        VideoFragmentEntity.videoText = videoText;
    }

    public static int getVideoPosition() {
        return videoPosition;
    }

    public static void setVideoPosition(int videoPosition) {
        VideoFragmentEntity.videoPosition = videoPosition;
    }

    public static IShowVideoPresenter getPresenter() {
        return presenter;
    }

    public static void setPresenter(IShowVideoPresenter presenter) {
        VideoFragmentEntity.presenter = presenter;
    }
}
