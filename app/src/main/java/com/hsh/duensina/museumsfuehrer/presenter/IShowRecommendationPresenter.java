package com.hsh.duensina.museumsfuehrer.presenter;

/**
 * Created by alex on 10.08.16.
 */
public interface IShowRecommendationPresenter {

    void setRecommendation(String header, String url, String text);
    void setRecomHeader(String text);
    void setRecomText(String text);
}
