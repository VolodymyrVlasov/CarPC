package com.example.carpc.widgets.settingsScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.ChargerConfig;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.CurrentConfig;
import com.example.carpc.widgets.settingsScreen.tabs.configTabs.LevelsConfig;

import java.util.ArrayList;

public class ConfiguratorTab extends Fragment {
    private static final String TAG = "GroupConfig";
    private androidx.fragment.app.FragmentTransaction fragmentTransaction;

    private ListView listView;
    private Spinner spinner;
    private ArrayAdapter<ConfigData> adapter;
    private String[] spinnerCategories;

    public ConfiguratorTab() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_pc_configuration, container, false);

        spinnerCategories = getContext().getResources().getStringArray(R.array.configurationGroupList);



        spinner = v.findViewById(R.id.configurationGroupList);
//        spinner.setAdapter(new ArrayAdapter<String>(getContext(), R.id.spinner_text_item, spinnerCategories));

        listView = v.findViewById(R.id.configListView);
        listView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.config_line, getListConfigData()));
//

//        setOnItemSelectedListener(spinner);
//        setRetainInstance(true);

        return v;
    }

    private ArrayList<ConfigData> getListConfigData() {
        ArrayList<ConfigData> data = new ArrayList<>();
        data.add(new ConfigData("Item", "300"));
        return data;
    }

//
//    private void setOnItemSelectedListener(Spinner spinner) {
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent,
//                                       View itemSelected, int selectedItemPosition, long selectedId) {
//                String[] choose = getResources().getStringArray(R.array.configurationGroupList);
////                createConfigurationFragment(choose[selectedItemPosition]);
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }

//    private void createConfigurationFragment(String item) {
//        fragmentTransaction = getChildFragmentManager().beginTransaction();
//        switch (item) {
//            case "Levels (BMS)":
//                fragmentTransaction.replace(R.id.frameForConfigFragments, new LevelsConfig());
//                break;
//            case "Current":
//                fragmentTransaction.replace(R.id.frameForConfigFragments, new CurrentConfig());
//                break;
//            case "Charger":
//                fragmentTransaction.replace(R.id.frameForConfigFragments, new ChargerConfig());
//                break;
//            case "Charging":
//                break;
//            case "Ignition":
//                break;
//            case "Power":
//                break;
//            case "Precharge":
//                break;
//            case "Main contactor":
//                break;
//            case "Charge Current Max":
//                break;
//            case "Discharge Current Max":
//                break;
//            case "Thermostat":
//                break;
//            case "Temperature Sensor Types":
//                break;
//            case "Interface":
//                break;
//            case "Speed":
//                break;
//            case "RPM":
//                break;
//            case "Charging temperature":
//                break;
//            case "Power temperature":
//                break;
//            case "CAN":
//                break;
//            case "Accelerator":
//                break;
//            case "Alarm":
//                break;
//            case "Flags":
//                break;
//            case "Buttons":
//                break;
//            case "Gauge":
//                break;
//            case "Battery":
//                break;
//        }
//        fragmentTransaction.commit();
//    }
}

class ConfigData {
    private String configItem;
    private String configHint;

    public ConfigData(String configItem, String configHint) {
        this.configItem = configItem;
        this.configHint = configHint;
    }

    public String getConfigItem() {
        return configItem;
    }

    public void setConfigItem(String configItem) {
        this.configItem = configItem;
    }

    public String getConfigHint() {
        return configHint;
    }

    public void setConfigHint(String configHint) {
        this.configHint = configHint;
    }
}