package com.hsh.duensina.museumsfuehrer.presenter;

import android.graphics.Bitmap;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.model.IShowImageModel;
import com.hsh.duensina.museumsfuehrer.model.ShowImageModel;
import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideShowImage;

/**
 * Created by alex on 13.07.16.
 */
public class ShowImagePresenter implements IShowImagePresenter, OnImageDownloadFinishedListener{
    private IMuseumGuideShowImage view;
    private IShowImageModel model;

    public ShowImagePresenter(IMuseumGuideShowImage view){
        this.view = view;
        model = new ShowImageModel();
        model.setPresenter(this);
    }

    @Override
    public void showInformation(String url, String text) {
        view.setTextView(text);
        model.downloadImage(url);
    }

    @Override
    public void onImageLoaded(final Bitmap bmp) {
        MuseumGuideShowInformationActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setImageView(bmp);
            }
        });
    }

    @Override
    public void onError() {

    }
}
