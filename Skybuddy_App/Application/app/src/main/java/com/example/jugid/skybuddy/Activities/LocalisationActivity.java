package com.example.jugid.skybuddy.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Localisation;
import com.example.jugid.skybuddy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocalisationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private List<Marker> liste_marker = new ArrayList<>();
    private List<Localisation> liste_localisation = new ArrayList<>();

    private String mVol;
    private Switch switchActivate;

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    FusedLocationProviderClient mFusedClientProvider;
    LocationCallback mLocationCallback;

    private int REQUEST_FINE_LOCATION = 2;
    private VolleyCallback callback = new VolleyCallback() {
        @Override
        public void onSuccess(String response) {
            if(!response.equals("0")){
                VolleyCallback callback_interne = new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        mMap.clear();
                        liste_localisation = Jason.getInstance().jsonToListLocalisation(response);
                        for(Localisation loc : liste_localisation){
                            if(!loc.getIduser().equals(Thibaut.user.getId())){
                                //Les autres utilisateurs
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng( Float.parseFloat(loc.getPosx()),Float.parseFloat(loc.getPosy())))
                                        .title(loc.getNomuser())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).showInfoWindow();
                            }
                            else{
                                //L'utilisateur courant
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng( Float.parseFloat(loc.getPosx()),Float.parseFloat(loc.getPosy())))
                                        .title(loc.getNomuser())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).showInfoWindow();

                                //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng( Float.parseFloat(loc.getPosx()),Float.parseFloat(loc.getPosy()))));
                            }
                        }
                    }
                };
                Chloe.getAllLocalisations(getApplicationContext(),mVol,callback_interne);
            }
            else{
                Toast.makeText(LocalisationActivity.this, "Une erreur est survenue et le serveur n'a pas pu retourner de données.", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVol = getIntent().getStringExtra("ID_VOL");
        setContentView(R.layout.activity_localisation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        switchActivate = findViewById(R.id.SCH_activate_localisation);
        switchActivate.setChecked(false);

        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // do work here
                onLocationChanged(locationResult.getLastLocation());
            }
        };
    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        mFusedClientProvider = getFusedLocationProviderClient(this);
        try{
            mFusedClientProvider.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
        catch(SecurityException e){
            Log.d("LOCALISATION startLocalisationUpdates",e.getMessage().toString());

        }

    }

    protected void stopLocationUpdates(){
        if(mFusedClientProvider != null){
            mFusedClientProvider.removeLocationUpdates(mLocationCallback);
        }
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Chloe.localiser(getApplicationContext(),mVol, String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),callback);
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        try{
            FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

            locationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // GPS location can be null if GPS is switched off
                            if (location != null) {
                                onLocationChanged(location);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MapDemoActivity", "Error trying to get last GPS location");
                            e.printStackTrace();
                        }
                    });
        }
        catch(SecurityException e){
            Log.d("LOCALISATION getLastLocation",e.getMessage().toString());
        }

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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(1);

        if(checkPermissions()) {
            switchActivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mMap.setMyLocationEnabled(true);
                        startLocationUpdates();
                    } else {
                        mMap.setMyLocationEnabled(false);
                        stopLocationUpdates();
                    }
                }
            });
        }
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_FINE_LOCATION);
    }

    @Override
    public void onDestroy() {
        stopLocationUpdates();
        super.onDestroy();
    }
}
