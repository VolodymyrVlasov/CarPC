package com.example.carpc.widgets.startScreen;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.carpc.R;

public class StartScreenWidget extends Fragment {
    private androidx.fragment.app.FragmentTransaction fTrans;
    private StartScreenMenu startScreenMenu;



    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.widget_start_screen, container, false);
        startScreenMenu = new StartScreenMenu();

        fTrans = getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.left_in, R.animator.right_in)
                .replace(R.id.button_container, startScreenMenu)
                .addToBackStack(null);

        fTrans.commit();
        return v;
    }
}