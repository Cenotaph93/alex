package com.hsh.duensina.museumsfuehrer.model;

import com.hsh.duensina.museumsfuehrer.presenter.OnImageDownloadFinishedListener;

/**
 * Created by alex on 10.08.16.
 */
public interface IShowRecommendationModel {
    void setPresenter(OnImageDownloadFinishedListener presenter);
    void downloadImage(String url);
}
