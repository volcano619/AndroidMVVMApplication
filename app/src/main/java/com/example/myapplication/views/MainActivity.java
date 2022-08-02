package com.example.myapplication.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.PetsListAdapter;
import com.example.myapplication.viewmodels.PetsListViewModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private RecyclerView PetsRecyclerView;
    private ProgressBar PetsLoaderProgressView;
    private TextView NoDataFoundTextView;
    private PetsListViewModel ViewModel;
    private final PetsListAdapter PetsListAdapter = new PetsListAdapter(new ArrayList<>());
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PetsRecyclerView = findViewById(R.id.recyclerView);
        PetsLoaderProgressView = findViewById(R.id.progressBar);
        NoDataFoundTextView = findViewById(R.id.textView);
        ViewModel = new ViewModelProvider(this).get(PetsListViewModel.class);
        ViewModel.refresh(getApplicationContext());
        PetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PetsRecyclerView.setAdapter(PetsListAdapter);
        ObserverViewModel();
    }

    private void ObserverViewModel()
    {
        ViewModel.Pets.observe(this, petsList -> {
            if(petsList != null)
            {
                PetsRecyclerView.setVisibility(View.VISIBLE);
                PetsListAdapter.updatePetsList(petsList);
            }
        });

        ViewModel.petsLoadError.observe(this, petsLoadError -> {
            if (petsLoadError != null)
            {
                NoDataFoundTextView.setVisibility(petsLoadError ? View.VISIBLE : View.GONE);
            }
        });

        ViewModel.isLoading.observe(this, IsLoadingStatus -> {
            if(IsLoadingStatus)
            {
                PetsLoaderProgressView.setVisibility(View.VISIBLE);
            }
            else
            {
              PetsLoaderProgressView.setVisibility(View.INVISIBLE);
            }
        });
    }
}