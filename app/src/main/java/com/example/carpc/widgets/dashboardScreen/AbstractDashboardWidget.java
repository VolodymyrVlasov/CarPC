package com.example.carpc.widgets.dashboardScreen;

import androidx.fragment.app.Fragment;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.DataParser;

public abstract class AbstractDashboardWidget extends Fragment implements TCPClient.TCPClientListener {
    public AbstractDashboardWidget() {
        TCPClient.getInstance(getContext()).addNetworkListener(this);
    }

    public abstract void updateUI(DataParser data);

    @Override
    public void OnDataReceive(DataParser data) {
        if (this.getView() != null) {
            this.updateUI(data);
        }
    }
}
