package com.example.carpc.widgets.dashboardScreen.tabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.carpc.R;

public class SpeedometerWidget extends Fragment {
    private int speedToSet = 0;

    public TextView speedTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.speedometer_widget, null);
        speedTextView = v.findViewById(R.id.speedDashboard);
        setRetainInstance(true);
        return v;
    }

    public void setSpeedText(int speed) {
        if (speedToSet <= speed) {
            speedToSet += 1;
            speedTextView.setText(String.valueOf(speedToSet));
        }
        if (speedToSet > speed) {
            speedToSet -= 1;
            speedTextView.setText(String.valueOf(speedToSet));
        }
    }
}
