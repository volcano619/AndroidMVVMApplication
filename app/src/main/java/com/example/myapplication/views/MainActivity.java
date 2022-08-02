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
    private TextView NotAccessibleTextView;
    private PetsListViewModel ViewModel;
    private final PetsListAdapter PetsListAdapter = new PetsListAdapter(new ArrayList<>());
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PetsRecyclerView = findViewById(R.id.recyclerView);
        PetsLoaderProgressView = findViewById(R.id.progressBar);
        NoDataFoundTextView = findViewById(R.id.textView);
        NotAccessibleTextView = findViewById(R.id.notAllowedTextView);
        ViewModel = new ViewModelProvider(this).get(PetsListViewModel.class);
        ViewModel.checkWorkingHour(this.getApplicationContext());
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
                if(Boolean.TRUE.equals(ViewModel.isWorkingHour.getValue()))
                {
                    PetsRecyclerView.setVisibility(View.VISIBLE);
                    PetsListAdapter.updatePetsList(petsList);
                }
                else
                {
                    NotAccessibleTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        ViewModel.petsLoadError.observe(this, petsLoadError -> {
            if (petsLoadError != null)
            {
                if(Boolean.TRUE.equals(ViewModel.isWorkingHour.getValue()))
                {
                    NoDataFoundTextView.setVisibility(petsLoadError ? View.VISIBLE : View.GONE);
                }
                else
                {
                    NotAccessibleTextView.setVisibility(View.VISIBLE);
                }
            }

        });

        ViewModel.isLoading.observe(this, IsLoadingStatus -> {
            if(Boolean.TRUE.equals(ViewModel.isWorkingHour.getValue()))
            {
                if(IsLoadingStatus)
                {
                    PetsLoaderProgressView.setVisibility(View.VISIBLE);
                }
                else
                {
                    PetsLoaderProgressView.setVisibility(View.INVISIBLE);
                }
            }
            else
            {
                NotAccessibleTextView.setVisibility(View.VISIBLE);
            }
        });

        ViewModel.isWorkingHour.observe(this, IsWorkingHour -> NotAccessibleTextView.setVisibility(IsWorkingHour ? View.GONE : View.VISIBLE));
    }
}