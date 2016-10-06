package com.hsh.duensina.museumsfuehrer.view;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.entity.RecommendationFragmentEntity;
import com.hsh.duensina.museumsfuehrer.presenter.IShowRecommendationPresenter;
import com.hsh.duensina.museumsfuehrer.presenter.ShowRecommendationPresenter;

/**
 * This class represents a Recommendation to a exhibit that was sent to a user
 */
public class MuseumGuideRecommendationFragment extends Fragment implements IMuseumGuideRecommendation, IMuseumGuideView{
    private IShowRecommendationPresenter presenter;
    private TextView recomHeader;
    private ImageView recomImageView;
    private TextView recomText;
    private Animation fadeIn;
    private static boolean hasStarted = false;

    public MuseumGuideRecommendationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_recommendation, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        recomHeader = (TextView) getView().findViewById(R.id.recommendationHeader);
        recomImageView = (ImageView) getView().findViewById(R.id.recommendationImageView);
        recomText = (TextView) getView().findViewById(R.id.recommendationTextView);
        fadeIn = AnimationUtils.loadAnimation(MuseumGuideShowInformationActivity.getContext(),R.anim.fadein);

        if(!hasStarted){
            setPresenter();
            setRecommendation(getArguments().getString("recomHeader"), getArguments().getString("recomURL"), getArguments().getString("recomText"));
            hasStarted = true;
        } else{
            restoreStatus();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        saveStatus();
    }

    @Override
    public void closeView() {
        RecommendationFragmentEntity.resetStatus();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void saveStatus() {
        RecommendationFragmentEntity.saveStatus(presenter, recomHeader.getText().toString(), recomImageView.getDrawable(), recomText.getText().toString());
    }

    @Override
    public void restoreStatus() {
        presenter = RecommendationFragmentEntity.getPresenter();
        recomHeader.setText(RecommendationFragmentEntity.getRecomHeader());
        recomImageView.setImageDrawable(RecommendationFragmentEntity.getRecomImg());
        recomText.setText(RecommendationFragmentEntity.getRecomText());
    }

    @Override
    public boolean hasStarted() {
        return hasStarted;
    }

    @Override
    public void setPresenter() {
        presenter = new ShowRecommendationPresenter(this);
    }

    @Override
    public void setRecomHeader(String text) {
        recomHeader.setText(text);
    }

    @Override
    public void setRecomImageView(Bitmap bmp) {
        recomImageView.startAnimation(fadeIn);
        recomImageView.setImageBitmap(bmp);
    }

    @Override
    public void setRecomText(String text) {
        recomText.setText(text);
    }

    @Override
    public void setRecommendation(String header, String url, String text) {
        presenter.setRecommendation(header, url, text);
    }
}
