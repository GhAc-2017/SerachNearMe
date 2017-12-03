package com.example.actech.digitecsoln;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class picloc extends AppCompatActivity implements OnMapReadyCallback {

    LatLng latLng=new LatLng(22.674208, 88.379913);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picloc);

        MapFragment mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        googleMap.addMarker(new MarkerOptions().position(latLng));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
