package com.example.javamaps;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.javamaps.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    ActivityResultLauncher<String> permissionLauncher;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        registerLauncher();
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

        // BU BOLUMDE CASTING ISLEMI YAPIYORUZ
         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);  // SiSTEMIN KONUM SERVISLERINE ERISMNI SAGLAR
         locationListener = new LocationListener() {        // LocationManagerdan konumiun degistiginin bilgisini alabilmek icin kullandigimiz bir arayuzdur.
            @Override
            public void onLocationChanged(@NonNull Location location) {
                System.out.println("location:" + location.toString());
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Snackbar.make(binding.getRoot(),"Permission needed",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //request permission
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }).show();
            }else {
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

            }
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
        




        LatLng eiffel = new LatLng(48.8559713,2.2930037);                // LatLng enlem ve boylam tutar
        mMap.addMarker(new MarkerOptions().position(eiffel).title("Eiffel Tower"));     // marker ekleme
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel,15));          // acilista kamera zoomlama
    }

    private void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if (o) {
                    //permission granted
                    if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

                    }



                } else {
                    //permission denied
                    Toast.makeText(MapsActivity.this, "Permisson needed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
