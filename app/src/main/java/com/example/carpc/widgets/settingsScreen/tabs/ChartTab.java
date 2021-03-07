package com.example.carpc.widgets.settingsScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import java.util.ArrayList;

//        String[] arr = new String[]{"Cell 5: 4055, 26.6, 30.6, 5, 8",
//                "Cell 4: 4066, 26.6, 30.6, 5, 8",
//                "Cell 3: 4057, 26.5, 30.6, 5, 8",
//                "Cell 2: 4045, 26.6, 30.5, 5, 8",
//                "Cell 1: 4067, 26.6, 30.5, 5, 8"};
public class ChartTab extends Fragment {

    private BarChart barChart;
    private static final String chartName = "Name";

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_tab, container, false);
        setRetainInstance(true);

        barChart = (BarChart) v.findViewById(R.id.lineChart);

        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(0, 4055));
        barEntries.add(new BarEntry(0, 4066));
        barEntries.add(new BarEntry(0, 4057));
        barEntries.add(new BarEntry(0, 4045));
        barEntries.add(new BarEntry(0, 4067));
        BarDataSet barDataSet = new BarDataSet(barEntries, chartName);

        BarData data = new BarData(barDataSet);

        barChart.setData(data);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy CHART TAB");
    }
}
