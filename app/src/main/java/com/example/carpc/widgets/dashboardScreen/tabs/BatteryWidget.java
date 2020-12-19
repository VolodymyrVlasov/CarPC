package com.example.carpc.widgets.dashboardScreen.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;
import com.example.carpc.utils.DataParser;
import com.example.carpc.utils.plotter.DrawView;

import java.util.Timer;
import java.util.TimerTask;

public class BatteryWidget extends Fragment {
    DrawView batteryIcon;
    LinearLayout surfaceView;
    TextView range;
    TextView tempOutside;
    Context context;
    DataParser parser;
    public final String TAG = "batteryWidget";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battery_widget, container, false);
        surfaceView = (LinearLayout) v.findViewById(R.id.for_battery_canvas);
        context = getContext();
        surfaceView.addView(batteryIcon = new DrawView(context));
        range = v.findViewById(R.id.range);
        tempOutside = v.findViewById(R.id.temp_outside);
        parser = (DataParser) MainActivity.getParser();
        update();
        setRetainInstance(true);
        return v;
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            range.setText((int) Math.round(parser.getRange()) + " km");
                            tempOutside.setText(Math.round(parser.getFirstTempSensorValue()) / 10 + "°C");
                            batteryIcon.updateCapacity(parser.getBatteryCapacity());
                        }
                    });
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 50L;
        long period = 10L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
}