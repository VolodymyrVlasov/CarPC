package com.example.carpc.settings.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.carpc.MainActivity;
import com.example.carpc.R;

import java.util.Objects;

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
                addBMSConfig();
                break;
            case "Current":
                addCurrentConfig();
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
    private void addBMSConfig() {
        String description = "In this group, voltage levels for the cells are configured." +
                "The levels are stored both on the main board and on all additional ones.";

        final String param1 = "Max cell voltage level (mv)",
                defaultValue1 = "4200";
        final String param2 = "Min cell voltage level (mv)",
                defaultValue2 = "3000";
        final String param3 = "Start balancing voltage level (mv)",
                defaultValue3 = "4100";
        final String param4 = "Stop balancing voltage level (mv)",
                defaultValue4 = "4180";
        final String param5 = "Charged voltage level (mv)",
                defaultValue5 = "4150";
        final String param6 = "Allowed voltage level (mv)",
                defaultValue6 = "2850";
        final String param7 = "Critical min voltage level (mv)",
                defaultValue7 = "2700";

        final String syncCommand = "sunc 1";


        final String[] resultCommand = {""};

        // Configuring root layout
        configContainer.removeAllViewsInLayout();


        LinearLayout configLayout = new LinearLayout(getContext());
        configLayout.setOrientation(LinearLayout.VERTICAL);
        configLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        // Configuring inner layout for params group
        LayoutInflater factory = LayoutInflater.from(getContext());
        View descriptionLayout = factory.inflate(R.layout.description_line, null);
        View paramLayout1 = factory.inflate(R.layout.config_line, null);
        View paramLayout2 = factory.inflate(R.layout.config_line, null);
        View paramLayout3 = factory.inflate(R.layout.config_line, null);
        View paramLayout4 = factory.inflate(R.layout.config_line, null);
        View paramLayout5 = factory.inflate(R.layout.config_line, null);
        View paramLayout6 = factory.inflate(R.layout.config_line, null);
        View paramLayout7 = factory.inflate(R.layout.config_line, null);

        View btnLayout = factory.inflate(R.layout.read_write_buttons, null);

        // Find all views
        TextView groupDescription = descriptionLayout.findViewById(R.id.description);

        TextView numParam1 = paramLayout1.findViewById(R.id.parameter_num);
        TextView descriptionParam1 = paramLayout1.findViewById(R.id.config_description);
        final TextView valueParam1 = paramLayout1.findViewById(R.id.config_value);

        TextView numParam2 = paramLayout2.findViewById(R.id.parameter_num);
        TextView descriptionParam2 = paramLayout2.findViewById(R.id.config_description);
        final TextView valueParam2 = paramLayout2.findViewById(R.id.config_value);

        TextView numParam3 = paramLayout3.findViewById(R.id.parameter_num);
        TextView descriptionParam3 = paramLayout3.findViewById(R.id.config_description);
        final TextView valueParam3 = paramLayout3.findViewById(R.id.config_value);

        TextView numParam4 = paramLayout4.findViewById(R.id.parameter_num);
        TextView descriptionParam4 = paramLayout4.findViewById(R.id.config_description);
        final TextView valueParam4 = paramLayout4.findViewById(R.id.config_value);

        TextView numParam5 = paramLayout5.findViewById(R.id.parameter_num);
        TextView descriptionParam5 = paramLayout5.findViewById(R.id.config_description);
        final TextView valueParam5 = paramLayout5.findViewById(R.id.config_value);

        TextView numParam6 = paramLayout6.findViewById(R.id.parameter_num);
        TextView descriptionParam6 = paramLayout6.findViewById(R.id.config_description);
        final TextView valueParam6 = paramLayout6.findViewById(R.id.config_value);

        TextView numParam7 = paramLayout7.findViewById(R.id.parameter_num);
        TextView descriptionParam7 = paramLayout7.findViewById(R.id.config_description);
        final TextView valueParam7 = paramLayout7.findViewById(R.id.config_value);

        Button write = btnLayout.findViewById(R.id.btn_write);
        Button read = btnLayout.findViewById(R.id.btn_read);

        configLayout.addView(descriptionLayout);
        configLayout.addView(paramLayout1);
        configLayout.addView(paramLayout2);
        configLayout.addView(paramLayout3);
        configLayout.addView(paramLayout4);
        configLayout.addView(paramLayout5);
        configLayout.addView(paramLayout6);
        configLayout.addView(paramLayout7);
        configLayout.addView(btnLayout);
        configContainer.addView(configLayout);

        groupDescription.setText(description);

        numParam1.setText("1");
        numParam2.setText("2");
        numParam3.setText("3");
        numParam4.setText("4");
        numParam5.setText("5");
        numParam6.setText("6");
        numParam7.setText("7");

        descriptionParam1.setText(param1);
        descriptionParam2.setText(param2);
        descriptionParam3.setText(param3);
        descriptionParam4.setText(param4);
        descriptionParam5.setText(param5);
        descriptionParam6.setText(param6);
        descriptionParam7.setText(param7);

        valueParam1.setHint(defaultValue1);
        valueParam2.setHint(defaultValue2);
        valueParam3.setHint(defaultValue3);
        valueParam4.setHint(defaultValue4);
        valueParam5.setHint(defaultValue5);
        valueParam6.setHint(defaultValue6);
        valueParam7.setHint(defaultValue7);


        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultCommand[0] = param1 + valueParam1.getText().toString() + "\n" +
                        param2 + valueParam2.getText().toString() + "\n" +
                        param3 + valueParam3.getText().toString() + "\n" +
                        param4 + valueParam4.getText().toString() + "\n" +
                        param5 + valueParam5.getText().toString() + "\n" +
                        param6 + valueParam6.getText().toString() + "\n" +
                        param7 + valueParam7.getText().toString() + "\n" +
                        syncCommand + "\n";

                Toast.makeText(getContext(),
                        resultCommand[0], Toast.LENGTH_SHORT).show();
                MainActivity.hideKeyboard(Objects.requireNonNull(getActivity()));
            }
        });
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void addCurrentConfig() {
        String description = "Setting up the current sensor.\n" +
                "To configure you need to use the terminal on the next tab";
        String description2 =
                "1. INP TYPE:\n" +
                "   0 — Undefined\n" +
                "   1 — ADC\n" +
                "   2 — USART\n" +
                "   3 — RS485\n" +

                "\n2. INP NUMBER\n" +
                "for ADC only, else - 0\n" +

                "\n3. ZERO LEVEL\n" +
                "   The value corresponding to zero current. Less is negative current. More is positive.\n" +
                "   For a USART input, this is the ADC value. It can be recognized by the diag / current command. In brackets is the value of the ADC.\n" +

                "\n4. AMPER ON VOLT\n" +
                "   For ADC input, this is the multiplier of the transducer output voltage to current conversion. For example, we know that a 10 milliVolt (0.01 volt) change in voltage at the sensor output corresponds to a current of 1 Ampere. So the value of this parameter is equal to sts (1 / 0.01 = 100).\n" +
                "   For USART and RS485 input, this is the M multiplier in the formula C = M * Adc. ADC capacity (Adc) - 4096. If the sensor measures current from -400 to + 400A, we get a range of 800A. 800/4096 = 0.195. Those. 195 mA per ADC unit. Considering that the return value in milliAmperes, this parameter is 195.\n" +

                "5. MIN FIXED CURRENT\n" +
                "    Current value in milliAmperes. The minimum current value that is taken into account. For example, the current less than 1 ampere is not taken into account due to the sensor error. Temperature drift of the used electronic components leads to the fact that in the absence of real current, the device will \\\"catch\\\" a current of 0.5A or -0.7A. With a measured range of currents from -200A to + 200A, the minimum current in 1A will be 0.5%. Which matches the accuracy of most measuring instruments. The value is always rounded up to 100mA. The max parameter value is 25000mA.";

        final String commandName = "current ";
        final String param1 = "INP TYPE:",
                defaultValue1 = "2";
        final String param2 = "INP NUMBER",
                defaultValue2 = "0";
        final String param3 = "ZERO LEVEL (mv)",
                defaultValue3 = "2012";
        final String param4 = "AMPER ON VOLT",
                defaultValue4 = "192";
        final String param5 = "MIN FIXED CURRENT",
                defaultValue5 = "500";

        final String[] resultCommand = {""};

        // Configuring root layout
        configContainer.removeAllViewsInLayout();


        LinearLayout configLayout = new LinearLayout(getContext());
        configLayout.setOrientation(LinearLayout.VERTICAL);
        configLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        // Configuring inner layout for params group
        LayoutInflater factory = LayoutInflater.from(getContext());
        View descriptionLayout = factory.inflate(R.layout.description_line, null);
        View paramLayout1 = factory.inflate(R.layout.config_line, null);
        View paramLayout2 = factory.inflate(R.layout.config_line, null);
        View paramLayout3 = factory.inflate(R.layout.config_line, null);
        View paramLayout4 = factory.inflate(R.layout.config_line, null);
        View paramLayout5 = factory.inflate(R.layout.config_line, null);
        View descriptionLayout2 = factory.inflate(R.layout.description_line, null);

        View btnLayout = factory.inflate(R.layout.read_write_buttons, null);

        // Find all views
        TextView groupDescription = descriptionLayout.findViewById(R.id.description);
        TextView configDescription = descriptionLayout2.findViewById(R.id.description);

        TextView numParam1 = paramLayout1.findViewById(R.id.parameter_num);
        TextView descriptionParam1 = paramLayout1.findViewById(R.id.config_description);
        final TextView valueParam1 = paramLayout1.findViewById(R.id.config_value);

        TextView numParam2 = paramLayout2.findViewById(R.id.parameter_num);
        TextView descriptionParam2 = paramLayout2.findViewById(R.id.config_description);
        final TextView valueParam2 = paramLayout2.findViewById(R.id.config_value);

        TextView numParam3 = paramLayout3.findViewById(R.id.parameter_num);
        TextView descriptionParam3 = paramLayout3.findViewById(R.id.config_description);
        final TextView valueParam3 = paramLayout3.findViewById(R.id.config_value);

        TextView numParam4 = paramLayout4.findViewById(R.id.parameter_num);
        TextView descriptionParam4 = paramLayout4.findViewById(R.id.config_description);
        final TextView valueParam4 = paramLayout4.findViewById(R.id.config_value);

        TextView numParam5 = paramLayout5.findViewById(R.id.parameter_num);
        TextView descriptionParam5 = paramLayout5.findViewById(R.id.config_description);
        final TextView valueParam5 = paramLayout5.findViewById(R.id.config_value);


        Button write = btnLayout.findViewById(R.id.btn_write);
        Button read = btnLayout.findViewById(R.id.btn_read);

        configLayout.addView(descriptionLayout);
        configLayout.addView(paramLayout1);
        configLayout.addView(paramLayout2);
        configLayout.addView(paramLayout3);
        configLayout.addView(paramLayout4);
        configLayout.addView(paramLayout5);

        configLayout.addView(btnLayout);
        configLayout.addView(descriptionLayout2);
        configContainer.addView(configLayout);

        groupDescription.setText(description);


        numParam1.setText("1");
        numParam2.setText("2");
        numParam3.setText("3");
        numParam4.setText("4");
        numParam5.setText("5");

        descriptionParam1.setText(param1);
        descriptionParam2.setText(param2);
        descriptionParam3.setText(param3);
        descriptionParam4.setText(param4);
        descriptionParam5.setText(param5);

        valueParam1.setHint(defaultValue1);
        valueParam2.setHint(defaultValue2);
        valueParam3.setHint(defaultValue3);
        valueParam4.setHint(defaultValue4);
        valueParam5.setHint(defaultValue5);

        configDescription.setText(description2);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultCommand[0] = commandName +
                        valueParam1.getText().toString() + ":" +
                        valueParam2.getText().toString() + ":" +
                        valueParam3.getText().toString() + ":" +
                        valueParam4.getText().toString() + ":" +
                        valueParam5.getText().toString();

                Toast.makeText(getContext(),
                        resultCommand[0], Toast.LENGTH_SHORT).show();
                MainActivity.hideKeyboard(Objects.requireNonNull(getActivity()));
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "TODO this func", Toast.LENGTH_SHORT).show();
                MainActivity.hideKeyboard(Objects.requireNonNull(getActivity()));
            }
        });
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void addBatteryConfig() {
    }

    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}