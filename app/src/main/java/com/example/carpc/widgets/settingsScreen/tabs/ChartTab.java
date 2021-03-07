package com.example.carpc.widgets.settingsScreen.tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.carpc.R;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.dashboardScreen.AbstractDashboardWidget;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import java.util.ArrayList;
import java.util.List;

//        String[] arr = new String[]{"Cell 5: 4055, 26.6, 30.6, 5, 8",
//                "Cell 4: 4066, 26.6, 30.6, 5, 8",
//                "Cell 3: 4057, 26.5, 30.6, 5, 8",
//                "Cell 2: 4045, 26.6, 30.5, 5, 8",
//                "Cell 1: 4067, 26.6, 30.5, 5, 8"};
public class ChartTab extends AbstractDashboardWidget {
    private BarChart barChart;
    private ArrayList<BarEntry> barEntries;
    private List<Integer> colors;

    private static final String FRAGMENT_TAG  = "CHART_TAB";
    private static final String chartName = "Cells voltage";
    private final int RED_COLOR  = Color.parseColor("#FF4500");
    private final int GREY_COLOR = Color.parseColor("#C0C0C0");
    private final int YELLOW_COLOR = Color.parseColor("#ffff00");
    private static final int DEFAULT_BAR_VOLTAGE = 999;

    // trigger variable for bar chart rendering
    private boolean isInitialized = false;

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_tab, container, false);
        setRetainInstance(true);

        barChart = (BarChart) v.findViewById(R.id.lineChart);

        int xAxisQuantity = DataParser.getCellsQuantity();

        colors = new ArrayList<Integer>(xAxisQuantity);

        barEntries = new ArrayList<BarEntry>(xAxisQuantity);

        // set default values with 999 bar height and grey color
        for(int i = 0; i < xAxisQuantity; i++) {
            colors.add(0);
            barEntries.add(null);
            this.addBarData(i, DEFAULT_BAR_VOLTAGE, GREY_COLOR);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, chartName);
        barDataSet.setColors(colors);

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        this.initBarChartStyles();

        this.isInitialized = true;
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        TCPClient tcpClient = TCPClient.getInstance(getContext());

        tcpClient.sendMessage("@a0"); // @0 - unsubscribe
        tcpClient.sendMessage("transmit 1"); // get data from transmit
        Log.i(FRAGMENT_TAG, "Subscribe to transmit data");
    }

    @Override
    public void onPause() {
        super.onPause();

        TCPClient tcpClient = TCPClient.getInstance(getContext());

        tcpClient.sendMessage("transmit 0"); // unsubscribe from transmit
        Log.i(FRAGMENT_TAG, "Unsubscribe from transmit data");
    }

    private void initBarChartStyles() {
        // remove background and borders
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);

        // remove description
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        // set animation
        barChart.animateY(1000);
    }

    private void addBarData(float x, float y, float min, float max, float allowd) {
        // set color for x
        colors.set((int) x, getBarColor(y, min, max, allowd));

        // update/set bar data with index x

        barEntries.set((int) x, new BarEntry(x, y));
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    private void addBarData(float x, float y, int color) {
        // set color for x
        colors.set((int) x, color);

        // update/set bar data with index x

        barEntries.set((int)x, new BarEntry(x, y));
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        System.out.println("Destroy CHART TAB");
//    }

    private int getBarColor(float voltageData, float min, float max, float allowd) {
        // < 1k grey
        // < min && > max red
        // > min && < allowd yellow

        if (voltageData < 1000) {
            return this.GREY_COLOR;
        } else if (voltageData < min && voltageData > max) {
            return this.RED_COLOR;
        } else if (voltageData > min && voltageData < allowd) {
            return this.YELLOW_COLOR;
        } else {
            return this.GREY_COLOR; // if another case return also gery color
        }
    }

    @Override
    public void updateUI(DataParser data) {
        Log.i(FRAGMENT_TAG, "isInitialized " + isInitialized + " array size " + colors.size() + " size2 " + barEntries.size());

        if(!isInitialized) {
            return;
        }

        String rawData = data.getTransmittedData();             //         volt, temp, temp bal, ...
        String[] rawSplitData = rawData.split(":");     // cell1:  4066, 26.6, 30.6, 5, 8

        Log.i(FRAGMENT_TAG, rawData);
        float groupIndex = Float.parseFloat(rawSplitData[0].substring(4));
        String[] groupValue = rawSplitData[1].split(",");


        float min = Float.parseFloat(data.getLevelsDataByCmdName("min"));
        float max = Float.parseFloat(data.getLevelsDataByCmdName("max"));
        float allowd = Float.parseFloat(data.getLevelsDataByCmdName("allowd"));

        addBarData(groupIndex, Float.parseFloat(groupValue[0]), min, max, allowd);
    }
}
