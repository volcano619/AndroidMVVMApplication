package com.example.myapplication.viewmodels;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.models.Settings;
import com.example.myapplication.models.SettingsResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class BaseViewModel extends ViewModel
{
    public MutableLiveData<Settings> appSettings = new MutableLiveData<>();
    public MutableLiveData<Boolean> isWorkingHour = new MutableLiveData<>();

    public void checkWorkingHour(Context ctx)
    {
        try {
            InputStream result = ctx.getResources().openRawResource(R.raw.pets);
            Reader reader = new InputStreamReader(result);
            SettingsResponse response = new Gson().fromJson(reader, new TypeToken<SettingsResponse>() {
            }.getType());
            appSettings.setValue(response.getSettings());
            reader.close();
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            Date StartTime = formatter.parse(appSettings.getValue().getStartWorkHours());
            Date EndTime = formatter.parse(appSettings.getValue().getEndWorkHours());
            new Thread(() -> {
                while(true){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Date currentTime = Calendar.getInstance().getTime();
                    if(currentTime.getTime() < EndTime.getTime() && currentTime.getTime() > StartTime.getTime()){
                        isWorkingHour.setValue(true);
                    }
                    else {
                        isWorkingHour.setValue(false);
                    }
                }
            }).start();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
