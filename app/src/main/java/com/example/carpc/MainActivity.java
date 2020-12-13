package com.example.carpc;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.carpc.instruments.DataBase;
import com.example.carpc.instruments.DataParser;
import com.example.carpc.instruments.Message;
import com.example.carpc.widgets.chargeScreen.ChargeWidget;
import com.example.carpc.widgets.dashboardScreen.DashboardWidget;
import com.example.carpc.widgets.settingsScreen.SettingsWidget;
import com.example.carpc.widgets.settingsScreen.tabs.TerminalTab;

import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "myLogs";
    private static DataParser parser;
    private static Message message;
    private static DataBase db;
    private DashboardWidget dashboardWidget;
    private SettingsWidget settingsWidget;
    private ChargeWidget chargeWidget;
    private androidx.fragment.app.FragmentTransaction fTrans;
    private Map<String, String> map;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parser = new DataParser();
        message = new Message();
        db = new DataBase("car_pc_db", this);
        dashboardWidget = new DashboardWidget();
        settingsWidget = new SettingsWidget();
        chargeWidget = new ChargeWidget();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCont, dashboardWidget);
        fTrans.commit();
        map = db.getAllValues();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - " + entry.getValue());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.writeDataBase();
    }

    public void onClick(View v) {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (v.getId()) {
            case R.id.btnCharge:
                if (!chargeWidget.isVisible()) fTrans.replace(R.id.frgmCont, chargeWidget);
                break;
            case R.id.btnDashboard:
                if (!dashboardWidget.isVisible()) fTrans.replace(R.id.frgmCont, dashboardWidget);
                break;
            case R.id.btnSettings:
                if (!settingsWidget.isVisible()) fTrans.replace(R.id.frgmCont, settingsWidget);
                break;
        }
        fTrans.commit();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static DataParser getParser() {
        return parser;
    }

    public static Message getMessage() {
        return message;
    }

    public static Context getContext() {
        return getContext();
    }

    public static DataBase getDataBase() {
        return db;
    }

}