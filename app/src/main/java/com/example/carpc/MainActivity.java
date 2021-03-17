package com.example.carpc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.carpc.models.DataPrefs;
import com.example.carpc.models.Message;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.AppConstants;
import com.example.carpc.utils.Counter;
import com.example.carpc.widgets.StartScreenWidget;
import com.example.carpc.widgets.chargeScreen.ChargeWidget;
import com.example.carpc.widgets.dashboardScreen.DashboardWidget;
import com.example.carpc.widgets.settingsScreen.SettingsWidget;

public class MainActivity extends AppCompatActivity {
    private static Message message;
    private DataPrefs dataPrefs;
    private DashboardWidget dashboardWidget;
    private SettingsWidget settingsWidget;
    private ChargeWidget chargeWidget;
    private StartScreenWidget startScreenWidget;
    private androidx.fragment.app.FragmentTransaction fTrans;
    private LinearLayout rootContainer;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootContainer = findViewById(R.id.root_container);

        message = new Message();
        dataPrefs = DataPrefs.getInstance(this);
        Counter.setUsedAH(dataPrefs.getUsedAmpereHour());
        Counter.setUsedWH(dataPrefs.getUsedWattHour());

        try {
            TCPClient.getInstance(this);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // fragments
        dashboardWidget = new DashboardWidget();
        settingsWidget = new SettingsWidget();
        chargeWidget = new ChargeWidget();
        startScreenWidget = new StartScreenWidget();

        fTrans = getSupportFragmentManager().beginTransaction();

        if (TCPClient.getInstance(this).isConnected()) {
            fTrans.add(R.id.frgmCont, dashboardWidget);
            TCPClient.getInstance(this).sendMessage(AppConstants.SUBSCRIBE);
        } else {
            Toast.makeText(this, "Cannot connect to server!\n" +
                    "Please, check connection params in settings", Toast.LENGTH_LONG).show();
            fTrans.add(R.id.frgmCont, startScreenWidget);
        }
        fTrans.addToBackStack(null);

        fTrans.commit();

        rootContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeWidget();
                return false;
            }
        });
    }

    public void changeWidget() {
        fTrans = getSupportFragmentManager().beginTransaction();
        Log.i("MAIN_ACTIVITY", "OnLongClick");
        fTrans.replace(R.id.frgmCont, settingsWidget);
        TCPClient.getInstance(this).sendMessage(AppConstants.UNSUBSCRIBE);
        fTrans.addToBackStack(null);

        fTrans.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataPrefs.setUsedAmpereHour(Counter.getUsedAH());
        dataPrefs.setUsedWattHour(Counter.getUsedWH());
    }

    @SuppressLint("NonConstantResourceId")
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
        fTrans.addToBackStack(null);
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