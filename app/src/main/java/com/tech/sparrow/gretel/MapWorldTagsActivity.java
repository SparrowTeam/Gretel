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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tech.sparrow.gretel.API.models.response.MarkDetailedInfo;
import com.tech.sparrow.gretel.API.models.response.MarkInfo;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import java.util.List;

public class MapWorldTagsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private UserInfo info;
    private List<MarkDetailedInfo> marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        info = (UserInfo) getIntent().getSerializableExtra("info");
        marks = (List< MarkDetailedInfo>) getIntent().getSerializableExtra("marks");
    }

    private void drawMark(MarkerOptions marker, CircleOptions circle, LatLng coord)
    {
        mMap.addCircle(circle.center(coord));
        mMap.addMarker(marker.position(coord));
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

        MarkerOptions marker = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_searching))
                .anchor((float) 0.50, (float) 0.50);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (MarkDetailedInfo mark : marks) {
            CircleOptions circle = new CircleOptions()
                    .fillColor(Color.parseColor(mark.getTeam().getColor()))
                    .strokeWidth(0);

            LatLng position = new LatLng(
                    Double.parseDouble(mark.getCoordinates().getLatitude()),
                    Double.parseDouble(mark.getCoordinates().getLongtitude())
            );

            drawMark(marker, circle.radius(mark.getValue()), position);
            builder.include(position);
        }

        if (!marks.isEmpty()) {
            LatLngBounds bounds = builder.build();
            int padding = 0; // offset from edges of the map in pixels

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 600, 600, padding));
        }
    }
}
