package com.example.carpc.widgets.settingsScreen.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;
import com.example.carpc.instruments.ClientSocket;
import com.example.carpc.instruments.DataParser;
import com.example.carpc.instruments.Message;

import java.util.Timer;
import java.util.TimerTask;

public class TerminalTab extends Fragment implements View.OnClickListener {
    private ClientSocket socket;
    private DataParser parser;
    private ScrollView inputDataScrollView;
    private EditText messageToSend;
    private TextView messages;
    private Button btnSend, btnClear, btnSubscribe, btnUnsubscribe;
    private Message message = MainActivity.getMessage();

    public TerminalTab(ClientSocket socket) {
        this.socket = socket;
        this.parser = MainActivity.getParser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.terminal_tab, container, false);
        inputDataScrollView = v.findViewById(R.id.inputDataScrollView);
        messages = v.findViewById(R.id.inputMessages);
        messageToSend = v.findViewById(R.id.myMessage);
        btnSend = v.findViewById(R.id.btnSendTermTab);
        btnClear = v.findViewById(R.id.btnClearTermTab);
        btnSubscribe = v.findViewById(R.id.btnSubscribe);
        btnUnsubscribe = v.findViewById(R.id.btnUnsubscribe);
        messages.setLines(50);
        message.clearAllText();
        update();
        setRetainInstance(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSend.setOnClickListener((View.OnClickListener) this);
        btnClear.setOnClickListener((View.OnClickListener) this);
        btnSubscribe.setOnClickListener((View.OnClickListener) this);
        btnUnsubscribe.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendTermTab:
                String temp = messageToSend.getText().toString();
                if (!temp.equals("")) sendMessage(temp);
                break;
            case R.id.btnClearTermTab:
                clearAllText();
                break;
            case R.id.btnSubscribe:
                sendMessage(getString(R.string.SUBSCRIBE));
                break;
            case R.id.btnUnsubscribe:
                sendMessage(getString(R.string.UNSUBSCRIBE));
                break;
        }
    }

    public void update() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (message.hasNewMessage()) {
                                messages.setText(message.getMessage() + "\n");
                                inputDataScrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        }
                    });
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1L;
        long period = 1L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    private void sendMessage(String message) {
        //messages.append(">> " + message + "\n");
        this.message.setMessage("OUT>>\t\t" + message, true);
        socket.sendMessage(message);
        messageToSend.setText("");
        inputDataScrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void clearAllText() {
        messages.setText("");
        messageToSend.setText("");
        message.clearAllText();
    }

}