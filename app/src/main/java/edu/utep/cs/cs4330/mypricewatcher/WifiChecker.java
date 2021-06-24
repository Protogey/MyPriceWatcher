package edu.utep.cs.cs4330.mypricewatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.widget.Toast;

//checks for wifi, if not sends to settings.
public class WifiChecker {
    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            if(wifiStateExtra == WifiManager.WIFI_STATE_ENABLED){

                Toast.makeText(context,"wifi is enabled", Toast.LENGTH_SHORT).show();
            }
            if(wifiStateExtra == WifiManager.WIFI_STATE_DISABLED){
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                Toast.makeText(context,"wifi is disabled", Toast.LENGTH_SHORT).show();
            }
        }
    };
}