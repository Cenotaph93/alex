package com.hsh.duensina.museumsfuehrer.model;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.hsh.duensina.museumsfuehrer.MuseumGuideStartActivity;
import com.hsh.duensina.museumsfuehrer.presenter.MuseumGuidePresenter;
import com.hsh.duensina.museumsfuehrer.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alex on 25.05.16.
 */
public class SendInitialContextModel implements ISendInitialContextModel {

    @Override
    public void sendInitialContext(final String age, final ArrayList<String> infoTypes) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String userInput = getUserInputAsJSON(age, infoTypes);
                    URL restURL = new URL(Config.REST_URL);
                    HttpURLConnection con = (HttpURLConnection)restURL.openConnection();
                    con.setDoOutput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestMethod("POST");
                    con.connect();

                    OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                    osw.write(userInput);
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
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setPresenter(MuseumGuidePresenter presenter) {

    }

    public String getUserInputAsJSON(String age, ArrayList<String> infoTypes){

        String userInputString = "";
        JSONObject userInputJSON = new JSONObject();
        JSONArray interestTypesArray = new JSONArray();
        String android_id = Settings.Secure.getString(MuseumGuideStartActivity.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try {
            userInputJSON.accumulate("init","true");
            userInputJSON.accumulate("androidID", android_id);
            userInputJSON.accumulate("age", age);

            for(int i=0; i<infoTypes.size();i++){
                interestTypesArray.put(infoTypes.get(i));
            }

            userInputJSON.accumulate("interestTypes",interestTypesArray);

            userInputString = userInputJSON.toString();

            Log.i("JSON",userInputString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userInputString;
    }
}
