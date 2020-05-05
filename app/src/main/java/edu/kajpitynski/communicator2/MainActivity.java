package edu.kajpitynski.communicator2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
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
        implements WiFiServiceFragment.OnListFragmentInteractionListener,
        WifiP2pManager.ConnectionInfoListener, Handler.Callback {

    private static final String TAG = "MainActivity";

    private static final String SERVICE_NAME = "_wificommunicator";
    private static final String SERVICE_TYPE = "_presence._tcp";
    private static final int SERVER_PORT = 8888;

    public static final int MESSAGE_READ = 0x400 + 1;
    public static final int MY_HANDLE = 0x400 + 2;
    private final Handler handler = new Handler(this);

    private WifiP2pManager manager;

    private final HashMap<String, String> buddies = new HashMap<>();

    private final IntentFilter intentFilter = new IntentFilter();
    private Channel channel;
    private BroadcastReceiver receiver;
    private WifiP2pDnsSdServiceRequest serviceRequest;

    private TextView statusText;

    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);

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
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WiFiServiceFragment fragment = WiFiServiceFragment.newInstance();
        fragmentTransaction.add(R.id.fragment, fragment, "services");
        fragmentTransaction.commit();

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
                appendStatus("Local service added");
            }

            @Override
            public void onFailure(int reason) {
                appendStatus("Local service not added");
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

                Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("services");
                if (fragment1 != null) {
                    WiFiServiceFragment fragment = (WiFiServiceFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.fragment);
                    Log.d(TAG, String.format("%s %s", instanceName, registrationType));
                    MyWiFiServiceRecyclerViewAdapter adapter = fragment.getAdapter();
                    adapter.add(srcDevice);
                    adapter.notifyDataSetChanged();
                }
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
        Log.d(TAG, item.toString());
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = item.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        if (serviceRequest != null) {
            manager.removeServiceRequest(channel, serviceRequest, new ActionListener() {
                @Override
                public void onSuccess() {
                    serviceRequest = null;
                }

                @Override
                public void onFailure(int reason) {

                }
            });
        }

        manager.connect(channel, config, new ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Connected");
            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    /**
     * The requested connection info is available
     *
     * @param info Wi-Fi p2p connection info
     */
    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        Log.d(TAG, "Connection info available");
        ChatManager manager = null;
        if (info.isGroupOwner) {
            Log.d(TAG, "Group owner");
            try {
                new ServerSocketHandler(SERVER_PORT, handler).start();
                appendStatus("Server done");
            } catch (IOException e) {
                e.printStackTrace();
                appendStatus("Server failed");
            }
        } else {
            Log.d(TAG, "Not owner");
            ClientSocketHandler clientSocketHandler =
                    new ClientSocketHandler(handler, info.groupOwnerAddress, SERVER_PORT);
            clientSocketHandler.start();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        chatFragment = ChatFragment.newInstance();
        transaction.replace(R.id.fragment, chatFragment, "chat");
//        transaction.remove(getSupportFragmentManager().findFragmentById(R.id.fragment));
//        transaction.add(R.id.fragment, chatFragment, null);

        transaction.commit();
    }

    /**
     * @param msg A {@link Message Message} object
     * @return True if no further handling is desired
     */
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                Toast.makeText(this, readMessage, Toast.LENGTH_LONG).show();
                break;
            case MY_HANDLE:
                Object obj = msg.obj;
                chatFragment.setChatManager((ChatManager) obj);
                break;
        }
        return true;
    }

    protected void appendStatus(String status) {
        String current = statusText.getText().toString();
        statusText.setText(current + "\n" + status);
    }
}
