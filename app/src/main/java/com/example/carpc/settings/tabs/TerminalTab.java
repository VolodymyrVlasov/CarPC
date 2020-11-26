package com.example.carpc.settings.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;

public class TerminalTab extends Fragment implements View.OnClickListener {
    @SuppressLint("StaticFieldLeak")
    private static TextView receivedMessages, transceiverMessages;
    @SuppressLint("StaticFieldLeak")
    private static ScrollView inputDataScrollView;
    @SuppressLint("StaticFieldLeak")
    private static ScrollView outputDataScrollView;
    private int lineCount = 0;
    EditText message;
    Button btnSend, btnClear, btnSubscribe, btnUnsubscribe;
    public static boolean updateFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.terminal_tab, container, false);
        inputDataScrollView = v.findViewById(R.id.inputDataScrollView);
        outputDataScrollView = v.findViewById(R.id.outputDataScrollView);

        receivedMessages = v.findViewById(R.id.inputMessages);
        receivedMessages.setLines(20);
        transceiverMessages = v.findViewById(R.id.outputMessages);
        transceiverMessages.setLines(3);
        message = v.findViewById(R.id.myMessage);
        btnSend = v.findViewById(R.id.btnSendTermTab);
        btnClear = v.findViewById(R.id.btnClearTermTab);
        btnSubscribe = v.findViewById(R.id.btnSubscribe);
        btnUnsubscribe = v.findViewById(R.id.btnUnsubscribe);
        setRetainInstance(true);
        System.out.println("Create TERMINAL TAB");
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        updateFlag = true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateFlag = false;
        System.out.println("Destroy TERMINAL TAB");

    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendTermTab:
                String temp = message.getText().toString();
                if (!temp.equals("")) {
                    sendMessage(message.getText().toString());
                }
                Toast.makeText(getContext(), "Button SEND clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnClearTermTab:
                receivedMessages.setText("");
                transceiverMessages.setText("");
                Toast.makeText(getContext(), "Button CLEAR clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSubscribe:
                sendMessage("@a1");
                updateFlag = true;
                break;
            case R.id.btnUnsubscribe:
                sendMessage("@a0");
                break;
            default:
                break;
        }
    }

    public void showNewMessage(String inputData) {
        System.out.println("showNewMessage " + inputData);
        if (updateFlag) {
            if (!inputData.equals("")) {
                receivedMessages.append("\n>");
                receivedMessages.append(inputData);
                inputDataScrollView.fullScroll(View.FOCUS_DOWN);
            }
        }
    }

    private void sendMessage(String message) {
        transceiverMessages.append("\n" + this.message.getText().toString());
        outputDataScrollView.fullScroll(View.FOCUS_DOWN);
        MainActivity.sendMessage(message);
    }
}