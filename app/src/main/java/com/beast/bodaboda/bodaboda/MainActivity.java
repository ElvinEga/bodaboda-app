package com.beast.bodaboda.bodaboda;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.beast.bodaboda.bodaboda.component.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.arnaudguyon.perm.Perm;
import fr.arnaudguyon.perm.PermResult;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener,
        LocationListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab_myPosition)
    FloatingActionButton fabMyPosition;
    @BindView(R.id.fab2_addLocation)
    FloatingActionButton fab2AddLocation;
    @BindView(R.id.fab_view)
    FloatingActionButton fabView;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.edit_placeName)
    AutoCompleteTextView editPlaceName;
    @BindView(R.id.btn_menu)
    ImageView btnMenu;
    private GoogleMap mMap;
    private Perm permFineLocation;
    private Perm permCoarseLocation;
    private static final int PERMISSION_FINE_REQUEST = 1;
    private static final int PERMISSION_COARSE_REQUEST = 2;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        permFineLocation = new Perm(this, Manifest.permission.ACCESS_FINE_LOCATION);
        permCoarseLocation = new Perm(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        sessionManager = new SessionManager(this);
        sessionManager = new SessionManager(this);

        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setUpNavigationView();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                appBar.setExpanded(true);
            }
        });

        setupPermissions();

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
                //  return true;
            }
        });
        params.setBehavior(behavior);
     /*   editPlaceName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng latLng = new LatLng(Double.parseDouble(providerList.get(position).getLatitude()),Double.parseDouble(providerList.get(position).getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(50));

            }
        });*/
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

        if (mMap != null) {

            //  setUpMap(latitude,longitude);
            // mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            //  mMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Add a marker in Sydney and move the camera
            LatLng kenya = new LatLng(0.0236, 37.9062);
            //    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(kenya));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

            //setupPlaces();
            checkGPS();
            mMap.setOnMarkerClickListener(this);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
           /* Toast.makeText(getApplicationContext(),
                    "permissions not gotten", Toast.LENGTH_SHORT)
                    .show();*/


            return;
        } else {
            buildGoogleApiClient();
            //  mMap.setMyLocationEnabled(true);
            // mMap.setMyLocationEnabled(true);

        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  setUpMapIfNeeded();
    }

    /* @Override
     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
         if (permissions.length == 1 &&
                 permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                 grantResults[0] == PackageManager.PERMISSION_GRANTED) {

             if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                     == PackageManager.PERMISSION_GRANTED) {
               //  mMap.setMyLocationEnabled(true);
                 Toast.makeText(getApplicationContext(),
                         "permissions gotten", Toast.LENGTH_SHORT)
                         .show();
             } else {
                 // Show rationale and request permission.
             }

         }
     }*/
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            Toast.makeText(getApplicationContext(),
                    "Creating Map", Toast.LENGTH_SHORT)
                    .show();
            // mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                //  setUpMap(latitude,longitude);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                mMap.getUiSettings().setRotateGesturesEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
    }

    private void setupPermissions() {
        if (permFineLocation.isGranted()) {

        } else if (permFineLocation.isDenied()) {

            permFineLocation.askPermission(PERMISSION_FINE_REQUEST);
        } else {
            permFineLocation.askPermission(PERMISSION_FINE_REQUEST);

        }
        if (permCoarseLocation.isGranted()) {

        } else if (permCoarseLocation.isDenied()) {

            permCoarseLocation.askPermission(PERMISSION_COARSE_REQUEST);
        } else {
            permCoarseLocation.askPermission(PERMISSION_COARSE_REQUEST);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_FINE_REQUEST) {
            PermResult finePermissionResult = new PermResult(permissions, grantResults);
            if (finePermissionResult.isGranted()) {

            } else if (finePermissionResult.isDenied()) {

            } else {

            }
        }
        if (requestCode == PERMISSION_COARSE_REQUEST) {
            PermResult finePermissionResult = new PermResult(permissions, grantResults);
            if (finePermissionResult.isGranted()) {

            } else if (finePermissionResult.isDenied()) {

            } else {

            }
        }
    }

    @OnClick({R.id.fab_myPosition, R.id.fab2_addLocation, R.id.fab_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_myPosition:
                if (checkGPS()) {
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(50));
                }
                break;
            case R.id.fab2_addLocation:
                if (checkGPS()) {
                 /*   Intent intent = new Intent(getApplicationContext(), AddPlaceActivity.class);
                    intent.putExtra("longitude", mLastLocation.getLongitude());
                    intent.putExtra("latitude", mLastLocation.getLatitude());
                    intent.putExtra("address", getAddress(mLastLocation.getLongitude(), mLastLocation.getLatitude()));
                    startActivity(intent);*/
                }
                break;
            case R.id.fab_view:
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position: " + getAddress(mLastLocation.getLongitude(), mLastLocation.getLatitude()));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                sessionManager.logoutUser();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean checkGPS() {
        LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(MainActivity.this.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Location Disabled");  // GPS not found
            builder.setMessage("Please Enable your gps in order to access location"); // Want to enable?
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton("NO", null);
            builder.create().show();
            return false;
        } else {
            return true;
        }
    }


    private String getAddress(double longitude, double latitude) {
        try {

            List<Address> addresses;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            //    stateName= addresses.get(0).getAdminArea();
            //   cityName = addresses.get(0).getLocality();
            //   streetName = addresses.get(0).getFeatureName();
            return addresses.get(0).getFeatureName() + "," + addresses.get(0).getLocality();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                                                         // This method will trigger on item Click of navigation menu
                                                         @Override
                                                         public boolean onNavigationItemSelected(MenuItem menuItem) {

                                                             //Check to see which item was being clicked and perform appropriate action
                                                             switch (menuItem.getItemId()) {
                                                                 //Replacing the main content with ContentFragment Which is our Inbox View;
                                                                 case R.id.nav_map:

                                                                     break;
                                                                 case R.id.nav_settings:

                                                                     break;
                                                                 case R.id.nav_privacy_policy:

                                                                     break;
                                                             }

                                                             //Checking if the item is in checked state or not, if not make it in checked state
                                                             if (menuItem.isChecked()) {
                                                                 menuItem.setChecked(false);
                                                             } else {
                                                                 menuItem.setChecked(true);
                                                             }
                                                             menuItem.setChecked(true);


                                                             return true;
                                                         }
                                                     }
        );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getSnippet() != null) {
            // setupDetails(Integer.parseInt(marker.getSnippet()));
        }
        return false;
    }

    public void collapseToolbar() {
        appBar.setExpanded(false);
    }

    @Override
    public void onBackPressed() {
        //Include the code here
        appBar.setExpanded(true);
        return;
    }

    @OnClick(R.id.fab_view)
    public void onClick() {
        collapseToolbar();
    }


    @OnClick(R.id.btn_menu)
    public void onViewClicked() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }
}
