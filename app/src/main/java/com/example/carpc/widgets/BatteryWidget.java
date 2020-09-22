package com.example.carpc.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.instruments.DrawView;

public class BatteryWidget extends Fragment {
    DrawView batteryIcon;
    LinearLayout surfaceView;
    TextView range;
    TextView tempOutside;
    Context context;
//    DataParser parser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battery_widget, container, false);
        surfaceView = (LinearLayout) v.findViewById(R.id.for_battery_canvas);
        context = getContext();
       surfaceView.addView(batteryIcon = new DrawView(context));

        range = v.findViewById(R.id.range);
        tempOutside = v.findViewById(R.id.temp_outside);

//        if (getActivity() != null) {
//            parser = (DataParser) MainActivity.getParser();
//        }
        setRetainInstance(true);
        return v;
    }

    @SuppressLint("SetTextI18n")
    public void updateParameters(Double range, Double outsideTemperature, Double capacity) throws NullPointerException {
        this.range.setText((int) Math.round(range) + " km");
        this.tempOutside.setText((int) Math.round(outsideTemperature) / 10 + "°C");
        batteryIcon.updateCapacity(capacity);
    }
}