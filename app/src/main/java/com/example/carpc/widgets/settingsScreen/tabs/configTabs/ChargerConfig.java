package com.example.carpc.widgets.settingsScreen.tabs.configTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpc.R;

public class ChargerConfig extends Fragment {
    private static final String TAG = "GroupConfig";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_charger_config, container, false);
        Log.i(TAG, "Charger config fragment created");
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "Charger config fragment destroyed");
    }
}