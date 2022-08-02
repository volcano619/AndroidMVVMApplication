package com.example.myapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.models.Pet;
import com.example.myapplication.models.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class PetsListViewModel extends ViewModel {
    public MutableLiveData<List<Pet>> Pets = new MutableLiveData<>();
    public MutableLiveData<Boolean> petsLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void refresh(Context ctx) {
        fetchPets(ctx);
    }

    public void fetchPets(Context ctx) {
        isLoading.setValue(true);
        try {
            InputStream result = ctx.getResources().openRawResource(R.raw.pets);
            Reader reader = new InputStreamReader(result);
            Response response = new Gson().fromJson(reader, new TypeToken<Response>() {
            }.getType());
            reader.close();
            Pets.setValue(response.getPets());
            petsLoadError.setValue(false);
            isLoading.setValue(false);
        } catch (IOException e)
        {
            e.printStackTrace();
            petsLoadError.setValue(true);
            isLoading.setValue(false);
        }
    }
}
