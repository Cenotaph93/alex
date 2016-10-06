package com.hsh.duensina.museumsfuehrer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hsh.duensina.museumsfuehrer.model.SendCurrentContextService;
import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideView;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideRecommendationFragment;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideShowAudioFragment;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideShowImageFragment;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideShowTextFragment;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideShowVideoFragment;
import com.hsh.duensina.museumsfuehrer.viewutil.RecommendationPagerAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class MuseumGuideShowInformationActivity extends FragmentActivity {

    private static Context a;
    private static MuseumGuideShowInformationActivity activity;
    private ViewPager viewPager;
    private RecommendationPagerAdapter adapter;
    private Fragment informationFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("ACTIVITY","onCreate() aufgerufen.");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_museum_guide_show_information);

    }

    @Override
    protected void onStart(){
        Log.i("ACTIVITY","onStart() aufgerufen.");
        super.onStart();

    }

    @Override
    protected void onRestart(){
        Log.i("ACTIVITY","onRestart() aufgerufen.");
        super.onRestart();
    }

    @Override
    protected void onStop(){
        Log.i("ACTIVITY","onStop() aufgerufen.");
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("ACTIVITY","changeFragment() aufgerufen.");
        try {
            changeFragment();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        Log.i("ACTIVITY","onDestroy() aufgerufen.");
        super.onDestroy();

    }

    @Override
    protected void onPause(){
        super.onPause();
        if(!this.isFinishing()){
            if(informationFragment instanceof IMuseumGuideView){
                //((IMuseumGuideView) informationFragment).closeView();
            }
        } else {
            stopService(new Intent(MuseumGuideStartActivity.getContext(),SendCurrentContextService.class));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance){
        super.onSaveInstanceState(savedInstance);
        //getFragmentManager().putFragment(savedInstance, "currentFragment", informationFragment);
    }

    public static Context getContext(){
        return a;
    }

    public static MuseumGuideShowInformationActivity getActivity(){
        return activity;
    }

    @Override
    public void onBackPressed() {
        if(informationFragment instanceof IMuseumGuideView){
            ((IMuseumGuideView) informationFragment).closeView();
        }
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void changeFragment() throws UnsupportedEncodingException {
        Log.i("FRAGMENT","changeFragment aufgerufen");

        Intent i = getIntent();
        final String url = URLDecoder.decode(i.getStringExtra("url"),"UTF-8");
        final String text = URLDecoder.decode(i.getStringExtra("text"),"UTF-8");
        String typeIDString = i.getStringExtra("typeID");
        int typeID = Integer.valueOf(typeIDString);

        String recomSetString = i.getStringExtra("recomSet");
        int recomSet = Integer.valueOf(recomSetString);




        Log.i("FRAGMENT","Type = "+typeID+" URL = "+url);

        a = this.getApplicationContext();
        activity = this;

        Bundle data = new Bundle();
        data.putString("url", url);
        data.putString("text", text);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(findViewById(R.id.fragmentContainerInformation) != null) {

            switch(typeID){
                case 0 :{
                    Log.i("FRAGMENT","Bild wird geladen");
                    informationFragment = new MuseumGuideShowImageFragment();
                    break;
                }
                case 1: {
                    Log.i("FRAGMENT","Audio wird geladen");
                    informationFragment = MuseumGuideShowAudioFragment.newInstance();
                    break;
                }
                case 2: {
                    Log.i("FRAGMENT","Text wird geladen");
                    informationFragment = new MuseumGuideShowTextFragment();
                    break;
                }
                case 3: {
                    Log.i("FRAGMENT","Video wird geladen");
                    informationFragment = MuseumGuideShowVideoFragment.newInstance();
                    break;
                }
            }
            if(recomSet == 1) {
                if(informationFragment instanceof IMuseumGuideView){
                    if(!((IMuseumGuideView) informationFragment).hasStarted()){
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                        dlgAlert.setMessage("Wischen Sie nach rechts, um diese anzuzeigen.");
                        dlgAlert.setTitle("Wir haben Empfehlungen für Sie!");
                        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                }

                viewPager = (ViewPager) findViewById(R.id.pager);
                adapter = new RecommendationPagerAdapter(getFragmentManager());
                viewPager.setAdapter(adapter);

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.i("VIEWPAGER","onpageSelected aufgerufen");
                        viewPager.setCurrentItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                String recomHeader = URLDecoder.decode(i.getStringExtra("recomHeader"), "UTF-8");
                String recomURL = i.getStringExtra("recomURL");
                String recomText = URLDecoder.decode(i.getStringExtra("recomText"), "UTF-8");

                Bundle recomData = new Bundle();
                recomData.putString("recomHeader", recomHeader);
                recomData.putString("recomURL", recomURL);
                recomData.putString("recomText", recomText);

                MuseumGuideRecommendationFragment recommendationFragment = new MuseumGuideRecommendationFragment();

                recommendationFragment.setArguments(recomData);
                informationFragment.setArguments(data);
                adapter.setFragments(informationFragment, recommendationFragment);
                adapter.notifyDataSetChanged();

            } else {
                // load the fragment depending on the information type
                informationFragment.setArguments(data);
                fragmentTransaction.replace(R.id.fragmentContainerInformation, informationFragment, "Start");
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        }
    }
}
