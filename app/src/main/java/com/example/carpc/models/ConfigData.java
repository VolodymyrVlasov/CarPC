package com.example.carpc.models;


public class ConfigData {
    private String configItem;
    private String configHint;
    private String configValue;
    private String cmdName;

    public ConfigData(String configItem, String configHint) {
        this.configItem = configItem;
        this.configHint = configHint;
        this.configValue = "";

    }

    public ConfigData(String configItem, String cmdName, String configHint) {
        this.configItem = configItem;
        this.configHint = configHint;
        this.cmdName = cmdName;
        this.configValue = "";
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

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public boolean isConfigValueEmpty() {
        return configValue.isEmpty();
    }
}