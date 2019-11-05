package com.example.webscrappingjobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class ConnectionR extends BroadcastReceiver {
    Context con;
    @Override
    public void onReceive(Context context, Intent intent) {
        con = context;
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            // Do something

            Log.d("Network Available ", "Flag No 1");
        }
      boolean state =  checkactivation() || checkactivation2();
        if(state == true)
        {
            Toast.makeText(context," متصل بالانترن ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"غير متصل بالانترن ", Toast.LENGTH_SHORT).show();
        }
    }
    boolean checkactivation()
    {
        ConnectivityManager connMgr =
                (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d("DEBUG", "Wifi connected: " + isWifiConn);
        Log.d("DEBUG", "Mobile connected: " + isMobileConn);
        return  isWifiConn;
    }
    boolean checkactivation2()
    {
        ConnectivityManager connMgr =
                (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d("DEBUG", "Wifi connected: " + isWifiConn);
        Log.d("DEBUG", "Mobile connected: " + isMobileConn);
        return  isMobileConn;
    }

}
