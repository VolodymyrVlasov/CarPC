package com.example.carpc.widgets.settingsScreen.tabs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.carpc.MainActivity;
import com.example.carpc.R;
import com.example.carpc.models.Message;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.AppConstants;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.dashboardScreen.AbstractDashboardWidget;

public class TerminalTab extends AbstractDashboardWidget implements View.OnClickListener {
    private ScrollView inputDataScrollView;
    private EditText messageToSend;
    private TextView messagesTextView;
    private Button btnSend, btnClear, btnSubscribe, btnUnsubscribe;
    private Message message = MainActivity.getMessage();

    public TerminalTab() {
    }

    @Override
    public void updateUI(final DataParser data) {
        messagesTextView.post(new Runnable() {
            @Override
            public void run() {
                if (message.hasNewMessage()) {
                    messagesTextView.setText(message.getMessage());
                    inputDataScrollView.fullScroll(View.FOCUS_DOWN);
                }
//                Log.i("TERMINALTAB", data.getInputLine());
//                messagesTextView.append(data.getInputLine() + "\n");
//                inputDataScrollView.fullScroll(View.FOCUS_DOWN);

            }
        });
//        messagesTextView.setText(data.getInputLine());
//        inputDataScrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.terminal_tab, container, false);
        inputDataScrollView = v.findViewById(R.id.inputDataScrollView);
        messagesTextView = v.findViewById(R.id.inputMessages);
        messageToSend = v.findViewById(R.id.myMessage);
        btnSend = v.findViewById(R.id.btnSendTermTab);
        btnClear = v.findViewById(R.id.btnClearTermTab);
        btnSubscribe = v.findViewById(R.id.btnSubscribe);
        btnUnsubscribe = v.findViewById(R.id.btnUnsubscribe);
//        messages.setLines(50);
//        message.clearAllText();

        messageToSend.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String temp = messageToSend.getText().toString();
                    if (!temp.equals("")) sendMessage(temp);
                }
                return false;
            }
        });
//        update();
        //TODO set Enter key onclick listener to send message
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
                sendMessage(AppConstants.SUBSCRIBE);
                break;
            case R.id.btnUnsubscribe:
                sendMessage(AppConstants.UNSUBSCRIBE);
                break;
        }
    }


//    public void update() {
//        TimerTask repeatedTask = new TimerTask() {
//            public void run() {
//                if (getActivity() != null) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @SuppressLint("SetTextI18n")
//                        @Override
//                        public void run() {
//                            if (message.hasNewMessage()) {
//                                messages.setText(message.getMessage());
//                                inputDataScrollView.fullScroll(View.FOCUS_DOWN);
//                            }
//                        }
//                    });
//                }
//            }
//        };
//        Timer timer = new Timer("Timer");
//        long delay = 1L;
//        long period = 10L;
//        timer.scheduleAtFixedRate(repeatedTask, delay, period);
//    }

    private void sendMessage(String message) {
//        this.message.setMessage("\t\t\t\tOUT>>\t\t" + message, true);
        TCPClient.getInstance(getContext()).sendMessage(message);
        messageToSend.setText("");
        //  messageToSend.setFocusable(true);
//        inputDataScrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void clearAllText() {
        messagesTextView.setText("");
        messageToSend.setText("");
//        message.clearAllText();
    }
}