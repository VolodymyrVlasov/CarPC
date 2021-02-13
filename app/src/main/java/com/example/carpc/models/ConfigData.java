package com.example.carpc.models;


public class ConfigData {
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