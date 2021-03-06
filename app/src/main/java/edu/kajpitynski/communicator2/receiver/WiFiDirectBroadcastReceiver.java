package edu.kajpitynski.communicator2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import edu.kajpitynski.communicator2.activity.MessageActivity;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastReceiver";

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private MessageActivity activity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager,
                                       WifiP2pManager.Channel channel, MessageActivity activity) {
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (manager == null) {
                return;
            }

            NetworkInfo networkInfo = intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) {
                manager.requestConnectionInfo(channel, activity);
            }
//            manager.requestNetworkInfo(channel, new WifiP2pManager.NetworkInfoListener() {
//                @Override
//                public void onNetworkInfoAvailable(@NonNull NetworkInfo networkInfo) {
//                    if (networkInfo.isConnected()) {
//                        manager.requestConnectionInfo(channel, activity);
//                    }
//                }
//            });
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            WifiP2pDevice device = intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.d(TAG, "Device status - " + device.status);
        }
    }
}
