package com.example.carpc.settings.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;

import java.io.IOException;

public class ConnectionTab extends Fragment implements View.OnClickListener {
    private EditText serverAddress, serverPort;
    private TextView myNetworkAddress;
    private Button btnConnect, btnDisconnect;
    String address = "192.168.1.7";
    int port = 8000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.connection_tab, null);
        serverAddress = v.findViewById(R.id.server_address);
        serverPort = v.findViewById(R.id.server_port);
        myNetworkAddress = v.findViewById(R.id.my_server_address);
        btnConnect = v.findViewById(R.id.btnConnect);
        btnDisconnect = v.findViewById(R.id.btnDisconnect);
        address = serverAddress.getText().toString();
        port = Integer.parseInt(serverPort.getText().toString());
        if (MainActivity.getConnectionState()) {
            WifiManager wifiMan = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            int ipAddress = wifiInf.getIpAddress();
            @SuppressLint("DefaultLocale") String ip = String.format("%d.%d.%d.%d",
                    (ipAddress & 0xff),
                    (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff),
                    (ipAddress >> 24 & 0xff));
            myNetworkAddress.setText(ip);
        }
        setConnectionStateIndicator();
        setRetainInstance(true);
        System.out.println("Create CONNECTION TAB");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnConnect.setOnClickListener(this);
        btnDisconnect.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy CONNECTION TAB");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:
                try {
                    updateConnectionParams();
                    setConnectionStateIndicator();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnDisconnect:
                try {
                    closeConnection();
                    setConnectionStateIndicator();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public String getServerAddress() {
        return address;
    }

    public Integer getServerPort() {
        return port;
    }


    public void updateConnectionParams() throws IOException, InterruptedException {
        //((MainActivity) (getActivity())).closeCommunication();
        ((MainActivity) (getActivity())).startCommunicationWithNewParams(
                serverAddress.getText().toString(),
                Integer.parseInt(serverPort.getText().toString()));
    }

    public void closeConnection() throws IOException, InterruptedException {
        ((MainActivity) getActivity()).closeCommunication();
    }

    public void setConnectionStateIndicator() {
        boolean flag = true;
        long time = SystemClock.uptimeMillis();

        while (flag) {
            if (SystemClock.uptimeMillis() >= time + 200) {
                if (MainActivity.getConnectionState()) {
                    btnConnect.setTextColor(Color.argb(255, 3, 218, 197));
                    btnConnect.setBackground(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.transparent_bg_bordered_button_active, null));
                    btnConnect.setText("CONNECTED");
                    btnConnect.setActivated(false);
                } else {
                    btnConnect.setTextColor(Color.WHITE);
                    btnConnect.setBackground(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.transparent_bg_bordered_button, null));
                    btnConnect.setText("CONNECT");
                    btnConnect.setActivated(true);
                }
                flag = false;
            }
        }
    }
}