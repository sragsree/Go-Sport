package com.example.keng.sdsu_code24hrs;

/**
 * Created by Sreerag on 11/18/2016.
 */
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



public class MyLocationDemoActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {


    private DatabaseReference mDatabase, root;


    String token = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private static final LatLng Soccer = new LatLng(32.774, -117.08);
    private Marker mSoccer;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_info);

        mDatabase = FirebaseDatabase.getInstance().getReference();





        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);


        mSoccer = mMap.addMarker(new MarkerOptions()
                .position(Soccer)
                .snippet("Soccer")

                .title("Tony"));
        mSoccer.setTag(0);

        mMap.setOnInfoWindowClickListener(this);

       enableMyLocation();}



    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).


         Double longi, lati;
        longi = mMap.getMyLocation().getLongitude();
        lati = mMap.getMyLocation().getLatitude();



        System.out.println("Heyyyy " +  mMap.getMyLocation().getLongitude());
        System.out.println("Heyyyy " +  mMap.getMyLocation().getLatitude());

        mDatabase.child("users").child(token).child("Location").child("Longitude").setValue(longi);
        mDatabase.child("users").child(token).child("Location").child("Latitude").setValue(lati);




        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String name;
        String myname;


        Toast.makeText(this, "Request been send!",
                Toast.LENGTH_SHORT).show();

       myname= mDatabase.child("users").child(token).child("username").getKey();


        name = mSoccer.getTitle();
        System.out.println(name);
        mDatabase.child("users").child("Tony").child("Request").child("name").setValue(myname);

        requst();

    }




    private void requst() {
        mDatabase.child("users").child(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("username").exists()){
                    final String myName = dataSnapshot.child("username").getValue().toString();
                    final String myPhone = dataSnapshot.child("Phone number").getValue().toString();

                    System.out.println("WHAT IS THISSSS!!! BROOO " + myName);
                    mDatabase.child("users").child(mSoccer.getTitle()).child("Request").child("name").setValue(myName);
                    mDatabase.child("users").child(mSoccer.getTitle()).child("Request").child("name").child(myName).setValue(myPhone);

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


















}