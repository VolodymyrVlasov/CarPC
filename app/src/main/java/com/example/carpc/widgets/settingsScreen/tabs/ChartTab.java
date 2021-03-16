package com.example.carpc.widgets.settingsScreen.tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.carpc.R;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.dashboardScreen.AbstractDashboardWidget;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class ChartTab extends AbstractDashboardWidget {
    private HorizontalBarChart barChart;
    private ArrayList<BarEntry> barEntries;
    private List<Integer> colors;

    private static final String FRAGMENT_TAG = "CHART_TAB";
    private static final String chartName = "Cells voltage";
    private static final int DEFAULT_BAR_VOLTAGE = 999;

    // colors
    private final int RED_COLOR = Color.parseColor("#FF4500");
    private final int COLOR_GREY = Color.parseColor("#C0C0C0");
    private final int YELLOW_COLOR = Color.parseColor("#ffff00");
    private final int COLOR_GREEN = Color.parseColor("#1fffad");

    // trigger variable for bar chart rendering
    private boolean isInitialized = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_tab, container, false);
        setRetainInstance(true);

        barChart = (HorizontalBarChart) v.findViewById(R.id.lineChart);
        int xAxisQuantity = DataParser.getInstance().getCellsQuantity();
        colors = new ArrayList<Integer>(xAxisQuantity);
        barEntries = new ArrayList<BarEntry>(xAxisQuantity);

        // set default values with 999 bar height and grey color
        for (int i = 0; i < xAxisQuantity; i++) {
            colors.add(0);
            barEntries.add(null);
            this.addBarData(i, DEFAULT_BAR_VOLTAGE, COLOR_GREY);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, chartName);
        barDataSet.setColors(colors);

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        this.initBarChartStyles();

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
    public void onResume() {
        super.onResume();
        this.isInitialized = true;
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
        barChart.setScaleEnabled(false);
        barChart.getLegend().setEnabled(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawZeroLine(false);
        barChart.getAxisLeft().setDrawLabels(false);

        barChart.getXAxis().setTextColor(COLOR_GREY);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawLabels(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setTextSize(15f);

        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawZeroLine(false);
        barChart.getAxisRight().setDrawLabels(false);

        // remove description
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        // set animation
        barChart.animateY(1000);

        // set max and min values for y axis
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(DataParser.getInstance().getMaxConfigVoltage() + 100);
        yAxis.setAxisMinimum(900);

        barChart.getXAxis().setLabelCount(12, true);
        barChart.getXAxis().setTextColor(COLOR_GREY);
        barChart.getAxisLeft().setTextColor(COLOR_GREY);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float voltage = e.getX();
                float cellIndex = e.getY();
                updateChartDescriptionDataOnSelect(voltage, cellIndex);

            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void updateChartDescriptionDataOnSelect(float voltage, float cellIndex) {
        Log.i(FRAGMENT_TAG, "onSelect " + voltage + " cellIndex " + cellIndex);
        // todo
    }

    private void addBarData(float x, float y, float min, float max, float allowd) {
        try {
            // set color for x
            colors.set((int) x - 1, getBarColor(y, min, max, allowd));
            // todo: fix 0 index
            // update/set bar data with index x
            barEntries.set((int) x - 1, new BarEntry(x, y));
            barChart.invalidate();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(),
                    "Cant make diagram. Check connection or Cells configuration on \"config\" directory",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void addBarData(float x, float y, int color) {
        // set color for x
        colors.set((int) x, color);

        // update/set bar data with index x
        barEntries.set((int) x, new BarEntry(x, y));
        barChart.invalidate();

    }

    private int getBarColor(float voltageData, float min, float max, float allowd) {
        // < 1k grey
        // < min && > max red
        // > min && < allowd yellow

        if (voltageData > allowd && voltageData < max) {
            return this.COLOR_GREEN;
        } else if (voltageData > max || voltageData < min) {
            return this.RED_COLOR;
        } else if (voltageData < allowd && voltageData > min) {
            return this.YELLOW_COLOR;
        } else {
            return this.COLOR_GREY; // if another case return also grey color
        }
    }

    @Override
    public void updateUI(final DataParser data) {
        if (!isInitialized) {
            return;
        }

        String rawData = data.getTransmittedData();            //        volt, temp, temp bal, ...
        String[] rawSplitData = rawData.split(":");     // cell1:  4066, 26.6, 30.6, 5, 8

        final float groupIndex = Float.parseFloat(rawSplitData[0].substring(4));
        final String[] groupValue = rawSplitData[1].split(",");

        final float min = Float.parseFloat(data.getLevelsDataByCmdName("min"));
        final float max = Float.parseFloat(data.getLevelsDataByCmdName("max"));
        final float allowd = Float.parseFloat(data.getLevelsDataByCmdName("allowd"));

        barChart.post(new Runnable() {
            @Override
            public void run() {
                addBarData(groupIndex, Float.parseFloat(groupValue[0]), min, max, allowd);
            }
        });
    }
}
