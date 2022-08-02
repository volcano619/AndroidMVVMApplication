package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Pet;
import com.example.myapplication.viewholder.PetsViewHolder;
import com.example.myapplication.views.PetDetailsView;
import com.google.gson.Gson;

import java.util.List;

public class PetsListAdapter extends RecyclerView.Adapter<PetsViewHolder> {

    private final List<Pet> petsList;

    public PetsListAdapter(List<Pet> petsList){
        this.petsList = petsList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updatePetsList(List<Pet> newPetsList){
        petsList.clear();
        petsList.addAll(newPetsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pets_item,parent,false);
       return new PetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsViewHolder holder, int position) {
        holder.bind(petsList.get(position));
        holder.itemView.setOnClickListener(view -> {
            Pet currentPet = petsList.get(holder.getAdapterPosition());
            Intent PetDetailIntent = new Intent(view.getContext(), PetDetailsView.class);
            String PetDetailsSerialized = new Gson().toJson(currentPet);
            PetDetailIntent.putExtra("PetDetails",PetDetailsSerialized);
            view.getContext().startActivity(PetDetailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return petsList.size();
    }
}
