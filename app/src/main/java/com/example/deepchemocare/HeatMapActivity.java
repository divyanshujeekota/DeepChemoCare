package com.example.deepchemocare;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private HeatmapTileProvider heatmapTileProvider;
    private TileOverlay tileOverlay;
    private ArrayList<WeightedLatLng> heatMapData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_heat_map);

        heatMapData = new ArrayList<>();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(HeatMapActivity.this);







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;
        myMap.setMyLocationEnabled(true);
        myMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        // Fetch UV data and plot heat map
        fetchUVdata();

        // Get User's Current Location
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 4));
            } else {
                Toast.makeText(HeatMapActivity.this, "Location not available.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void fetchUVdata() {
        // Sample LatLng data for various locations (replace with real API data)
        List<LatLng> locations = Arrays.asList(
                new LatLng(28.6139, 77.2090),  // New Delhi
                new LatLng(19.0760, 72.8777),  // Mumbai
                new LatLng(13.0827, 80.2707),  // Chennai
                new LatLng(12.9716, 77.5946),  // Bangalore
                new LatLng(22.5726, 88.3639)   // Kolkata
        );

        // UV index values (hypothetical, replace with actual API values)
        int[] uvValues = {4, 7, 8, 10, 5};

        for (int i = 0; i < locations.size(); i++) {
            double weight = Math.min(uvValues[i] / 10.0, 1.0);  // Normalize UV index
            WeightedLatLng weightedLatLng = new WeightedLatLng(locations.get(i), weight);
            heatMapData.add(weightedLatLng);
        }

        addHeatMap();
    }


    private void addHeatMap() {
        if (heatmapTileProvider == null) {
            heatmapTileProvider = new HeatmapTileProvider.Builder()
                    .weightedData(heatMapData)
                    .radius(50) // Radius of the heat spots
                    .build();

            tileOverlay = myMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider));
        } else {
            heatmapTileProvider.setWeightedData(heatMapData);
            tileOverlay.clearTileCache();
        }
    }


}