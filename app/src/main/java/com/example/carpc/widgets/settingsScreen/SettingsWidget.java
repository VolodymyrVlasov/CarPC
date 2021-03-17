package com.example.carpc.widgets.settingsScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.carpc.R;
import com.example.carpc.adapters.ViewPagerAdapter;
import com.example.carpc.widgets.settingsScreen.tabs.ConfiguratorTab;
import com.example.carpc.widgets.settingsScreen.tabs.ConnectionTab;
import com.example.carpc.widgets.settingsScreen.tabs.ChartTab;
import com.example.carpc.widgets.settingsScreen.tabs.TerminalTab;
import com.example.carpc.widgets.settingsScreen.tabs.UITab;
import com.google.android.material.tabs.TabLayout;

public class SettingsWidget extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.widget_settings, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        tabLayout = v.findViewById(R.id.tabs);

        setRetainInstance(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new ConnectionTab(), "CONNECTION");
        viewPagerAdapter.addFragment(new ConfiguratorTab(), "CONFIG");
        viewPagerAdapter.addFragment(new TerminalTab(), "TERMINAL");
        viewPagerAdapter.addFragment(new UITab(), "UI");
        viewPagerAdapter.addFragment(new ChartTab(), "CELLS");

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        socket.disconnect();
    }

}