package com.example.kyuyeol.onedaydrink.MainActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.kyuyeol.onedaydrink.BookMarkActivity.BookMarkActivity;
import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.StoreTypeRecyclerViewAdapter;
import com.example.kyuyeol.onedaydrink.R;
import com.example.kyuyeol.onedaydrink.SearchActivity.SearchActivity;
import com.example.kyuyeol.onedaydrink.SignActivity.SignActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, View.OnClickListener {

    private static final String TAG = "MainActivity_Log";
    public static final int REQUEST_LOCATION = 001;

    /*String CLIENT_ID = "127279302599-ujilbih6vd4cqkclqqd2crp0iahusbpc.apps.googleusercontent.com";

    GoogleSignInClient mGoogleSignInAccount;
    GoogleSignInOptions gso;*/

    private ArrayList<String> list = new ArrayList<String>() {{
        add("고깃집");
        add("초밥집");
        add("술집");
        add("감자집");
        add("치즈집");
        add("치킨집");
    }};

    boolean bottomState;

    private ConstraintSet constraintSet1;
    private ConstraintSet constraintSet2;
    private AutoTransition transition;
    private MaterialMenuDrawable materialMenu;
    SlidingRootNav slidingRootNav;

    @BindView(R.id.constraint)
    ConstraintLayout constraintLayout;
    @BindView(R.id.bottom_bar)
    View bottomBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView1;
    @BindView(R.id.main_menu)
    ImageView mainMenu;
    @BindView(R.id.around_store_back)
    ImageButton backButton;
    @BindView(R.id.around_store_filter)
    ImageButton filterButton;
    @BindView(R.id.main_search)
    TextView mainSearchText;
    @BindView(R.id.main_search_container_cardview)
    CardView mainSearch;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    private double mLatitude;
    private double mLongitude;

    private LocationRequest locationRequest;
    private LocationManager locationManager;
    private LocationSettingsRequest.Builder locationSettingsRequest;
    private PendingResult<LocationSettingsResult> pendingResult;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locationManager.removeUpdates(locationListener);

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(MainActivity.this);

            ImageView locationButton = (ImageView) mapFragment.getView().findViewById(2);
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.setMargins(0, 0, 30, 400);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("gps", "onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng position = new LatLng(mLatitude, mLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

    public void requestMyLocation() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestMyLocation();
            } else {
                Toast.makeText(this, "권한없음", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Toast.makeText(this, "Gps enabled", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this, "Gps Canceled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void mEnableGps() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        mLocationSetting();
    }

    public void mLocationSetting() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1 * 1000);
        locationRequest.setFastestInterval(1 * 1000);
        locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        mResult();
    }

    public void mResult() {
        pendingResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingsRequest.build());
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mEnableGps();
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                requestMyLocation();
            } else {
                requestMyLocation();
            }
        } else {
            requestMyLocation();
        }

        materialMenu = new MaterialMenuDrawable(this, Color.GRAY, MaterialMenuDrawable.Stroke.THIN);

        mainMenu.setImageDrawable(materialMenu);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .addDragListener(new DragListener() {
                    @Override
                    public void onDrag(float progress) {
                        materialMenu.setTransformationOffset(
                                MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                                progress
                        );
                    }
                })
                .withMenuLayout(R.layout.activity_main_drawer)
                .withSavedState(savedInstanceState)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withDragDistance(140)
                .withRootViewScale(0.8f)
                .withRootViewElevation(3)
                .withRootViewYTranslation(3)
                .inject();

        slidingRootNav.getLayout().findViewById(R.id.darwer_text1).setOnClickListener(this);
        slidingRootNav.getLayout().findViewById(R.id.darwer_text2).setOnClickListener(this);
/*
        TMapView tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey("df15431c-c688-49f4-b53a-6e5f56f0ed90");
        view.addView(tmapview);*/

        constraintSet1 = new ConstraintSet();
        constraintSet1.clone(constraintLayout);
        constraintSet2 = new ConstraintSet();
        constraintSet2.clone(this, R.layout.activity_around_store);

        transition = new AutoTransition();
        transition.setDuration(400);

        bottomState = false;
        bottomBar.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(new StoreTypeRecyclerViewAdapter(this, list));

        backButton.setOnClickListener(this);
        filterButton.setOnClickListener(this);
        mainSearchText.setOnClickListener(this);
        mainMenu.setOnClickListener(this);
    }

/*    public void googleSignOut() {
        mGoogleSignInAccount.signOut().addOnCompleteListener(MainActivity.this, null);
    }*/

    @Override
    public void onBackPressed() {
        if (!bottomState) super.onBackPressed();
        else {
            TransitionManager.beginDelayedTransition(constraintLayout, transition);
            constraintSet1.applyTo(constraintLayout);
            bottomState = !bottomState;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu:
                if (!slidingRootNav.isMenuOpened()) {
                    slidingRootNav.openMenu();
                } else {
                    slidingRootNav.closeMenu();
                }
                break;
            case R.id.main_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MainActivity.this, (View) mainSearch, "main_search_transition");
                    startActivity(intent, options.toBundle());
                } else {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.around_store_filter:
                break;
            case R.id.around_store_back:
                onBackPressed();
                break;
            case R.id.bottom_bar:
                TransitionManager.beginDelayedTransition(constraintLayout, transition);
                if (bottomState) constraintSet1.applyTo(constraintLayout);
                else constraintSet2.applyTo(constraintLayout);
                bottomState = !bottomState;
                break;
            case R.id.darwer_text1:
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        //FirebaseAuth.getInstance().signOut();
                        //googleSignOut(); //google
                        //LoginManager.getInstance().logOut();
                        Intent intent = new Intent(MainActivity.this, SignActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
                break;
            case R.id.darwer_text2:
                Intent intent = new Intent(MainActivity.this, BookMarkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }
}
