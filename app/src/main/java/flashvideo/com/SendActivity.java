package flashvideo.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Method;

public class SendActivity extends AppCompatActivity {


    private WifiP2pManager wifiManager;
    private WifiP2pManager.Channel wifichannel;
    private BroadcastReceiver wifiServerReceiver;
    private WifiManager mWifiManager;
    private IntentFilter wifiServerReceiverIntentFilter;


    private ImageButton broadcast, receive;
    private FloatingActionButton closeFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_activity);
        initialiseVariables();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);


    }


    public void initialiseVariables() {


        broadcast = (ImageButton) findViewById(R.id.send_video);
        broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHotspot("streak share", "");
            }
        });


        receive = (ImageButton) findViewById(R.id.receive_video);


        mWifiManager = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);


        wifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

    }

    public void createHotspot() {
        wifichannel = wifiManager.initialize(this, getMainLooper(), null);

        wifiServerReceiver = new WiFiServerBroadcastReceiver(wifiManager, wifichannel, this);

        wifiServerReceiverIntentFilter = new IntentFilter();
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        registerReceiver(wifiServerReceiver, wifiServerReceiverIntentFilter);
    }


    public void createGroupNow() {

        wifiManager.createGroup(wifichannel, new WifiP2pManager.ActionListener() {
            public void onSuccess() {
                setServerStatus("WiFi Group creation successful");

                //Group creation successful
            }

            public void onFailure(int reason) {
                setServerStatus("WiFi Group creation failed");

                //Group creation failed

            }
        });
    }

    public void setServerStatus(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setServerWifiStatus(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean setHotspot(String SSID, String password) {


        Method[] methods = mWifiManager.getClass().getDeclaredMethods();

        for (Method method : methods) {


            if (method.getName().equals("setWifiApEnabled")) {
                WifiConfiguration netConfig = new WifiConfiguration();
                if (password == "") {
                    netConfig.SSID = SSID;
                    netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);

                    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                    netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                } else {
                    netConfig.SSID = SSID;
                    netConfig.preSharedKey = password;
                    netConfig.hiddenSSID = true;
                    netConfig.status = WifiConfiguration.Status.ENABLED;
                    netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                    netConfig.allowedPairwiseCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                    netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                }
                try {
                    method.invoke(mWifiManager, netConfig, true);
                    mWifiManager.saveConfiguration();

                } catch (Exception e) {
                    Log.d("wifi-create", "" + e.getMessage());

                }
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
