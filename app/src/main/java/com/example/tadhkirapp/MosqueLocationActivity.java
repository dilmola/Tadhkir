package com.example.tadhkirapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MosqueLocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    boolean isPermissionGranted;
    private GoogleMap mMap;
    FloatingActionButton fab;
    private FusedLocationProviderClient mLocationClient;
    private ImageView imageView;
    ImageButton previousbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_location);

        fab = findViewById(R.id.fab);
        checkMyPermission();
        initMap();

        mLocationClient = new FusedLocationProviderClient(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrLoc();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);


        previousbtn = findViewById(R.id.back_button);

        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    private void initMap() {
        if (isPermissionGranted) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
            supportMapFragment.getMapAsync(this);
        }
    }

    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(MosqueLocationActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("Package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint("MissingPermission")
    private void getCurrLoc() {
        mLocationClient.getLastLocation().addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                Location location = task.getResult();
                gotoLocation(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng LatLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng, 18);
        mMap.moveCamera(cameraUpdate);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        try{

            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle
                    ));
            if (!success) {
                Log.e("MosqueLocationActivity_2", "Style parsing failed");

            }
        } catch (Resources.NotFoundException e) {
            Log.e("MosqueLocationActivity_2", "Cant find style error: ", e);
        }
        // Add a marker in Sydney and move the camera

        LatLng AbuUbaidah = new LatLng(4.637484363609298, 101.15062501485919);
        mMap.addMarker(new MarkerOptions().position(AbuUbaidah).title("Masjid Abu Ubaidah"));

        LatLng HajjahPutehMahami= new LatLng(4.634003492867411, 101.14686616689227);
        mMap.addMarker(new MarkerOptions().position(HajjahPutehMahami).title("Surau Hajjah Puteh Mahami"));

        LatLng SurauAlHuda= new LatLng(4.633911029156919, 101.14889553557542);
        mMap.addMarker(new MarkerOptions().position(SurauAlHuda).title("Surau Al Huda / Surau Sekolah Rendah Agama Rakyat Al Huda"));

        LatLng AlJamaiyah= new LatLng(4.63326751413675, 101.14725699686315);
        mMap.addMarker(new MarkerOptions().position(AlJamaiyah).title("Surau Al-Jamaiyah"));

        LatLng SurauEhsaniah = new LatLng(3.9295795360013486, 100.77724224383788);
        mMap.addMarker(new MarkerOptions().position(SurauEhsaniah).title("Surau Ehsaniah"));

        LatLng MasjidAlKhairi = new LatLng(3.931618337933994, 100.77031964129684);
        mMap.addMarker(new MarkerOptions().position(MasjidAlKhairi).title("Masjid Al-Khairi"));

        LatLng SurauDarulIstiqamah = new LatLng(1.6555116039899385, 110.33483562802888);
        mMap.addMarker(new MarkerOptions().position(SurauDarulIstiqamah).title("Surau Darul Istiqamah Kampung Rampangi Fasa 1"));

        LatLng DarusSalamMosqueKampun= new LatLng(1.6497784147100434, 110.33346222556608);
        mMap.addMarker(new MarkerOptions().position(DarusSalamMosqueKampun).title("Darus Salam Mosque Kampung Rampangi Phase 1"));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void previous(View view) {

        Intent intent = new Intent(MosqueLocationActivity.this, MainPageActivity.class);
        startActivity(intent);
    }
}