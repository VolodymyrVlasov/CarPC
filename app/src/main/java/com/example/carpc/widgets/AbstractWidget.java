package com.example.carpc.widgets;

import androidx.fragment.app.Fragment;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.DataParser;

public abstract class AbstractWidget extends Fragment implements TCPClient.TCPClientListener {
    public AbstractWidget() {
        TCPClient.getInstance(getContext()).addNetworkListener(this);
    }

    public abstract void updateUI(DataParser data);

    @Override
    public void OnDataReceive(DataParser data) {
        if (this.getView() != null && this.isVisible()) {
            this.updateUI(data);
        }
    }
}
