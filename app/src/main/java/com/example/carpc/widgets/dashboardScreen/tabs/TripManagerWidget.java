package com.example.carpc.widgets.dashboardScreen.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;
import com.example.carpc.utils.DataParser;

import java.util.Timer;
import java.util.TimerTask;

public class TripManagerWidget extends Fragment {
    //LAST CHARGE
    public TextView passedDistanceTextView, rangeDistanceTextView, ampereHourTextView;
    //CURRENT TRIP
    public TextView passedDistanceCurTripTextView,
            usedEnergyCurTripTextView, averageConsumptionCurTripTextView;
    //ODOMETER
    public TextView totalTripTextView, totalEnergyTextView;
    int totalTripToSet = 0;
    DataParser parser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trip_manager_widget, null);
        passedDistanceTextView = v.findViewById(R.id.passed_last_charge_text_view);
        rangeDistanceTextView = v.findViewById(R.id.range_last_charge_text_view);
        ampereHourTextView = v.findViewById(R.id.ampere_hour_text_view);

        passedDistanceCurTripTextView = v.findViewById(R.id.passed_current_trip_text_view);
        usedEnergyCurTripTextView = v.findViewById(R.id.current_trip_used_energy_text_view);
        averageConsumptionCurTripTextView = v.findViewById(R.id.current_trip_average_consumption_text_view);

        totalTripTextView = v.findViewById(R.id.value_total_trip);
        totalEnergyTextView = v.findViewById(R.id.value_total_energy);
        parser = (DataParser) MainActivity.getParser();
        update();
        setRetainInstance(true);
        return v;
    }


    @SuppressLint("SetTextI18n")
    public void update() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            double totalTrip = parser.getTotalDistance();
                            passedDistanceTextView.setText(String.valueOf((int) Math.round(
                                    parser.getLastChargePassedDistance())));
                            rangeDistanceTextView.setText(String.valueOf((int) Math.round(
                                    parser.getRange())));
                            if (totalTripToSet <= totalTrip) {
                                totalTripToSet += 1;
                                totalTripTextView.setText(String.valueOf((int) Math.round(totalTripToSet)));
                            }
                            if (totalTripToSet > totalTrip) {
                                totalTripToSet -= 1;
                                totalTripTextView.setText(String.valueOf((int) Math.round(totalTripToSet)));
                            }
                        }
                    });
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 50L;
        long period = 10L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }


    public void updateParameters(Double passedLC, Double rangeLC, Double totalTrip) {
        passedDistanceTextView.setText(String.valueOf((int) Math.round(passedLC)));
        rangeDistanceTextView.setText(String.valueOf((int) Math.round(rangeLC)));
//        totalTripTextView.setText(String.valueOf((int) Math.round(totalTrip)));


        if (totalTripToSet <= totalTrip) {
            totalTripToSet += 1;
            totalTripTextView.setText(String.valueOf((int) Math.round(totalTripToSet)));
        }
        if (totalTripToSet > totalTrip) {
            totalTripToSet -= 1;
            totalTripTextView.setText(String.valueOf((int) Math.round(totalTripToSet)));
        }
    }
}