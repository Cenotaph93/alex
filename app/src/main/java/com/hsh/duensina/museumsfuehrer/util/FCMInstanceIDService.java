package com.hsh.duensina.museumsfuehrer.util;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.hsh.duensina.museumsfuehrer.model.SendCurrentContextService;

/**
 * Created by alex on 11.07.16.
 */
public class FCMInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token", "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        SendCurrentContextService.updateRegistrationToken(refreshedToken);
    }
}
