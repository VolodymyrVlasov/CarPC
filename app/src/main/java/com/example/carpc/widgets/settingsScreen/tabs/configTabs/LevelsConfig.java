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

import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;

import java.util.Objects;

public class LevelsConfig extends Fragment {
    private static final String TAG = "GroupConfig";
    LinearLayout configContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_levels_config, container, false);
        configContainer = v.findViewById(R.id.container);
        addBMSConfig();
        Log.i(TAG, "Levels config fragment created");
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Levels config fragment destroyed");
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void addBMSConfig() {
        final int paramQuantity = 7;
        final String[] commandName = new String[]{
                "max",
                "min",
                "startbal",
                "bal",
                "chrgd",
                "allowd",
                "cmin",
        };
        final String[] paramTitle = new String[]{
                "Max voltage level (mv)",
                "Min voltage level (mv)",
                "Start balancing voltage (mv)",
                "Stop balancing voltage (mv)",
                "Charged voltage (mv)",
                "Allowed voltage (mv)",
                "Critical min voltage (mv)"
        };
        final String[] paramHint = new String[]{
                "4200",
                "3000",
                "4100",
                "4180",
                "4150",
                "2850",
                "2700"
        };
        String description = getString(R.string.CONFIGURATOR_Levels_Top_Description);
        String description2 = getString(R.string.CONFIGURATOR_Levels_Bottom_Description);

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
        final Button read = btnLayout.findViewById(R.id.btn_read);
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

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readParams(paramQuantity, commandName, valueParam);
            }
        });
    }

    private void readParams(int paramQuantity, String[] commandName, TextView[] valueParam) {
//            MainActivity.getLevels(paramQuantity, commandName);
//            boolean flag = true;
//            long time = System.currentTimeMillis() + 100;
//            while (flag) {
//                if (System.currentTimeMillis() >= time) {
//                    valueParam[0].setText(MainActivity.getParser().getLevelsMax());
//                    valueParam[1].setText(MainActivity.getParser().getLevelsMin());
//                    valueParam[2].setText(MainActivity.getParser().getLevelsStartbal());
//                    valueParam[3].setText(MainActivity.getParser().getLevelsBal());
//                    valueParam[4].setText(MainActivity.getParser().getLevelsChrgd());
//                    valueParam[5].setText(MainActivity.getParser().getLevelsAllowd());
//                    valueParam[6].setText(MainActivity.getParser().getLevelsCmin());
//                    MainActivity.hideKeyboard(Objects.requireNonNull(getActivity()));
//                    flag = false;
//                }
//            }
    }
}