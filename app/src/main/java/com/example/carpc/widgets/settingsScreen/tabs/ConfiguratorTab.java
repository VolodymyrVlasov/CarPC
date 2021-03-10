package com.example.carpc.widgets.settingsScreen.tabs;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.carpc.MainActivity;
import com.example.carpc.R;
import com.example.carpc.adapters.ConfigAdapter;
import com.example.carpc.models.ConfigData;
import com.example.carpc.network.TCPClient;
import com.example.carpc.utils.DataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ConfiguratorTab extends Fragment {
    private static final String TAG = "GroupConfig";

    private ListView listView;
    private Spinner spinner;
    private ConfigAdapter adapter;
    private String[] spinnerCategories;

    private Button readBtn;
    private Button writeBtn;
    private TextView descriptionTextView;
    private TextView helpTextView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_pc_configuration, container, false);
        spinnerCategories = getContext().getResources().getStringArray(R.array.configurationGroupList);

        spinner = v.findViewById(R.id.configurationGroupList);
        final ArrayAdapter adapter =
                ArrayAdapter.createFromResource(getContext(), R.array.configurationGroupList, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.dropdown_items);

        spinner.setAdapter(adapter);

        readBtn = v.findViewById(R.id.btn_read);
        writeBtn = v.findViewById(R.id.btn_write);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < spinnerCategories.length) {
                    getSelectedSpinnerData(position);
                } else {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                            "Choose true category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromServer(getConfigurationMap().get(spinner.getSelectedItemPosition()));
            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToServer(getConfigurationMap().get(spinner.getSelectedItemPosition()));
            }
        });

        listView = v.findViewById(R.id.configListView);

        descriptionTextView = v.findViewById(R.id.description_text_view);
        helpTextView = v.findViewById(R.id.help_text_view);
        getSelectedSpinnerData(0);
        return v;
    }

    private void writeToServer(String cmdName) {
        if (cmdName.equals("levels")) {
            writeLevelsToSever();
            return;
        }
        TCPClient tcpClient = TCPClient.getInstance(getContext());
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).isConfigValueEmpty()) {
                Toast.makeText(getContext(), "All input fields shouldn`t be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        try {
            StringBuilder resultCommand = new StringBuilder(cmdName + " ");

            for (int i = 0; i < adapter.getCount(); i++) {
                resultCommand.append(adapter.getItem(i).getConfigValue());
                if (i < adapter.getCount() - 1) {
                    resultCommand.append(":");
                }
            }

            tcpClient.sendMessage("..");
            Thread.sleep(3);
            tcpClient.sendMessage("config");
            Thread.sleep(3);
            tcpClient.sendMessage(resultCommand.toString());
            Toast.makeText(getContext(), resultCommand.toString(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Don`t apply", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        MainActivity.hideKeyboard(Objects.requireNonNull(getActivity()));
    }



    private void readFromServer(String cmdName) {
        if (cmdName.equals("levels")) {
            readLevelsFromSever();
            return;
        }
        TCPClient tcpClient = TCPClient.getInstance(getContext());
        try {
            tcpClient.sendMessage("..");
            Thread.sleep(3);
            tcpClient.sendMessage("config");
            Thread.sleep(3);
            tcpClient.sendMessage(cmdName);
            Thread.sleep(100);
            String newConfig = DataParser.getInstance().getCurrentConfig();
            if (!newConfig.contains("try")) {
                String[] arr = newConfig.split(":");

                for (int i = 0; i < arr.length; i++) {
                    adapter.getItem(i).setConfigValue(arr[i]);
                }

                listView.setAdapter(adapter);
                listView.setLayoutParams(setListViewHeight(listView, adapter));
                listView.requestLayout();
            }
            Toast.makeText(getContext(), newConfig, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeLevelsToSever() {
        TCPClient tcpClient = TCPClient.getInstance(getContext());
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).isConfigValueEmpty()) {
                Toast.makeText(getContext(), "All input fields shouldn`t be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        try {
            for (int i = 0; i < adapter.getCount(); i++) {
                String cmdName = adapter.getItem(i).getCmdName();
                String resultCommand = cmdName + " " + adapter.getItem(i).getConfigValue();
                tcpClient.sendMessage("..");
                Thread.sleep(3);
                tcpClient.sendMessage("levels");
                Thread.sleep(3);
                tcpClient.sendMessage(resultCommand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readLevelsFromSever() {
        TCPClient tcpClient = TCPClient.getInstance(getContext());
        try {

            //    ?? levelv> ?? cmin = 4200
            // min = 200
            // max = 5454

            for (int i = 0; i < adapter.getCount(); i++) {
                tcpClient.sendMessage("..");
//                Thread.sleep(1);
                tcpClient.sendMessage("levels");
//                Thread.sleep(1);
                tcpClient.sendMessage(adapter.getItem(i).getCmdName());
//                Thread.sleep(1);
                System.out.println("send message: " + adapter.getItem(i).getCmdName());
                String cmdName = adapter.getItem(i).getCmdName();
                Thread.sleep(10);
                String newConfigValue = DataParser.getInstance().getLevelsDataByCmdName(cmdName);
                Log.i(TAG, "newConfigValue: " + newConfigValue);
                adapter.getItem(i).setConfigValue(newConfigValue);
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSpinnerDescriptions(String descriptionText, String helpText) {
        descriptionTextView.setText(descriptionText);
        helpTextView.setText(helpText);
    }

    private void getSelectedSpinnerData(int configId) {
        HashMap<Integer, String> configurationMap = getConfigurationMap();
        String arrayKeyName = configurationMap.get(configId);
        int idName = this.getResources().getIdentifier(arrayKeyName, "array", getContext().getPackageName());
        String[] rawData = getContext().getResources().getStringArray(idName);
        ArrayList<ConfigData> filteredData = new ArrayList<>();

        for (int i = 2; i < rawData.length; i++) {
            String[] parts = rawData[i].split(",");
            if (parts.length == 2) {
                filteredData.add(new ConfigData(parts[0], parts[1]));
            } else {
                filteredData.add(new ConfigData(parts[0], parts[1], parts[2]));
            }
        }

        adapter = new ConfigAdapter(getContext(), filteredData);
        listView.setAdapter(adapter);
        listView.setLayoutParams(setListViewHeight(listView, adapter));
        listView.requestLayout();
        updateSpinnerDescriptions(rawData[0], rawData[1]);
    }

    private HashMap<Integer, String> getConfigurationMap() {
        HashMap<Integer, String> configurationMap = new HashMap<>();
        configurationMap.put(0, "levels");
        configurationMap.put(1, "current");
        configurationMap.put(2, "сharger");
        configurationMap.put(3, "charging");
        configurationMap.put(4, "ignition");
        configurationMap.put(5, "power");
        configurationMap.put(6, "precharge");
        configurationMap.put(7, "maincontactor");
        configurationMap.put(8, "chcurrentmax");
        return configurationMap;
    }

    private ViewGroup.LayoutParams setListViewHeight(ListView listView, ConfigAdapter adapter){
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        return params;
    }
}



