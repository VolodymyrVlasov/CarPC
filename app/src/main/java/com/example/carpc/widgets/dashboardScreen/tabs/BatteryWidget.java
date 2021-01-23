package com.example.carpc.widgets.dashboardScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.utils.plotter.DrawView;

public class BatteryWidget extends Fragment {
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
        rangeTextView.post(new Runnable() {
            @Override
            public void run() {
                rangeTextView.setText(String.valueOf(batteryRange));
            }
        });
        tempOutside.post(new Runnable() {
            @Override
            public void run() {
                tempOutside.setText(String.valueOf(firstTempSensor));
            }
        });
        batteryIcon.post(new Runnable() {
            @Override
            public void run() {
                batteryIcon.updateCapacity(batteryCapacity);
            }
        });
    }
}