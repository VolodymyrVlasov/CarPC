package com.example.carpc.widgets.settingsScreen.tabs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.carpc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ConfiguratorTab extends Fragment {
    private static final String TAG = "GroupConfig";
    private androidx.fragment.app.FragmentTransaction fragmentTransaction;

    private ListView listView;
    private Spinner spinner;
    private ConfigAdapter adapter;
    private String[] spinnerCategories;


    public ConfiguratorTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_pc_configuration, container, false);
        spinnerCategories = getContext().getResources().getStringArray(R.array.configurationGroupList);

        spinner = v.findViewById(R.id.configurationGroupList);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getContext(), R.array.configurationGroupList, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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

        listView = v.findViewById(R.id.configListView);
        getSelectedSpinnerData(0);
        return v;
    }

    private void getSelectedSpinnerData(int configId) {
        HashMap<Integer, String> configurationMap = getConfigurationMap();
        String arrayKeyName = configurationMap.get(configId);
        int idName = this.getResources().getIdentifier(arrayKeyName, "array", getContext().getPackageName());

        String[] rawData = getContext().getResources().getStringArray(idName);
        ArrayList<ConfigData> filteredData = new ArrayList<>();
        for (String s : rawData) {
            String[] parts = s.split(",");
            if (parts.length == 2) {
                filteredData.add(new ConfigData(parts[0], parts[1]));
            } else {
                filteredData.add(new ConfigData(parts[0], parts[1], parts[2]));
            }
        }

        adapter = new ConfigAdapter(getContext(), filteredData);
        listView.setAdapter(adapter);
    }

    private HashMap<Integer, String> getConfigurationMap() {
        HashMap<Integer, String> configurationMap = new HashMap<>();
        configurationMap.put(0, "levels");
        configurationMap.put(1, "current");
        return configurationMap;
    }
}


class ConfigData {
    private String configItem;
    private String configHint;
    private String cmdName;

    public ConfigData(String configItem, String configHint) {
        this.configItem = configItem;
        this.configHint = configHint;
    }

    public ConfigData(String configItem, String cmdName, String configHint) {
        this.configItem = configItem;
        this.configHint = configHint;
        this.cmdName = cmdName;
    }

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getConfigItem() {
        return configItem;
    }

    public void setConfigItem(String configItem) {
        this.configItem = configItem;
    }

    public String getConfigHint() {
        return configHint;
    }

    public void setConfigHint(String configHint) {
        this.configHint = configHint;
    }
}


class ConfigAdapter extends BaseAdapter {
    Context context;
    ArrayList<ConfigData> data;
    private static LayoutInflater inflater = null;

    public ConfigAdapter(Context context, ArrayList<ConfigData> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size() ;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = inflater.inflate(R.layout.config_line, null);
        }

        // parameter number text
        TextView textNumber = (TextView) v.findViewById(R.id.parameter_num);
        textNumber.setText(String.valueOf(i + 1));
        // parameter text
        TextView parameterText = (TextView) v.findViewById(R.id.config_title);
        parameterText.setText(data.get(i).getConfigItem());
        //edit text
        TextView editText = v.findViewById(R.id.config_value);
        editText.setText(data.get(i).getConfigHint());

        return v;
    }
}