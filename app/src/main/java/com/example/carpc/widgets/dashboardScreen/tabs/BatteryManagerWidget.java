package com.example.carpc.widgets.dashboardScreen.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.utils.Counter;
import com.example.carpc.utils.DataParser;

public class BatteryManagerWidget extends Fragment {
    public static TextView currentTextView, voltageTextView, powerTextView,
            avConsumption, currentConsumption, currentSessionWattMeter,
            minCellNumber, minCellVoltage,
            maxCellNumber, maxCellVoltage;
    private DataParser parser;
    private Counter counter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battery_manager_widget, null);

        currentTextView = v.findViewById(R.id.current_text_view);
        voltageTextView = v.findViewById(R.id.voltae_text_view);
        powerTextView = v.findViewById(R.id.power_text_view);

        avConsumption = v.findViewById(R.id.average_WH_divide_KM_TextView);
        currentConsumption = v.findViewById(R.id.current_WH_divide_KM_TextView);
        currentSessionWattMeter = v.findViewById(R.id.this_session_used_power);

        minCellNumber = v.findViewById(R.id.value_cell_min_id);
        minCellVoltage = v.findViewById(R.id.value_cell_min_voltage);
        maxCellNumber = v.findViewById(R.id.value_cell_max_id);
        maxCellVoltage = v.findViewById(R.id.value_cell_max_voltage);
        parser = DataParser.getInstance();
//        update();
        setRetainInstance(true);
        return v;
    }

    public void updateWidgetUI(final Double current, final Double voltage, final Double capacity) {
        System.out.println("Battery manager Widget");
        currentTextView.post(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                currentTextView.setText(String.valueOf((int) Math.round(current)));
                voltageTextView.setText(String.valueOf((int) Math.round(voltage)));
                powerTextView.setText(String.valueOf((int) Math.round(current * voltage / 1000)));
                avConsumption.setText(String.format("%.0f",
                        Counter.getUsedWH() * 1000 / parser.getLastChargePassedDistance()));
                currentConsumption.setText(String.valueOf((int) Math.round(capacity)));
                String[] maxCellVal = parser.getMaxCellVoltage().split(":");
                String[] minCellVal = parser.getMinCellVoltage().split(":");
                if (minCellVal.length == 2) {
                    minCellNumber.setText(minCellVal[0]);
                    minCellVal[1] = minCellVal[1].substring(0, 1) + "." + minCellVal[1].substring(1);
                    minCellVoltage.setText(minCellVal[1]);
                }
                if (maxCellVal.length == 2) {
                    maxCellNumber.setText(maxCellVal[0]);
                    maxCellVal[1] = maxCellVal[1].substring(0, 1) + "." + maxCellVal[1].substring(1);
                    maxCellVoltage.setText(maxCellVal[1]);
                }
            }
        });
    }


//    @SuppressLint("SetTextI18n")
//    public void update() {
//        TimerTask repeatedTask = new TimerTask() {
//            public void run() {
//                if (getActivity() != null) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @SuppressLint("DefaultLocale")
//                        @Override
//                        public void run() {
//                            double current = parser.getCurrent();
//                            double voltage = parser.getVoltage();
//                            double capacity = parser.getBatteryCapacity();
//
//
//                            currentTextView.setText(String.valueOf((int) Math.round(current)));
//                            voltageTextView.setText(String.valueOf((int) Math.round(voltage)));
//                            powerTextView.setText(String.valueOf((int) Math.round(current * voltage / 1000)));
//                            if (Counter.getUsedWH() < 10) {
//                                currentSessionWattMeter.setText(String.format("%.1f", Counter.getUsedWH()));
//                            } else {
//                                currentSessionWattMeter.setText(String.format("%.0f", Counter.getUsedWH()));
//                            }
//                            avConsumption.setText(String.valueOf(String.format("%.0f",
//                                    Counter.getUsedWH() * 1000 / parser.getLastChargePassedDistance())));
//                            currentConsumption.setText(String.valueOf((int) Math.round(capacity)));
//
//                            String[] maxCellVal = parser.getMaxCellVoltage().split(":");
//                            String[] minCellVal = parser.getMinCellVoltage().split(":");
//                            if (minCellVal.length == 2) {
//                                minCellNumber.setText(minCellVal[0]);
//                                minCellVal[1] = minCellVal[1].substring(0, 1) + "." + minCellVal[1].substring(1);
//                                minCellVoltage.setText(minCellVal[1]);
//                            }
//                            if (maxCellVal.length == 2) {
//                                maxCellNumber.setText(maxCellVal[0]);
//                                maxCellVal[1] = maxCellVal[1].substring(0, 1) + "." + maxCellVal[1].substring(1);
//                                maxCellVoltage.setText(maxCellVal[1]);
//                            }
//                        }
//                    });
//                }
//            }
//        };
//        Timer timer = new Timer("Timer");
//        long delay = 50L;
//        long period = 10L;
//        timer.scheduleAtFixedRate(repeatedTask, delay, period);
//    }

}