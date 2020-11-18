package com.example.carpc.settings.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;

public class ConfiguratorTab extends Fragment {
    ScrollView configContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_pc_configuration, container, false);

        Spinner spinner = v.findViewById(R.id.configurationGroupList);
        configContainer = v.findViewById(R.id.frameForConfigFragments);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.configurationGroupList);

                createConfigurationFragment(choose[selectedItemPosition]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        setRetainInstance(true);
        System.out.println("Create CONFIGURATOR TAB");
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy CONFIGURATOR TAB");
    }

    private void createConfigurationFragment(String item) {
        switch (item) {
            case "Levels (BMS)":
                Toast.makeText(getActivity().getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                break;
            case "Current":
                break;
            case "Charger":
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
                addBatteryConfig();
                break;
        }
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void addBatteryConfig() {
        Context context = getActivity().getApplicationContext();
        Drawable myDrawable = null;
        Resources res = getResources();
        try {
            myDrawable = Drawable.createFromXml(res, res.getXml(R.drawable.transparent_bg_bordered_button));
        } catch (Exception ex) {
        }

        LinearLayout myLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(30, 0, 30, 0);
        myLayout.setLayoutParams(params);
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        myLayout.setBackgroundColor(Color.RED);


        TextView description = new TextView(context);
        description.setTextColor(Color.GRAY);
        description.setTextSize(1, 15);
        description.setText("Set battery capacity (Ah)");

        Button write = new Button(context);
        Button read = new Button(context);

        write.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));
        read.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));


        read.setBackground(myDrawable);
        read.setTextColor(Color.GRAY);

        write.setBackground(myDrawable);
        write.setTextColor(Color.GRAY);

        read.setText("READ PARAMS");
        write.setText("WRITE PARAMS");


        LinearLayout buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonsLayout.setBackgroundColor(Color.GREEN);

        buttonsLayout.addView(read);
        buttonsLayout.addView(write);

        myLayout.addView(description);
        myLayout.addView(buttonsLayout);

        configContainer.addView(myLayout);

    }


}