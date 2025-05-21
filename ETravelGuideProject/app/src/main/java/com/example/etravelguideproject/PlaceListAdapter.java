package com.example.etravelguideproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.Holder> {

    private final List<Place> data;
    private final Context ctx;
    private final PlaceClick callback;

    public PlaceListAdapter(List<Place> list, Context c, PlaceClick cb){
        data = list; ctx = c; callback = cb;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_place_row, parent, false);
        return new Holder(view);
    }

    @Override public void onBindViewHolder(@NonNull Holder h,int i){
        Place pl = data.get(i);
        h.name.setText(pl.name);
        h.loc.setText(pl.location);
        Glide.with(ctx).load(pl.imageUrl).into(h.img);

        h.itemView.setOnClickListener(v -> callback.onPlaceClick(pl));
    }
    @Override public int getItemCount(){ return data.size(); }

    static class Holder extends RecyclerView.ViewHolder{
        TextView name, loc; ImageView img;
        Holder(View v){ super(v);
            name = v.findViewById(R.id.rowName);
            loc  = v.findViewById(R.id.rowLoc);
            img  = v.findViewById(R.id.rowImg);
        }
    }
}

