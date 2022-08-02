package com.example.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {

    @SerializedName("startWorkHours")
    @Expose
    private String startWorkHours;
    @SerializedName("endWorkHours")
    @Expose
    private String endWorkHours;
    @SerializedName("onlyWeekDays")
    @Expose
    private String onlyWeekDays;

    public String getStartWorkHours() {
        return startWorkHours;
    }

    public void setStartWorkHours(String startWorkHours) {
        this.startWorkHours = startWorkHours;
    }

    public String getEndWorkHours() {
        return endWorkHours;
    }

    public void setEndWorkHours(String endWorkHours) {
        this.endWorkHours = endWorkHours;
    }

    public String getOnlyWeekDays() {
        return onlyWeekDays;
    }

    public void setOnlyWeekDays(String onlyWeekDays) {
        this.onlyWeekDays = onlyWeekDays;
    }
}