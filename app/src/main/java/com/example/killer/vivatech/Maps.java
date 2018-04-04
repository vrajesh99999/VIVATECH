package com.example.killer.vivatech;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends FragmentActivity implements OnMapReadyCallback {
    private  GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
             map=googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng viva = new LatLng(19.474019, 72.858260);
        Point p=new Point(19,72);
        map.moveCamera(CameraUpdateFactory.zoomBy(5f));
        map.addMarker(new MarkerOptions().position(viva).title("viva"));
       map.moveCamera(CameraUpdateFactory.newLatLng(viva));


        Circle circle = map.addCircle(new CircleOptions().center(new LatLng(19.474019, 72.858260)).radius(9000).strokeColor(Color.RED));
        circle.setVisible(true);
        getZoomLevel(circle);
    }int zoomLevel;
    public int getZoomLevel(Circle circle) {

        if (circle != null){
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel =(int) (30 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }
}
