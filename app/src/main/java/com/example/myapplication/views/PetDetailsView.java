package com.example.myapplication.views;

import android.net.Uri;
import android.os.Bundle;
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
    private PetDetailsViewModel ViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details_view);
        PetDetailImage = findViewById(R.id.petDetailsImageView);
        PetDetailTextView = findViewById(R.id.petDetailsNameView);
        PetDetailContentView = findViewById(R.id.petDetailsContentView);
        ViewModel = new ViewModelProvider(this).get(PetDetailsViewModel.class);
        GetPetDetails();
        ObserverViewModel();
    }

    private void ObserverViewModel()
    {
        ViewModel.PetContent.observe(this, petContent -> {
            if(petContent != null)
            {
                PetDetailContentView.setText(petContent);
            }
        });

        ViewModel.PetImage.observe(this, petContentImage -> {
            if(petContentImage != null)
            {
                Picasso.get().load(Uri.parse(petContentImage)).into(PetDetailImage);
            }
        });

        ViewModel.PetName.observe(this, petName -> {
            if (petName != null)
            {
                PetDetailTextView.setText(ViewModel.PetName.getValue());
            }
        });

        ViewModel.isLoading.observe(this, IsLoadingStatus -> {
        });
    }

    private void GetPetDetails()
    {
        String PetDetailsSerialized = getIntent().getExtras().get("PetDetails").toString();
        Pet PetDetails = new Gson().fromJson(PetDetailsSerialized, Pet.class);
        ViewModel.initializePetDetails(PetDetails);
    }
}