package com.example.myapplication.viewholder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Pet;
import com.squareup.picasso.Picasso;

public class PetsViewHolder extends RecyclerView.ViewHolder {


    ImageView PetImageView;
    TextView PetTextView;
    

    public PetsViewHolder(@NonNull View itemView) {
        super(itemView);
        PetImageView = itemView.findViewById(R.id.petImageView);
        PetTextView = itemView.findViewById(R.id.petNameTextView);
    }

    public void bind(Pet pets){
        PetTextView.setText(pets.getTitle());
        Picasso.get().load(Uri.parse(pets.getImageUrl())).into(PetImageView);
    }
}
