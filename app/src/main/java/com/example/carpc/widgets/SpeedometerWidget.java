package com.example.carpc.widgets;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;


public class SpeedometerWidget extends Fragment {

    public static TextView speedTextView;
    public static int speedToSet = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.speedometer_widget, null);
        speedTextView = v.findViewById(R.id.speedDashboard);
        setRetainInstance(true);
        return v;
    }

    public void updateSpeed(final Integer currentSpeed) {
        if (speedToSet <= currentSpeed) {
            speedToSet += 1;
            speedTextView.setText(String.valueOf(speedToSet));
        }
        if (speedToSet > currentSpeed) {
            speedToSet -= 1;
            speedTextView.setText(String.valueOf(speedToSet));
        }
    }
}
