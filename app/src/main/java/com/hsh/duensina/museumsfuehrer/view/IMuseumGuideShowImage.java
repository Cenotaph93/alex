package com.hsh.duensina.museumsfuehrer.view;

import android.app.Fragment;
import android.graphics.Bitmap;

/**
 * Created by alex on 13.07.16.
 */
public interface IMuseumGuideShowImage {
    public void setTextView(String text);
    public void setPresenter();
    public void setImageView(Bitmap bmp);

    void showInformation(String url, String text);
}
