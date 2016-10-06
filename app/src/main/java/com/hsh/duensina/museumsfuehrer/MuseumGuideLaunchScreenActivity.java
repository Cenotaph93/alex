package com.hsh.duensina.museumsfuehrer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MuseumGuideLaunchScreenActivity extends Activity {
    int SPLASH_DISPLAY_LENGTH = 1200;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_museum_guide_launch_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(MuseumGuideLaunchScreenActivity.this,MuseumGuideStartActivity.class);
                MuseumGuideLaunchScreenActivity.this.startActivity(mainIntent);
                MuseumGuideLaunchScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }




}
