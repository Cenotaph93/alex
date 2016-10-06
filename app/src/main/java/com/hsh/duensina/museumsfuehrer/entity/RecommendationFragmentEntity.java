package com.hsh.duensina.museumsfuehrer.entity;

import android.graphics.drawable.Drawable;

import com.hsh.duensina.museumsfuehrer.presenter.IShowRecommendationPresenter;

/**
 * Created by alex on 10.08.16.
 */
public class RecommendationFragmentEntity {
    private static IShowRecommendationPresenter presenter;
    private static String recomHeader;
    private static Drawable recomImg;
    private static String recomText;

    public static void saveStatus(IShowRecommendationPresenter p, String rH, Drawable rI, String rT){
        setPresenter(p);
        setRecomHeader(rH);
        setRecomImg(rI);
        setRecomText(rT);
    }

    public static void resetStatus(){
        presenter = null;
        recomHeader = null;
        recomImg = null;
        recomText = null;
    }

    public static IShowRecommendationPresenter getPresenter() {
        return presenter;
    }

    public static void setPresenter(IShowRecommendationPresenter presenter) {
        RecommendationFragmentEntity.presenter = presenter;
    }

    public static String getRecomHeader() {
        return recomHeader;
    }

    public static void setRecomHeader(String recomHeader) {
        RecommendationFragmentEntity.recomHeader = recomHeader;
    }

    public static Drawable getRecomImg() {
        return recomImg;
    }

    public static void setRecomImg(Drawable recomImg) {
        RecommendationFragmentEntity.recomImg = recomImg;
    }

    public static String getRecomText() {
        return recomText;
    }

    public static void setRecomText(String recomText) {
        RecommendationFragmentEntity.recomText = recomText;
    }
}
