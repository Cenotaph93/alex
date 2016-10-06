package com.hsh.duensina.museumsfuehrer.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hsh.duensina.museumsfuehrer.presenter.OnImageDownloadFinishedListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alex on 10.08.16.
 */
public class ShowRecommendationModel implements IShowRecommendationModel {
    private OnImageDownloadFinishedListener presenter;

    @Override
    public void setPresenter(OnImageDownloadFinishedListener presenter) {
        this.presenter = presenter;
    }

    @Override
    public void downloadImage(String url) {
        final String imageURL = url;
        Thread dI = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL newurl = new URL(imageURL);
                    Bitmap image = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                    presenter.onImageLoaded(image);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    presenter.onError();
                } catch (IOException e) {
                    e.printStackTrace();
                    presenter.onError();
                }
            }
        });
        dI.start();
    }
}
