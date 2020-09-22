package com.example.carpc.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;

public class IconStatusRightWidget extends Fragment {
    public ImageView rightLight, headLight, upHeadLight, fogLight, reverseSelector, driveSelector;
    public static final int VOLTAGE_ON = 12;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.icon_status_right_widget, null);

        rightLight = v.findViewById(R.id.right_light);
        headLight = v.findViewById(R.id.head_light);
        upHeadLight = v.findViewById(R.id.up_head_light);
        fogLight = v.findViewById(R.id.fog_light);
        reverseSelector = v.findViewById(R.id.reverse);
        driveSelector = v.findViewById(R.id.drive);
        setRetainInstance(true);
        return v;
    }

    public void updateParameters(String inputsValue) {
        String[] values = inputsValue.split(":");
        Double[] voltageValues = new Double[6];
        int i = 0;
        for (String s : values) {
            voltageValues[i] = Double.parseDouble(s) / 10;
            i++;
        }
        rightLight.setVisibility(voltageValues[0] > VOLTAGE_ON ? View.VISIBLE : View.INVISIBLE);
        headLight.setVisibility(voltageValues[1] > VOLTAGE_ON ? View.VISIBLE : View.INVISIBLE);
        upHeadLight.setVisibility(voltageValues[2] > VOLTAGE_ON ? View.VISIBLE : View.INVISIBLE);
        fogLight.setVisibility(voltageValues[3] > VOLTAGE_ON ? View.VISIBLE : View.INVISIBLE);
        reverseSelector.setVisibility(voltageValues[4] > VOLTAGE_ON ? View.VISIBLE : View.INVISIBLE);
        driveSelector.setVisibility(voltageValues[5] > VOLTAGE_ON ? View.VISIBLE : View.INVISIBLE);
    }
}
