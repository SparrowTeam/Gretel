package com.tech.sparrow.gretel;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapWorldTagsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng park = new LatLng(60.188199, 24.832441);
        LatLng dipoli = new LatLng(60.185242, 24.831872);
        LatLng otakari = new LatLng(60.186033, 24.827361);

        int team_color1 = 0x70FF0000;
        int team_color2 = 0x70000FFF;

        CircleOptions circle_team1 = new CircleOptions()
                .radius(100)
                .fillColor(team_color1)
                .strokeWidth(0);

        CircleOptions circle_team2 = new CircleOptions()
                .radius(100)
                .fillColor(team_color2)
                .strokeWidth(0);

        MarkerOptions marker_team1 = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_searching))
                .anchor((float) 0.50, (float) 0.50);

        MarkerOptions marker_team2 = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_searching))
                .anchor((float) 0.50, (float) 0.50);

        mMap.addCircle(circle_team1.center(park));
        mMap.addMarker(marker_team1.position(park));

        mMap.addCircle(circle_team1.center(dipoli));
        mMap.addMarker(marker_team1.position(dipoli));

        mMap.addCircle(circle_team2.center(otakari));
        mMap.addMarker(marker_team2.position(otakari));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(park, 15));
    }
}
