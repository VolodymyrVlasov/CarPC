package com.example.carpc.widgets.settingsScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.carpc.R;

public class PlotTab extends Fragment {

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plot_tab, container, false);
        setRetainInstance(true);
        System.out.println("Create PLOT TAB");
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy PLOT TAB");
    }
}
