package com.hsh.duensina.museumsfuehrer.viewutil;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideView;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideRecommendationFragment;

/**
 * Created by alex on 08.08.16.
 */
public class RecommendationPagerAdapter extends FragmentPagerAdapter{
    private IMuseumGuideView view;
    private MuseumGuideRecommendationFragment recommendationFragment;

    public void setFragments(android.app.Fragment view, MuseumGuideRecommendationFragment recommendationFragment){
        if (view instanceof IMuseumGuideView)
            this.view = (IMuseumGuideView) view;
        this.recommendationFragment = recommendationFragment;
    }

    public RecommendationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: { return (Fragment) view; }
            case 1: { return recommendationFragment; }
            default: { return (Fragment) view; }
        }
    }

    @Override
    public int getCount() {
        if(recommendationFragment == null)
            return 1;
        return 2;
    }
}
