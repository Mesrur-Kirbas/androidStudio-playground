package com.example.javamaps.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.javamaps.R;
import com.example.javamaps.model.Place;
import com.example.javamaps.room.db.PlaceDao;
import com.example.javamaps.room.db.PlaceDatabase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.javamaps.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ActivityResultLauncher<String> permissionLauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    SharedPreferences sharedPreferences;
    PlaceDatabase db;
    PlaceDao placeDao;
    boolean info;
    Double selectedLatitude;
    Double getSelectedLongitude;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


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

        sharedPreferences = this.getSharedPreferences("com.example.javamaps",MODE_PRIVATE);
        info = false;
        db = Room.databaseBuilder(getApplicationContext(),PlaceDatabase.class,"Places").build();
        //dao ve databe işlemleri main thread da yapılması önerilmez dolayısıyla uygulama çalışırken şu aşamada save butonun basınca
        //uygulamamız çöker bu yüzden yukarıdaki kodumuzu .allowMainThreadQueries.build() ile değiştirirsek uygulamamız çalışır
        //ANCAK bu amatör bir yöntemdir ve önerilmez.!!!
        placeDao = db.placeDao();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        binding.saveButton.setEnabled(false);

        // BU BOLUMDE CASTING ISLEMI YAPIYORUZ
         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);  // SiSTEMIN KONUM SERVISLERINE ERISMNI SAGLAR
         locationListener = new LocationListener() {        // LocationManagerdan konumiun degistiginin bilgisini alabilmek icin kullandigimiz bir arayuzdur.
            @Override
            public void onLocationChanged(@NonNull Location location) {
                //System.out.println("location:" + location.toString());

                info =  sharedPreferences.getBoolean("info",false);

                if (!info){    // !info means info == false
                    LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
                    sharedPreferences.edit().putBoolean("info",true).apply();
                }
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

            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);              //Son Bilinen konum alinir.
            if (lastLocation != null){
                LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
            }

            mMap.setMyLocationEnabled(true);
        }

        //LatLng eiffel = new LatLng(48.8559713,2.2930037);                // LatLng enlem ve boylam tutar
        //mMap.addMarker(new MarkerOptions().position(eiffel).title("Eiffel Tower"));     // marker ekleme
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel,15));          // acilista kamera zoomlama
    }

    private void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if (o) {
                    //permission granted
                    if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);              //Son Bilinen konum alinir.
                        if (lastLocation != null) {
                            LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15));                // daha onceden kayitli bir konum var ise konum izni alinir alinmaz oncelikle mapsde o konumu gosterir sonrasinda mevcut konumu alarak o konuma ayarlama yapilir.
                        }


                    } else {
                        //permission denied
                        Toast.makeText(MapsActivity.this, "Permisson needed!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));

        selectedLatitude = latLng.latitude;
        getSelectedLongitude = latLng.longitude;

        binding.saveButton.setEnabled(true);
    }

    public void save(View view){

        Place place = new Place(binding.placeNameText.getText().toString(),selectedLatitude,getSelectedLongitude);

        //threading --> Main (UI), Default (CPU Intensive), IO (Network, database)

        //placeDao.insert(place).subscribeOn(Schedulers.io()).subscribe(); //bu hangi threadde yapilacagini soyler ONERILMEZ !!!
        //Bunun yerine disposable onerilir

        // #disposable#
        compositeDisposable.add(placeDao.insert(place)
                        .subscribeOn(Schedulers.io())           //hangi threadin kullanilacagini soyler
                                .observeOn(AndroidSchedulers.mainThread())   //sonucun Hangi threadde gosterilecegini soyler
                                        .subscribe(MapsActivity.this::handleResponse)
                        );

        placeDao.insert(place);

    }

    private void handleResponse() {
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void delete(View view){
        /*
        compositeDisposable.add(placeDao.delete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MapsActivity.this::handleResponse)
        );  */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();  //Daha once yapilan butun callar cope atiliyor dolayisiyla hafizada yer tutmuyor
    }
}
