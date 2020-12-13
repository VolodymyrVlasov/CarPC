package com.example.carpc.widgets.settingsScreen.tabs;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.example.carpc.instruments.ClientSocket;

import java.util.Objects;

public class ConnectionTab extends Fragment implements View.OnClickListener {
    private EditText serverAddress, serverPort;
    private TextView myNetworkAddress;
    private ClientSocket socket;
    private Button btnConnect, btnDisconnect;
    String address = "192.168.1.90";
    int port = 8080;

    public ConnectionTab(ClientSocket socket) {
        this.socket = socket;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.connection_tab, null);
        serverAddress = v.findViewById(R.id.server_address);
        serverPort = v.findViewById(R.id.server_port);
        myNetworkAddress = v.findViewById(R.id.my_server_address);
        btnConnect = v.findViewById(R.id.btnConnect);
        btnDisconnect = v.findViewById(R.id.btnDisconnect);
        if (socket.isConnected()) {
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:
                updateConnectionParams(
                        address = serverAddress.getText().toString(),
                        port = Integer.parseInt(serverPort.getText().toString()));
                setConnectionStateIndicator();
                MainActivity.hideKeyboard((Activity) Objects.requireNonNull(getContext()));
                break;
            case R.id.btnDisconnect:
                socket.disconnect();
                setConnectionStateIndicator();
                break;
        }
    }

    public String getServerAddress() {
        return address;
    }

    public Integer getServerPort() {
        return port;
    }

    public void updateConnectionParams(String ip, int port) {
        socket = new ClientSocket(ip, port, MainActivity.getParser(), false);
    }

    public void setConnectionStateIndicator() {
        boolean flag = true;
        long time = SystemClock.uptimeMillis();

        while (flag) {
            if (SystemClock.uptimeMillis() >= time + 200) {
                if (socket.isConnected()) {
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