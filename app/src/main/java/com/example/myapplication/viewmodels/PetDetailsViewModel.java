package com.example.myapplication.viewmodels;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Pet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PetDetailsViewModel extends ViewModel {

    public MutableLiveData<String> PetImage = new MutableLiveData<>();
    public MutableLiveData<String> PetName = new MutableLiveData<>();
    public MutableLiveData<String> PetContent = new MutableLiveData<>();
    public MutableLiveData<Boolean> petDetailsLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void initializePetDetails(Pet petDetail) {
        isLoading.setValue(true);
        PetName.setValue(petDetail.getTitle());
        PetImage.setValue(petDetail.getImageUrl());
        String url = petDetail.getContentUrl();
        DownloadWebPageTask backgroundTask = new DownloadWebPageTask();
        backgroundTask.execute(url);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Document document = null;
            try {
                document = Jsoup.connect(urls[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements paragraphs = null;
            if (document != null) {
                paragraphs = document.select("p");
            }
            StringBuilder builder = new StringBuilder();
            if (paragraphs != null) {
                for (Element p : paragraphs)
                    builder.append(p.text());
            }
            return  builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            PetContent.setValue(result);
            petDetailsLoadError.setValue(false);
            isLoading.setValue(false);
        }
    }
}
