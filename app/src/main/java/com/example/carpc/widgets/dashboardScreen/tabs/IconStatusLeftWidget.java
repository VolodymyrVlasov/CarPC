package com.example.carpc.widgets.dashboardScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;

public class IconStatusLeftWidget extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.icon_status_left_widget, null);
        setRetainInstance(true);
        return v;
    }
}
