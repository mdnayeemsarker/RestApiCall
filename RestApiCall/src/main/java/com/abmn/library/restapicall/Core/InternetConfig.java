package com.abmn.library.restapicall.Core;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetConfig {
    Activity activity;
    public InternetConfig(Activity activity) {
        try {
            this.activity = activity;
        } catch (Exception e) {
            Log.d("internetCheck", e.getMessage());
        }
    }

    public Boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiNetwork != null && wifiNetwork.isConnected()) || (mobileNetwork != null && mobileNetwork.isConnected());
    }

}
