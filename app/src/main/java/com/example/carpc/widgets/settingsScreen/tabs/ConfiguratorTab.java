package com.example.carpc.widgets.settingsScreen.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.ChargerConfig;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.CurrentConfig;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.LevelsConfig;

public class ConfiguratorTab extends Fragment {
    FrameLayout configContainer;
    private androidx.fragment.app.FragmentTransaction fragmentTransaction;
    private static final String TAG = "GroupConfig";
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_pc_configuration, container, false);
        spinner = v.findViewById(R.id.configurationGroupList);
        spinnerSetSelector();
        setRetainInstance(true);
        System.out.println("Create CONFIGURATOR TAB");
        return v;
    }

    private void spinnerSetSelector() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.configurationGroupList);
                createConfigurationFragment(choose[selectedItemPosition]);
                Log.i(TAG, "Selected on spinner: "  + choose[selectedItemPosition]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void createConfigurationFragment(String item) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        switch (item) {
            case "Levels (BMS)":
                fragmentTransaction.replace(R.id.frameForConfigFragments, new LevelsConfig());
                fragmentTransaction.commit();
                //addBMSConfig();
                break;
            case "Current":
                fragmentTransaction.replace(R.id.frameForConfigFragments, new CurrentConfig());
                fragmentTransaction.commit();
                // addCurrentConfig();
                break;
            case "Charger":
                fragmentTransaction.replace(R.id.frameForConfigFragments, new ChargerConfig());
                fragmentTransaction.commit();
                break;
            case "Charging":
                break;
            case "Ignition":
                break;
            case "Power":
                break;
            case "Precharge":
                break;
            case "Main contactor":
                break;
            case "Charge Current Max":
                break;
            case "Discharge Current Max":
                break;
            case "Thermostat":
                break;
            case "Temperature Sensor Types":
                break;
            case "Interface":
                break;
            case "Speed":
                break;
            case "RPM":
                break;
            case "Charging temperature":
                break;
            case "Power temperature":
                break;
            case "CAN":
                break;
            case "Accelerator":
                break;
            case "Alarm":
                break;
            case "Flags":
                break;
            case "Buttons":
                break;
            case "Gauge":
                break;
            case "Battery":
                break;
        }
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void addBatteryConfig() {
    }

    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    private LinearLayout initConfigContainer() {
        LinearLayout configLayout = new LinearLayout(getContext());
        try {
            configContainer.removeAllViewsInLayout();
            configLayout.setOrientation(LinearLayout.VERTICAL);
            configLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configLayout;
    }
}