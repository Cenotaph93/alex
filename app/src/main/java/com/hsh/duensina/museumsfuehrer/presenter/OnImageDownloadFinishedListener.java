package com.hsh.duensina.museumsfuehrer.presenter;

import android.graphics.Bitmap;

/**
 * Created by alex on 13.07.16.
 */
public interface OnImageDownloadFinishedListener {
    public void onImageLoaded(Bitmap bmp);
    public void onError();
}
