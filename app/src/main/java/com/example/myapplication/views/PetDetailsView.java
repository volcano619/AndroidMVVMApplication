package com.example.myapplication.views;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.models.Pet;
import com.example.myapplication.viewmodels.PetDetailsViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class PetDetailsView extends AppCompatActivity {

    private ImageView PetDetailImage;
    private TextView PetDetailTextView;
    private  TextView PetDetailContentView;
    private TextView NotAccessibleTextView;
    private PetDetailsViewModel ViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details_view);
        PetDetailImage = findViewById(R.id.petDetailsImageView);
        PetDetailTextView = findViewById(R.id.petDetailsNameView);
        PetDetailContentView = findViewById(R.id.petDetailsContentView);
        ViewModel = new ViewModelProvider(this).get(PetDetailsViewModel.class);
        ViewModel.checkWorkingHour(this.getApplicationContext());
        GetPetDetails();
        ObserverViewModel();
    }

    private void ObserverViewModel()
    {
        ViewModel.PetContent.observe(this, petContent -> {
            if(petContent != null)
            {
                if(Boolean.TRUE.equals(ViewModel.isWorkingHour.getValue()))
                {
                    PetDetailContentView.setText(petContent);
                }
                else
                {
                    NotAccessibleTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        ViewModel.PetImage.observe(this, petContentImage -> {
            if(petContentImage != null) {
                if (Boolean.TRUE.equals(ViewModel.isWorkingHour.getValue())) {

                    Picasso.get().load(Uri.parse(petContentImage)).into(PetDetailImage);
                } else {
                    NotAccessibleTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        ViewModel.PetName.observe(this, petName -> {

            if (petName != null)
            {
                if (Boolean.TRUE.equals(ViewModel.isWorkingHour.getValue())) {

                    PetDetailTextView.setText(ViewModel.PetName.getValue());
                } else {
                    NotAccessibleTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        ViewModel.isLoading.observe(this, IsLoadingStatus -> {
        });

        ViewModel.isWorkingHour.observe(this, IsWorkingHour -> {
            NotAccessibleTextView.setVisibility(IsWorkingHour ? View.GONE : View.VISIBLE);
        });
    }

    private void GetPetDetails()
    {
        String PetDetailsSerialized = getIntent().getExtras().get("PetDetails").toString();
        Pet PetDetails = new Gson().fromJson(PetDetailsSerialized, Pet.class);
        ViewModel.initializePetDetails(PetDetails);
    }
}