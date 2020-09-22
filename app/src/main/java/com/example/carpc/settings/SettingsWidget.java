package com.example.carpc.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.carpc.R;
import com.example.carpc.instruments.ViewPagerAdapter;
import com.example.carpc.settings.tabs.ConfiguratorTab;
import com.example.carpc.settings.tabs.ConnectionTab;
import com.example.carpc.settings.tabs.TerminalTab;
import com.example.carpc.settings.tabs.UITab;
import com.google.android.material.tabs.TabLayout;

public class SettingsWidget extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_widget, container, false);

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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new ConnectionTab(), "CONNECTION");
        viewPagerAdapter.addFragment(new ConfiguratorTab(), "CONFIGURATOR");
        viewPagerAdapter.addFragment(new TerminalTab(), "TERMINAL");
        viewPagerAdapter.addFragment(new UITab(), "UI");
        viewPager.setAdapter(viewPagerAdapter);
    }
}