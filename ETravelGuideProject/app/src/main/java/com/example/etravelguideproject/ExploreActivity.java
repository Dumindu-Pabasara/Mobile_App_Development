package com.example.etravelguideproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

// ✅ NO android.os.Build.VERSION_CODES.R

public class ExploreActivity extends AppCompatActivity implements OnMapReadyCallback {
    Place place;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_explore); // ✅ correct and now should compile

        String json = getIntent().getStringExtra("place");
        place = new Gson().fromJson(json, Place.class);

        TextView name = findViewById(R.id.exploreName);
        ImageView image = findViewById(R.id.exploreImage);
        name.setText(place.name);
        Glide.with(this).load(place.imageUrl).into(image);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        map = gMap;
        LatLng pos = new LatLng(place.latitude, place.longitude);
        map.addMarker(new MarkerOptions().position(pos).title(place.name));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 12));
    }
}
