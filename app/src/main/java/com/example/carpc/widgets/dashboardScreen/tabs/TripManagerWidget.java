package com.example.carpc.widgets.dashboardScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carpc.R;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.dashboardScreen.AbstractDashboardWidget;

public class TripManagerWidget extends AbstractDashboardWidget {
    //LAST CHARGE
    public TextView passedDistanceTextView, rangeDistanceTextView, ampereHourTextView;
    //CURRENT TRIP
    public TextView passedDistanceCurTripTextView,
            usedEnergyCurTripTextView, averageConsumptionCurTripTextView;
    //ODOMETER
    public TextView totalTripTextView, totalEnergyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.widget_trip_manager, null);
        passedDistanceTextView = v.findViewById(R.id.passed_last_charge_text_view);
        rangeDistanceTextView = v.findViewById(R.id.range_last_charge_text_view);
        ampereHourTextView = v.findViewById(R.id.ampere_hour_text_view);

        passedDistanceCurTripTextView = v.findViewById(R.id.passed_current_trip_text_view);
        usedEnergyCurTripTextView = v.findViewById(R.id.current_trip_used_energy_text_view);
        averageConsumptionCurTripTextView = v.findViewById(R.id.current_trip_average_consumption_text_view);

        totalTripTextView = v.findViewById(R.id.value_total_trip);
        totalEnergyTextView = v.findViewById(R.id.value_total_energy);
        setRetainInstance(true);
        return v;
    }

    @Override
    public void updateUI(final DataParser data) {
        passedDistanceTextView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    passedDistanceTextView.setText(String.valueOf(Math.round(data.getLastChargePassedDistance())));
                    rangeDistanceTextView.setText(String.valueOf(Math.round(data.getRange())));
                    totalTripTextView.setText(String.valueOf(Math.round(data.getTotalDistance())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}