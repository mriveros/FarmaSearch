package com.coloredmoon.com.chicajimenezemilio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dao.FarmaciaDAO;
import modelo.Farmacia;

import utilidades.MiToolBar;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final Context context = this;
    double lat = Double.MAX_VALUE;
    double longi=Double.MAX_VALUE;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main);
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getDouble("lat");
        longi = bundle.getDouble("long");
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
        List<Farmacia> farmacias = FarmaciaDAO.getInstance(this).getFarmacias();
        for (int i = 0; i < farmacias.size(); ++i) {
            LatLng posicion = new LatLng(farmacias.get(i).getLatitud(), farmacias.get(i).getLongitud());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(posicion);
            markerOptions.title(farmacias.get(i).getNombre() + "_" + farmacias.get(i).getId());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.farmacialogo));

            mMap.addMarker(markerOptions);

        }
        LatLng posicion;
        // Add a marker in Sydney and move the camera
        if(lat!=Double.MAX_VALUE && longi != Double.MAX_VALUE) {
             posicion = new LatLng(lat, longi);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(posicion);
            markerOptions.title("MI POSICION");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 14));
        }else
        {
            posicion = new LatLng(37.1736883, -3.5932346);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 14));
            Toast.makeText(this,"No hemos podido localizarle, active el GPS",Toast.LENGTH_LONG).show();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mMap.setMyLocationEnabled(true);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(!marker.getTitle().equals("MI POSICION")) {
                    long id = Long.parseLong(marker.getTitle().split("_")[1]);
                    Intent farmacia = new Intent(context, CargaFarmacia.class);
                    farmacia.putExtra("idFarmacia", id);
                    ((Activity) context).startActivity(farmacia);
                }
                return false;
            }
        });
    }
}
