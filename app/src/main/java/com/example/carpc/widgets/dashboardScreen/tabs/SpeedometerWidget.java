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
import com.example.carpc.instruments.DataParser;
import java.util.Timer;
import java.util.TimerTask;

public class SpeedometerWidget extends Fragment {

    public static TextView speedTextView;
    public static int speedToSet = 0;
    DataParser parser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.speedometer_widget, null);
        speedTextView = v.findViewById(R.id.speedDashboard);
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
                            if (speedToSet <= parser.getSpeed()) {
                                speedToSet += 1;
                                speedTextView.setText(String.valueOf(speedToSet));
                            }
                            if (speedToSet > parser.getSpeed()) {
                                speedToSet -= 1;
                                speedTextView.setText(String.valueOf(speedToSet));
                            }
                        }
                    });
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 50L;
        long period = 50L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
}
