package com.example.carpc.widgets.dashboardScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.utils.DataParser;
import com.example.carpc.utils.plotter.DrawView;
import com.example.carpc.widgets.dashboardScreen.AbstractDashboardWidget;

public class BatteryWidget extends AbstractDashboardWidget {
    DrawView batteryIcon;
    LinearLayout surfaceView;
    TextView rangeTextView;
    TextView tempOutside;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battery_widget, container, false);
        surfaceView = (LinearLayout) v.findViewById(R.id.for_battery_canvas);
        surfaceView.addView(batteryIcon = new DrawView(getContext()));
        rangeTextView = v.findViewById(R.id.range);
        tempOutside = v.findViewById(R.id.temp_outside);
        setRetainInstance(true);
        return v;
    }

    public void updateWidgetUI(final Double batteryRange, final Double firstTempSensor, final Double batteryCapacity) {

    }

    @Override
    public void updateUI(final DataParser data) {
        rangeTextView.post(new Runnable() {
            @Override
            public void run() {
                rangeTextView.setText(String.valueOf(data.getRange()));
                tempOutside.setText(String.valueOf(data.getTempSensorValue(1)));
                batteryIcon.updateCapacity(data.getBatteryCapacity());
            }
        });
    }
}