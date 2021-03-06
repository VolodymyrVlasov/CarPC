package com.example.carpc.widgets.dashboardScreen.tabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.DataParser;

public class SpeedometerWidget extends Fragment implements TCPClient.TCPClientListener {
    private int speedToSet = 0;

    public TextView speedTextView;

    public SpeedometerWidget() {
        TCPClient.getInstance(getContext()).addNetworkListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.speedometer_widget, null);
        speedTextView = v.findViewById(R.id.speedDashboard);
        setRetainInstance(true);
        return v;
    }

    public void setSpeedText(int speed) {
        if (speedToSet <= speed) speedToSet += 1;
        if (speedToSet > speed) speedToSet -= 1;

        speedTextView.post(new Runnable() {
                               @Override
                               public void run() { speedTextView.setText(String.valueOf(speedToSet));
                               }
                           }
        );
    }

    @Override
    public void OnDataReceive(DataParser data) {
        if (speedToSet <= data.getSpeed()) speedToSet += 1;
        if (speedToSet > data.getSpeed()) speedToSet -= 1;

        speedTextView.post(new Runnable() {
                               @Override
                               public void run() { speedTextView.setText(String.valueOf(speedToSet));
                               }
                           }
        );

    }
}
