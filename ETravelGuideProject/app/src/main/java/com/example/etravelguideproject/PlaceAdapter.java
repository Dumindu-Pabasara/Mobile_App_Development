package com.example.etravelguideproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    List<Place> placeList;
    Context context;

    // Temporary storage (use Firebase or Room in full apps)
    public static List<Place> wishlist = new ArrayList<>();

    public PlaceAdapter(List<Place> list, Context ctx) {
        placeList = list;
        context = ctx;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Place> list) {
        placeList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int i) {
        Place p = placeList.get(i);
        h.name.setText(p.name);
        h.loc.setText(p.location);
        Glide.with(context).load(p.imageUrl).into(h.img);

        // Wishlist state
        if (wishlist.contains(p)) {
            h.heart.setImageResource(R.drawable.heart_icon);
        } else {
            h.heart.setImageResource(R.drawable.heart_outline);
        }

        h.heart.setOnClickListener(v -> {
            if (wishlist.contains(p)) {
                wishlist.remove(p);
                h.heart.setImageResource(R.drawable.heart_outline);
            } else {
                wishlist.add(p);
                h.heart.setImageResource(R.drawable.heart_icon);
            }
        });

        h.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ExploreActivity.class);
            intent.putExtra("place", new Gson().toJson(p));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return placeList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, loc;
        ImageView img, heart;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtTripTitle);
            loc = v.findViewById(R.id.txtTripLocation);
            img = v.findViewById(R.id.imgTrip);
            heart = v.findViewById(R.id.imgHeart); // new
        }
    }
}


