package com.example.etravelguideproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Home screen with:
 *  • Search (name + location)
 *  • Category buttons (Lakes/Sea/Mountain/Forest/All)
 *  • 2‑column grid RecyclerView
 *  • BottomNavigation to Explore / Chat / Profile
 */
public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final List<Place> placeList = new ArrayList<>();
    private PlaceAdapter adapter;
    private DatabaseReference dbRef;

    private SearchView searchView;
    private Button btnLakes, btnSea, btnMountain, btnForest, btnAll;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* -------- UI refs -------- */
        recyclerView = findViewById(R.id.recyclerTopTrips);
        searchView   = findViewById(R.id.searchBar);

        btnLakes    = findViewById(R.id.btnLakes);
        btnSea      = findViewById(R.id.btnSea);
        btnMountain = findViewById(R.id.btnMountain);
        btnForest   = findViewById(R.id.btnForest);
        btnAll      = findViewById(R.id.btnAll);

        /* -------- RecyclerView -------- */
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        adapter = new PlaceAdapter(placeList, this);
        recyclerView.setAdapter(adapter);

        /* -------- Firebase load -------- */
        dbRef = FirebaseDatabase.getInstance().getReference("places");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snap) {
                placeList.clear();
                for (DataSnapshot s : snap.getChildren()) {
                    placeList.add(s.getValue(Place.class));
                }
                adapter.updateList(new ArrayList<>(placeList)); // full list first
            }
            @Override public void onCancelled(@NonNull DatabaseError e) {}
        });

        /* -------- Search filter -------- */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String q) { return false; }
            @Override public boolean onQueryTextChange(String q) {
                filterByText(q);
                return true;
            }
        });

        /* -------- Category buttons -------- */
        btnLakes.setOnClickListener(v -> filterByCategory("Lakes"));
        btnSea.setOnClickListener(v -> filterByCategory("Sea"));
        btnMountain.setOnClickListener(v -> filterByCategory("Mountain"));
        btnForest.setOnClickListener(v -> filterByCategory("Forest"));
        btnAll.setOnClickListener(v -> adapter.updateList(new ArrayList<>(placeList))); // reset

        /* -------- Bottom‑Navigation -------- */
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {                             // already here
                return true;
            } else if (id == R.id.nav_trips) {                     // Explore list page
                startActivity(new Intent(this, TripsActivity.class));
            } else if (id == R.id.nav_chat) {                  // Chat / Help
                startActivity(new Intent(this, ChatActivity.class));
            } else if (id == R.id.nav_profile) {                   // User profile
                startActivity(new Intent(this, TripsActivity.class));
            }else if (id == R.id.nav_wishlist) {                   // User profile
                startActivity(new Intent(this, WishlistActivity.class));
            }
            overridePendingTransition(0,0); // no animation
            return true;
        });
        bottomNav.setSelectedItemId(R.id.nav_home); // highlight home
    }

    /* ---------- Filters ---------- */

    private void filterByText(String txt) {
        String q = txt == null ? "" : txt.trim().toLowerCase();
        if (q.isEmpty()) { adapter.updateList(new ArrayList<>(placeList)); return; }

        List<Place> filtered = new ArrayList<>();
        for (Place p : placeList) {
            String n = p.name     == null ? "" : p.name.toLowerCase();
            String l = p.location == null ? "" : p.location.toLowerCase();
            if (n.contains(q) || l.contains(q)) filtered.add(p);
        }
        adapter.updateList(filtered);
    }

    private void filterByCategory(String cat) {
        List<Place> filtered = new ArrayList<>();
        for (Place p : placeList) {
            if (p.category != null && p.category.equalsIgnoreCase(cat)) filtered.add(p);
        }
        adapter.updateList(filtered);
    }
}
