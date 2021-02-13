package com.example.carpc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carpc.R;
import com.example.carpc.models.ConfigData;

import java.util.ArrayList;


public class ConfigAdapter extends BaseAdapter {
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
        return data.size();
    }

    @Override
    public ConfigData getItem(int i) {
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
        editText.setHint(data.get(i).getConfigHint());

        return v;
    }
}