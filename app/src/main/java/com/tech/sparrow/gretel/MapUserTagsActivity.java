package com.tech.sparrow.gretel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tech.sparrow.gretel.API.APIError;
import com.tech.sparrow.gretel.API.ErrorUtils;
import com.tech.sparrow.gretel.API.models.response.MarkInfo;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MapUserTagsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private UserInfo info;
    private List<MarkInfo> marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        info = (UserInfo) getIntent().getSerializableExtra("info");
        marks = (List<MarkInfo>) getIntent().getSerializableExtra("marks");
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

        CircleOptions circle_user = new CircleOptions()
                .radius(100)
                .fillColor(Color.parseColor(info.getTeam().getColor()))
                .strokeWidth(0);

        for (MarkInfo mark: marks) {
            LatLng position = new LatLng(
                    Double.parseDouble(mark.getCoordinates().getLatitude()),
                    Double.parseDouble(mark.getCoordinates().getLongtitude())
            );

            drawMark(marker, circle_user, position);
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(, 15));
    }
}
