package com.example.a2101658224_ba26_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.a2101658224_ba26_project.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Configuration.getInstance().load(this,
                PreferenceManager.getDefaultSharedPreferences(this));
        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        IMapController controller = mapView.getController();
        controller.setZoom(19);
        GeoPoint iGeoPoint = new GeoPoint(-6.20201, 106.78113);
        controller.setCenter(iGeoPoint);

        CompassOverlay compassOverlay = new CompassOverlay(this,
                new InternalCompassOrientationProvider(this), mapView);
        mapView.getOverlays().add(compassOverlay);

        Marker marker = new Marker(mapView);
        marker.setTitle("XiaoWu");
        marker.setPosition(iGeoPoint);
        mapView.getOverlays().add(marker);

        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.INTERNET);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        requestMapPermission(permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ArrayList<String> notAllowedPermission = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                notAllowedPermission.add(permissions[i]);
            }
        }

        if (notAllowedPermission.size() > 0) {
            ActivityCompat.requestPermissions(this, notAllowedPermission.toArray(new String[0]), 1);
        }
    }

    void requestMapPermission(ArrayList<String> permissions) {
        ArrayList<String> notAllowedPermission = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            if (ContextCompat.checkSelfPermission(this, permissions.get(i))
                    != PackageManager.PERMISSION_GRANTED) {
                notAllowedPermission.add(permissions.get(i));
            }
        }

        if (notAllowedPermission.size() > 0) {
            ActivityCompat.requestPermissions(this, notAllowedPermission.toArray(new String[0]), 1);
        }
    }
}