package com.example.catapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>{

    public static ArrayList<CatModel> favCats = new ArrayList<>();


    public void setData(ArrayList<CatModel> favCatsToAdapt) {

        this.favCats = favCatsToAdapt;

    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {

        public TextView favCatName;

        public View view;

        public FavouriteViewHolder(View v) {
            super(v);
            favCatName = v.findViewById(R.id.favcat_name);
            view = v;

        }

    }
    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_catfav, parent, false);

        FavouriteAdapter.FavouriteViewHolder favouriteViewHolder= new FavouriteAdapter.FavouriteViewHolder(view);
        return favouriteViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final FavouriteViewHolder holder, final int position) {

        final CatModel currentCat = favCats.get(position);

        holder.favCatName.setText(currentCat.getName());


        //Allowing direct transition to favourite cat's details page by clicking
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent explicitIntent = new Intent(context, CatActivity.class);
                explicitIntent.putExtra("id", currentCat.getId());
                context.startActivity(explicitIntent);
            }
        });

    }

//Return number of favourite cats
    @Override
    public int getItemCount() {
        return favCats.size();
    }

}