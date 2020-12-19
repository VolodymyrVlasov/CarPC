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

public class SpeedometerWidget extends Fragment {
    private static final long DELAY = 50L;
    private static final long PERIOD = 50L;
    private static int speedToSet = 0;

    public TextView speedTextView;
    private DataParser parser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.speedometer_widget, null);
        speedTextView = v.findViewById(R.id.speedDashboard);
        parser = MainActivity.getParser();
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
//        long delay = 50L;
//        long period = 50L;
        timer.scheduleAtFixedRate(repeatedTask, DELAY, PERIOD);
    }
}
