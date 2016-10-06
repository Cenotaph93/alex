package com.hsh.duensina.museumsfuehrer.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.hsh.duensina.museumsfuehrer.MuseumGuideStartActivity;
import com.hsh.duensina.museumsfuehrer.util.Config;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RunningAverageRssiFilter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class SendCurrentContextService extends Service implements BeaconConsumer, Runnable, ISendCurrentContext {
    private BeaconManager beaconManager;
    private static ArrayList<Beacon> beacons;
    private Thread t;
    private static String fcmRegistrationToken;
    private static Beacon currentClosestBeacon;

    @Override
    public void onCreate(){
        Log.i("SERVICE","Service wurde gestartet");
        super.onCreate();

        if(MuseumGuideStartActivity.getContext() != null) {
            beaconManager = BeaconManager.getInstanceForApplication(MuseumGuideStartActivity.getContext());
            beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

            beaconManager.bind(this);
            RunningAverageRssiFilter.setSampleExpirationMilliseconds(30000l);
            t = new Thread(this);
            t.start();
        }
    }

    @Override
    public void onDestroy(){
        endService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        return START_NOT_STICKY;
    }

    public SendCurrentContextService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i("Beacon","In Region eingetreten");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i("Beacon","Aus Region raus");
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> nearBeacons, Region region) {
                if (nearBeacons.size() > 0) {
                    beacons = (ArrayList<Beacon>) nearBeacons;
                    setCurrentBeacon(beacons);
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            sendCurrentContext();
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void setCurrentBeacon(ArrayList<Beacon> beacons){
            if (beacons.size() > 0) {
                Beacon minBeacon = beacons.get(0);
                for (Beacon b : beacons) {
                    if (b.getDistance() < minBeacon.getDistance()-(minBeacon.getDistance()*0.1)) {
                        minBeacon = b;
                    }
                }

                currentClosestBeacon = minBeacon;
            } else {

            }
    }


    /**
     * This method returns the current nearest beacon, if there are any beacons near.
     * @return Beacon
     */
    public static Beacon getNearestBeacon(){
        return currentClosestBeacon;
    }

    @Override
    public void sendCurrentContext(){
        if(getNearestBeacon() != null) {
            try {
                String curCon = getCurConAsJSON(getNearestBeacon());
                URL restURL = new URL(Config.REST_URL);
                HttpURLConnection con = (HttpURLConnection) restURL.openConnection();
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("PUT");
                con.connect();

                OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                osw.write(curCon);
                osw.close();

                int result = con.getResponseCode();
                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getCurConAsJSON(Beacon b){

        String curConString = "";
        JSONObject curConJSON = new JSONObject();
        String android_id = Settings.Secure.getString(MuseumGuideStartActivity.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try {
            curConJSON.accumulate("androidID", android_id);
            curConJSON.accumulate("beaconName", b.getBluetoothName());
            curConJSON.accumulate("dist", b.getDistance());
            curConJSON.accumulate("fcmToken", FirebaseInstanceId.getInstance().getToken());

            curConString = curConJSON.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("JSON",curConString);

        return curConString;
    }

    public static void updateRegistrationToken(String token){
        fcmRegistrationToken = token;
    }

    public void endService(){
        Log.i("SERVICE","Service wurde beendet.");
        beaconManager.unbind(this);
        t.interrupt();
        super.onDestroy();
        stopSelf();
    }
}
