# Go Sport

Go Sport is an Java based application developed in android framework that locates the the people in a particular area in Google Maps who are interested in the same sport as the user.Some other features in the app includes:

  - Schedule local matches
  - Invite other people for an event
  - Chat

### Installation

The application can be run using the apk on any device running android 4.3 and above
For contibuting to the development or to view the source code one has to install Android Studio. The installation is as follows

  - Download Android Studio from https://developer.android.com/studio/index.html
  - Launch the .exe file you downloaded.
  - Follow the setup wizard to install Android Studio and any necessary SDK tools.
  - This application can be run in Emulator or on an actual device running Android 4.3 or above
  - Network access is required for the application to work.

### Usage

```
public class MapsActivity extends FragmentActivity 
implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment)
								getSupportFragmentManager()
                                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
```

### Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

License
----

**Free Software, Hell Yeah!**
