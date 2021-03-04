package com.example.carpc;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.carpc.models.DataPrefs;
import com.example.carpc.models.Message;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.AppConstants;
import com.example.carpc.utils.Counter;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.chargeScreen.ChargeWidget;
import com.example.carpc.widgets.dashboardScreen.DashboardWidget;
import com.example.carpc.widgets.settingsScreen.SettingsWidget;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static Message message;
    private DataPrefs dataPrefs;
    private DashboardWidget dashboardWidget;
    private SettingsWidget settingsWidget;
    private ChargeWidget chargeWidget;
    private androidx.fragment.app.FragmentTransaction fTrans;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = new Message();
        dataPrefs = DataPrefs.getInstance(this);
        Counter.setUsedAH(dataPrefs.getUsedAmpereHour());
        Counter.setUsedWH(dataPrefs.getUsedWattHour());
        // fragments
        dashboardWidget = new DashboardWidget();
        settingsWidget = new SettingsWidget();
        chargeWidget = new ChargeWidget();

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCont, dashboardWidget);
        fTrans.commit();

//        FOR TESTING
        Map<String, ?> allData = DataPrefs.getInstance(getApplicationContext()).GetAll();
        for (Map.Entry<String, ?> entry : allData.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - " + entry.getValue());
        }
        TCPClient.getInstance(this).sendMessage(AppConstants.SUBSCRIBE);
        TCPClient.getInstance(this).setTCPClientListener(new TCPClient.TCPClientListener() {
            @Override
            public void OnDataReceive(DataParser data) {
                if (dashboardWidget.isVisible()) {
                    dashboardWidget.getSpeedometerWidget().setSpeedText(data.getSpeed());
                    dashboardWidget.getBatteryWidget().updateWidgetUI(data.getRange(), data.getFirstTempSensorValue(), data.getBatteryCapacity());
                    dashboardWidget.getTripManagerWidget().updateWidgetUI(data.getLastChargePassedDistance(), data.getRange(), data.getTotalDistance());
                    dashboardWidget.getBatteryManagerWidget().updateWidgetUI(data.getCurrent(), data.getVoltage(), data.getBatteryCapacity());
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        dataPrefs.setUsedAmpereHour(Counter.getUsedAH());
        dataPrefs.setUsedWattHour(Counter.getUsedWH());
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
                TCPClient.getInstance(this).sendMessage(AppConstants.SUBSCRIBE);
                break;
            case R.id.btnSettings:
                if (!settingsWidget.isVisible()) fTrans.replace(R.id.frgmCont, settingsWidget);
                TCPClient.getInstance(this).sendMessage(AppConstants.UNSUBSCRIBE);
                break;
        }
        fTrans.commit();
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Message getMessage() {
        return message;
    }

}