package com.example.carpc.widgets.settingsScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.carpc.MainActivity;
import com.example.carpc.R;
import com.example.carpc.instruments.ClientSocket;
import com.example.carpc.instruments.ViewPagerAdapter;
import com.example.carpc.io.DataPrefs;
import com.example.carpc.widgets.settingsScreen.tabs.ConfiguratorTab;
import com.example.carpc.widgets.settingsScreen.tabs.ConnectionTab;
import com.example.carpc.widgets.settingsScreen.tabs.TerminalTab;
import com.example.carpc.widgets.settingsScreen.tabs.UITab;
import com.google.android.material.tabs.TabLayout;

public class SettingsWidget extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    private ClientSocket socket;
    private DataPrefs dataPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_widget, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        tabLayout = v.findViewById(R.id.tabs);

        // data
        dataPrefs = DataPrefs.getInstance(getContext());

        // get connection
        socket = new ClientSocket(dataPrefs.getIP(), dataPrefs.getPort(), MainActivity.getParser());
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
        viewPagerAdapter.addFragment(new ConnectionTab(socket), "CONNECTION");
        viewPagerAdapter.addFragment(new ConfiguratorTab(socket, MainActivity.getParser()), "CONFIGURATOR");
        viewPagerAdapter.addFragment(new TerminalTab(socket), "TERMINAL");
        viewPagerAdapter.addFragment(new UITab(), "UI");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

}