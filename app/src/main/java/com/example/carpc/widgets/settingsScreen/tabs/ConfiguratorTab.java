package com.example.carpc.widgets.settingsScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.network.ClientSocket;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.ChargerConfig;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.CurrentConfig;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.LevelsConfig;

public class ConfiguratorTab extends Fragment {

    private static final String TAG = "GroupConfig";

    private androidx.fragment.app.FragmentTransaction fragmentTransaction;
    private ClientSocket socket;
    private DataParser parser;

    public ConfiguratorTab(final ClientSocket socket, final DataParser parser) {
        this.socket = socket;
        this.parser = parser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_pc_configuration, container, false);
        Spinner spinner = v.findViewById(R.id.configurationGroupList);
        setOnItemSelectedListener(spinner);
        setRetainInstance(true);
        return v;
    }

    private void setOnItemSelectedListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.configurationGroupList);
                createConfigurationFragment(choose[selectedItemPosition]);
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
                break;
            case "Current":
                fragmentTransaction.replace(R.id.frameForConfigFragments, new CurrentConfig(socket, parser));
                break;
            case "Charger":
                fragmentTransaction.replace(R.id.frameForConfigFragments, new ChargerConfig());
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
        fragmentTransaction.commit();
    }
}