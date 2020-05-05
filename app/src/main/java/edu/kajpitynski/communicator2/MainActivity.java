package edu.kajpitynski.communicator2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import static android.net.wifi.p2p.WifiP2pManager.ActionListener;
import static android.net.wifi.p2p.WifiP2pManager.DnsSdServiceResponseListener;
import static android.net.wifi.p2p.WifiP2pManager.DnsSdTxtRecordListener;
import static android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION;
import static android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION;
import static android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION;
import static android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION;

public class MainActivity extends AppCompatActivity
        implements WiFiServiceFragment.OnListFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    private static final String SERVICE_NAME = "_wificommunicator";
    private static final String SERVICE_TYPE = "_presence._tcp";
    private static final int SERVER_PORT = 8888;

    private WifiP2pManager manager;

    private final HashMap<String, String> buddies = new HashMap<>();

    private final IntentFilter intentFilter = new IntentFilter();
    private Channel channel;
    private BroadcastReceiver receiver;
    private WifiP2pDnsSdServiceRequest serviceRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver();

        startRegistration();
        discoverService();
    }

    private void startRegistration() {
        Map<String, String> record = new HashMap<>();
        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", "John Doe" + (int) (Math.random() * 1000));
        record.put("available", "visible");

        WifiP2pDnsSdServiceInfo serviceInfo =
                WifiP2pDnsSdServiceInfo.newInstance(SERVICE_NAME, SERVICE_TYPE,
                        record);
        manager.addLocalService(channel, serviceInfo, new ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Local service added");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "Local service not added");
            }
        });
    }

    private void discoverService() {
        DnsSdTxtRecordListener txtRecordListener = new DnsSdTxtRecordListener() {
            @Override
            public void onDnsSdTxtRecordAvailable(String fullDomainName,
                                                  Map<String, String> txtRecordMap,
                                                  WifiP2pDevice srcDevice) {
                Log.d(TAG, "DnsSdTxtRecord available -" + txtRecordMap.toString());
                buddies.put(srcDevice.deviceAddress, txtRecordMap.get("buddyname"));
            }
        };

        DnsSdServiceResponseListener serviceListener = new DnsSdServiceResponseListener() {
            @Override
            public void onDnsSdServiceAvailable(String instanceName,
                                                String registrationType, WifiP2pDevice srcDevice) {
                srcDevice.deviceName = buddies.containsKey(srcDevice.deviceAddress) ? buddies
                        .get(srcDevice.deviceAddress) : srcDevice.deviceName;

                WiFiServiceFragment fragment = (WiFiServiceFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment);
                MyWiFiServiceRecyclerViewAdapter adapter = fragment.getAdapter();
                adapter.add(srcDevice);
                adapter.notifyDataSetChanged();
            }
        };

        manager.setDnsSdResponseListeners(channel, serviceListener, txtRecordListener);

        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        manager.addServiceRequest(channel, serviceRequest, new ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
        manager.discoverServices(channel, new ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Services discovered successfully");
            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onListFragmentInteraction(WifiP2pDevice item) {

    }
}
