package com.example.carpc.widgets.dashboardScreen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.AppConstants;
import com.example.carpc.widgets.dashboardScreen.tabs.BatteryManagerWidget;
import com.example.carpc.widgets.dashboardScreen.tabs.BatteryWidget;
import com.example.carpc.widgets.dashboardScreen.tabs.IconStatusLeftWidget;
import com.example.carpc.widgets.dashboardScreen.tabs.IconStatusRightWidget;
import com.example.carpc.widgets.dashboardScreen.tabs.MapsFragment;
import com.example.carpc.widgets.dashboardScreen.tabs.SpeedometerWidget;
import com.example.carpc.widgets.dashboardScreen.tabs.TripManagerWidget;

public class DashboardWidget extends Fragment {
    private SpeedometerWidget speedometerWidget;
    private BatteryWidget batteryWidget;
    private BatteryManagerWidget batteryManagerWidget;
    private TripManagerWidget tripManagerWidget;
    private IconStatusRightWidget iconStatusRightWidget;
    private IconStatusLeftWidget iconStatusLeftWidget;
    private androidx.fragment.app.FragmentTransaction fragmentTransaction;

    private MapsFragment mapsFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.widget_dashboard, container, false);

        speedometerWidget = new SpeedometerWidget(); //+
        batteryManagerWidget = new BatteryManagerWidget();
        tripManagerWidget = new TripManagerWidget(); //+
        iconStatusRightWidget = new IconStatusRightWidget();
        iconStatusLeftWidget = new IconStatusLeftWidget();
        batteryWidget = new BatteryWidget(); //+
        mapsFragment = new MapsFragment();

        TCPClient.getInstance(this.getContext()).sendMessage(AppConstants.SUBSCRIBE);

        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.dashboardCenterCont, speedometerWidget);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.dashboardMapCont, mapsFragment);
        }

        //        fragmentTransaction.replace(R.id.dashboardRightUpCont, iconStatusRightWidget);
        //        fragmentTransaction.replace(R.id.dashboardLeftUpCont, iconStatusLeftWidget);
        fragmentTransaction.replace(R.id.dashboardBottomLeftCont, batteryWidget);
        fragmentTransaction.setCustomAnimations(R.animator.left_in, R.animator.right_in).replace(R.id.dashboardLeftCont, batteryManagerWidget);
        fragmentTransaction.setCustomAnimations(R.animator.left_out, R.animator.right_out).replace(R.id.dashboardRightCont, tripManagerWidget);


        fragmentTransaction.commit();

        return v;
    }
}