package com.hsh.duensina.museumsfuehrer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hsh.duensina.museumsfuehrer.view.IMuseumGuideStart;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideMainMenuFragment;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideSettingsFragment;
import com.hsh.duensina.museumsfuehrer.view.MuseumGuideStartFragment;

/**
 * This class represents the entry point of the app after the LaunchScreen (MuseumGuideLaunchScreenActivity)
 *
 */
public class MuseumGuideStartActivity extends AppCompatActivity implements IMuseumGuideStart {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static Context a = null;
    private static Activity act = null;
    private MuseumGuideStartFragment startFragment;
    private float y1,y2;
    private String[] drawerOptions;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
    private String activityTitle;
    static final int MIN_DISTANCE = 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_guide_start);

        drawerOptions = new String[]{"Einstellungen", "Meine gespeicherten Daten", "FAQ"};
        drawerLayout = (DrawerLayout) findViewById(R.id.museumguide_drawer_start);
        activityTitle = getTitle().toString();
        drawerListView = (ListView) findViewById(R.id.drawer_listview);
        drawerLayout.requestDisallowInterceptTouchEvent(true);
        drawerListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerOptions));

        // Code for Navigation Drawer, could not be implemented due to lack of time

        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = (String) adapterView.getItemAtPosition(position);
                Fragment drawerFrag = new Fragment();

                switch(item){
                    case "Einstellungen":
                       drawerFrag = new MuseumGuideSettingsFragment();
                        break;
                }

                changeFragment(drawerFrag);
            }
        });

        if (findViewById(R.id.fragmentContainer) != null) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            // Ask the user to enable bluetooth on his device
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }

            startFragment = new MuseumGuideStartFragment();
            // embed the inital Fragment into this Activity
            changeFragment(startFragment);
        }

        // Request permission for coarse location. Necessary for Android 6 and above to scan beacons (because of the beacon library)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Diese App benötigt Zugriff auf Ihren Standort.");
                builder.setMessage("Bitte geben Sie der App die Berechtigung, um Beacons scannen zu können.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }

        a = this.getApplicationContext();
        act = this;
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permissions", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Eingeschränkte Benutzbarkeit der App");
                    builder.setMessage("Ohne Zugriff auf Ihren Standort können keine Beacons gescannt werden.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    /**
     * Returns the Application Context.
     * @return Context
     */
    public static Context getContext(){
        return a;
    }

    private void setupDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult (int requestCode,int resultCode,Intent data){
        // if the Bluetooth Permissions are denied (resultCode = RESULT_CANCELED),
        // quit the app
        if(resultCode == 0){
            finish();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    public static Activity getActivity(){
        return act;
    }


    private void changeFragment(Fragment f){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentContainer, f, "Start");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }
}


