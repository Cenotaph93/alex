package com.hsh.duensina.museumsfuehrer.view;

import android.graphics.Bitmap;

/**
 * Created by alex on 10.08.16.
 */
public interface IMuseumGuideRecommendation {
    void setPresenter();
    void setRecomHeader(String text);
    void setRecomImageView(Bitmap bmp);
    void setRecomText(String text);
    void setRecommendation(String header, String url, String text);
}
