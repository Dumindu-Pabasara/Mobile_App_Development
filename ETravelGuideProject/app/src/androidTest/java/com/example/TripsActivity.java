package com.example.etravelguideproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TripsActivity extends AppCompatActivity implements PlaceClick {

    private RecyclerView rv;
    private PlaceListAdapter adapter;
    private final List<Place> list = new ArrayList<>();
    private DatabaseReference db;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_trips);

        rv = findViewById(R.id.tripsRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlaceListAdapter(list, this, this); // implements click
        rv.setAdapter(adapter);

        db = FirebaseDatabase.getInstance().getReference("places");
        db.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot s) {
                list.clear();
                for (DataSnapshot sn : s.getChildren()) list.add(sn.getValue(Place.class));
                adapter.notifyDataSetChanged();
            }
            @Override public void onCancelled(@NonNull DatabaseError e) {}
        });
    }

    /* 🔗 click -> ExploreActivity */
    @Override public void onPlaceClick(Place p) {
        Intent i = new Intent(this, ExploreActivity.class);
        i.putExtra("place", new Gson().toJson(p));
        startActivity(i);
    }
}
