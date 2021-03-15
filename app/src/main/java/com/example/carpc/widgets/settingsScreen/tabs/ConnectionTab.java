package com.example.carpc.widgets.settingsScreen.tabs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.example.carpc.models.DataPrefs;
import com.example.carpc.network.TCPClient;

import java.util.Objects;

public class ConnectionTab extends Fragment implements View.OnClickListener {
    public static boolean isVisible = false;
    private EditText serverAddress, serverPort;
    private TextView myNetworkAddress;
    private static Button btnConnect;
    private Button btnDisconnect;
    private DataPrefs dataPrefs;

    private static TCPClient tcpClient;


    public ConnectionTab() {
        this.tcpClient = TCPClient.getInstance(getContext());
        this.dataPrefs = DataPrefs.getInstance(getContext());
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

        // set values
        serverAddress.setText(dataPrefs.getIP());
        serverPort.setText(String.valueOf(dataPrefs.getPort()));

        if (tcpClient.isConnected()) {
            String localIpAddress = tcpClient.getLocalNetworkAddress(getContext());
            myNetworkAddress.setText(localIpAddress);
            setConnectionStateIndicator(true);
        }

        /**
         *  messageToSend.setOnKeyListener(new View.OnKeyListener() {
         *
         *             @Override
         *             public boolean onKey(View v, int keyCode, KeyEvent event) {
         *                 if (keyCode == KeyEvent.KEYCODE_ENTER) {
         *                     String temp = messageToSend.getText().toString();
         *                     if (!temp.equals("")) sendMessage(temp);
         *                 }
         *                 return false;
         *             }
         *         });
         */

        serverPort.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!serverAddress.getText().toString().equals("") && !serverPort.getText().toString().equals("")) {
                        tcpClient.disconnect();
                        MainActivity.hideKeyboard((Activity) Objects.requireNonNull(getContext()));
                        setConnectBtnState(false);
                        updateConnectionParams(serverAddress.getText().toString(), Integer.parseInt(serverPort.getText().toString()));
                    }
                }

                return false;
            }
        });
        setRetainInstance(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnConnect.setOnClickListener(this);
        btnDisconnect.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisible = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:
                tcpClient.disconnect();
                MainActivity.hideKeyboard((Activity) Objects.requireNonNull(getContext()));
                setConnectBtnState(false);
                updateConnectionParams(serverAddress.getText().toString(), Integer.parseInt(serverPort.getText().toString()));
                break;
            case R.id.btnDisconnect:
                tcpClient.disconnect();
                break;
        }
    }

    public void updateConnectionParams(String ip, int port) {
        dataPrefs.setIP(ip);
        dataPrefs.setPort(port);
        // TODO: add commit to data prefs
        tcpClient.createConnection(ip, port, false);
    }


    public static void setConnectionStateIndicator(boolean stateIndicator) {
        setConnectBtnState(stateIndicator);
    }

    private static void setConnectBtnState(boolean state) {
        if (state) {
            btnConnect.post(new Runnable() {
                @Override
                public void run() {
                    btnConnect.setTextColor(Color.argb(255, 3, 218, 197));
                    btnConnect.setBackground(ResourcesCompat.getDrawable(
                            btnConnect.getContext().getResources(),
                            R.drawable.transparent_bg_bordered_button_active, null));
                    btnConnect.setText("CONNECTED");
                    btnConnect.setActivated(false);
                }
            });
        } else {
            btnConnect.post(new Runnable() {
                @Override
                public void run() {
                    btnConnect.setTextColor(Color.WHITE);
                    btnConnect.setBackground(ResourcesCompat.getDrawable(
                            btnConnect.getContext().getResources(),
                            R.drawable.transparent_bg_bordered_button, null));
                    btnConnect.setText("CONNECT");
                    btnConnect.setActivated(true);
                }
            });
        }
    }
}
