package com.hsh.duensina.museumsfuehrer.model;

import android.graphics.Bitmap;

import com.hsh.duensina.museumsfuehrer.presenter.IShowImagePresenter;
import com.hsh.duensina.museumsfuehrer.presenter.OnImageDownloadFinishedListener;

/**
 * Created by alex on 13.07.16.
 */
public interface IShowImageModel {
    public void setPresenter(OnImageDownloadFinishedListener presenter);
    public void downloadImage(String url);
}
