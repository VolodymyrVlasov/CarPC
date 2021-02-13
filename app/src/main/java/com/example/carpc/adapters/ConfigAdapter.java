package com.example.carpc.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
    private TextView editText;

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
        final ConfigData cfgData = data.get(i);
        editText = v.findViewById(R.id.config_value);
        if (cfgData.getConfigValue().isEmpty()) {
            editText.setHint(cfgData.getConfigHint());
        } else {
            editText.setText(cfgData.getConfigValue());
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                cfgData.setConfigValue(s);
            }
        });
        return v;
    }

    public TextView getEditText() {
        return editText;
    }

    public void setEditText(TextView editText) {
        this.editText = editText;
    }
}