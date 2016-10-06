package com.hsh.duensina.museumsfuehrer.entity;

import android.graphics.drawable.Drawable;

import com.hsh.duensina.museumsfuehrer.presenter.IShowImagePresenter;

/**
 * Created by alex on 31.07.16.
 */
public class ImageFragmentEntity {
    private static IShowImagePresenter presenter;
    private static Drawable image;
    private static String imageText;

    public static void saveStatus(IShowImagePresenter pres, Drawable img, String imgText){
        presenter = pres;
        image = img;
        imageText = imgText;
    }

    public static void resetStatus(){
        presenter = null;
        image = null;
        imageText = null;
    }

    public static IShowImagePresenter getPresenter() {
        return presenter;
    }

    public static void setPresenter(IShowImagePresenter presenter) {
        ImageFragmentEntity.presenter = presenter;
    }

    public static Drawable getImage() {
        return image;
    }

    public static void setImage(Drawable image) {
        ImageFragmentEntity.image = image;
    }

    public static String getImageText() {
        return imageText;
    }

    public static void setImageText(String imageText) {
        ImageFragmentEntity.imageText = imageText;
    }
}
