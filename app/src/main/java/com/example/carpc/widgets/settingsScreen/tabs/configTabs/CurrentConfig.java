package com.example.carpc.widgets.settingsScreen.tabs.configTabs;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;

import java.util.Objects;

public class CurrentConfig extends Fragment {
    private static final String TAG = "GroupConfig";
    LinearLayout configContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_config, container, false);
        configContainer = v.findViewById(R.id.container);
        addCurrentConfig();
        Log.i(TAG, "Current config fragment created");
        return v;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        onDestroy();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Current config fragment destroyed");
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void addCurrentConfig() {
        final int paramQuantity = 5;
        final String[] commandName = new String[]{"current"};
        String description = getString(R.string.CONFIGURATOR_Current_Top_Description);
        String description2 = getString(R.string.CONFIGURATOR_Current_Bottom_Description);
        final String[] paramTitle = new String[]{
                "INP TYPE:",
                "INP NUMBER",
                "ZERO LEVEL (mv)",
                "AMPER ON VOLT",
                "MIN FIXED CURRENT"
        };
        final String[] paramHint = new String[]{
                "2",
                "0",
                "2048",
                "192",
                "500"
        };
        LayoutInflater factory = LayoutInflater.from(getContext());
        View descriptionLayout = factory.inflate(R.layout.description_line, null);
        View btnLayout = factory.inflate(R.layout.read_write_buttons, null);
        View descriptionLayout2 = factory.inflate(R.layout.description_line, null);
        View[] paramLayout = new View[paramQuantity];
        TextView groupDescription = descriptionLayout.findViewById(R.id.description);
        TextView configDescription = descriptionLayout2.findViewById(R.id.description);
        TextView[] numParam = new TextView[paramQuantity];
        TextView[] titleParam = new TextView[paramQuantity];
        final TextView[] valueParam = new TextView[paramQuantity];
        Button write = btnLayout.findViewById(R.id.btn_write);
        Button read = btnLayout.findViewById(R.id.btn_read);
        configContainer.addView(descriptionLayout);

        for (int i = 0; i < paramQuantity; i++) {
            paramLayout[i] = factory.inflate(R.layout.config_line, null);
            numParam[i] = paramLayout[i].findViewById(R.id.parameter_num);
            titleParam[i] = paramLayout[i].findViewById(R.id.config_title);
            valueParam[i] = paramLayout[i].findViewById(R.id.config_value);
            numParam[i].setText(String.valueOf(i + 1));
            titleParam[i].setText(paramTitle[i]);
            valueParam[i].setHint(paramHint[i]);
            configContainer.addView(paramLayout[i]);
        }

        configContainer.addView(btnLayout);
        configContainer.addView(descriptionLayout2);
        groupDescription.setText(description);
        configDescription.setTextColor(Color.argb(255, 90, 90, 90));
        configDescription.setText(description2);

//        write.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    String resultCommand = commandName[0] + " ";
//
//                    for (int i = 0; i < paramQuantity; i++) {
//                        resultCommand = resultCommand + valueParam[i].getText().toString() + ":";
//                    }
//
//                    MainActivity.sendMessage("..");
//                    Thread.sleep(3);
//                    MainActivity.sendMessage("config");
//                    Thread.sleep(3);
//                    MainActivity.sendMessage(resultCommand);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                MainActivity.hideKeyboard(Objects.requireNonNull(getActivity()));
//            }
//        });
//
//        read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    MainActivity.sendMessage("..");
//                    Thread.sleep(3);
//                    MainActivity.sendMessage("config");
//                    Thread.sleep(3);
//                    MainActivity.sendMessage(commandName[0]);
//                    Thread.sleep(100);
//                    String newConfig = MainActivity.getParser().getCurrentConfig();
//                    Log.i("io/tag", "currentConfig: " + newConfig);
//                    if (!newConfig.contains("try")) {
//                        String[] arr = newConfig.split(":");
//
//                        for (int i = 0; i < paramQuantity; i++) {
//                            valueParam[i].setText(arr[i]);
//                        }
//                    }
//                    Toast.makeText(getContext(), newConfig, Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                MainActivity.hideKeyboard(Objects.requireNonNull(getActivity()));
//            }
//        });
    }
}
