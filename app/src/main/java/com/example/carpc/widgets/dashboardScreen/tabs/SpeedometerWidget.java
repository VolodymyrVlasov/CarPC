package com.example.carpc.widgets.dashboardScreen.tabs;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carpc.R;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.AppConstants;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.dashboardScreen.AbstractDashboardWidget;
import com.example.carpc.widgets.settingsScreen.SettingsWidget;

public class SpeedometerWidget extends AbstractDashboardWidget {
    private int speedToSet = 0;
    public TextView speedTextView;
    public TextView infoTextView;

    private androidx.fragment.app.FragmentTransaction fTrans;
    private SettingsWidget settingsWidget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.widget_speedometer, null);
        speedTextView = v.findViewById(R.id.speedDashboard);
        infoTextView = v.findViewById(R.id.info);
        settingsWidget = new SettingsWidget();

        setRetainInstance(true);
        return v;
    }


    @Override
    public void updateUI(DataParser data) {
        if (speedToSet <= data.getSpeed()) speedToSet += 1;
        if (speedToSet > data.getSpeed()) speedToSet -= 1;

        speedTextView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    speedTextView.setText(String.valueOf(speedToSet));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
