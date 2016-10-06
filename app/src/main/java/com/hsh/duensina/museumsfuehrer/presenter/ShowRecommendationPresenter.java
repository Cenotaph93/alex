package com.hsh.duensina.museumsfuehrer.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.model.IShowRecommendationModel;
import com.hsh.duensina.museumsfuehrer.model.ShowRecommendationModel;
import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideRecommendation;

/**
 * Created by alex on 10.08.16.
 */
public class ShowRecommendationPresenter implements IShowRecommendationPresenter, OnImageDownloadFinishedListener {
    private IMuseumGuideRecommendation view;
    private IShowRecommendationModel model;

    public ShowRecommendationPresenter(IMuseumGuideRecommendation view){
        this.view = view;
        this.model = new ShowRecommendationModel();
        model.setPresenter(this);
    }

    @Override
    public void setRecommendation(String header, String url, String text) {
        setRecomHeader(header);
        setRecomText(text);
        model.downloadImage(url);
    }

    @Override
    public void setRecomHeader(final String text) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setRecomHeader(text);
            }
        });
    }

    @Override
    public void setRecomText(final String text) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setRecomText(text);
            }
        });
    }

    @Override
    public void onImageLoaded(final Bitmap bmp) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setRecomImageView(bmp);
            }
        });
    }

    @Override
    public void onError() {

    }
}
