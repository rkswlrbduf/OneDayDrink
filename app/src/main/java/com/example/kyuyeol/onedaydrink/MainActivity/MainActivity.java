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
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.kyuyeol.onedaydrink.BookMarkActivity.BookMarkActivity;
import com.example.kyuyeol.onedaydrink.ContactActivity.ContactActivity;
import com.example.kyuyeol.onedaydrink.EventActivity.EventActivity;
import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.MainActivityAdapter;
import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.StoreTypeRecyclerViewAdapter;
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.ClusterNode;
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.NodeData;
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.ServerConnectService;
import com.example.kyuyeol.onedaydrink.R;
import com.example.kyuyeol.onedaydrink.SearchActivity.SearchActivity;
import com.example.kyuyeol.onedaydrink.SettingActivity.SettingActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.GridBasedAlgorithm;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    @BindView(R.id.main_menu)
    ImageView mainMenu;
    @BindView(R.id.main_search)
    TextView mainSearchText;
    @BindView(R.id.main_search_container_cardview)
    CardView mainSearch;
    @BindView(R.id.activity_main_listview)
    RecyclerView recyclerView;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    private double mLatitude;
    private double mLongitude;

    private LocationRequest locationRequest;
    private LocationManager locationManager;
    private LocationSettingsRequest.Builder locationSettingsRequest;
    private PendingResult<LocationSettingsResult> pendingResult;

    private Intent intent;

    private CustomClusterManager mClusterManager;

    /**
     * Google AdMob
     */
    private InterstitialAd mInterstitialAd;

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

        mClusterManager = new CustomClusterManager<>(getApplicationContext(), mMap);

        mMap.setOnCameraIdleListener(mClusterManager); //카메라 이동완료시 리스너(줌인/아웃 포함)
        mMap.setOnMarkerClickListener(mClusterManager); // 클릭시 위치로 카메라 움직임(수정 가능 할듯)

        CustomClusterRenderer defaultClusterRenderer = new CustomClusterRenderer(getApplicationContext(), mMap, mClusterManager);
        defaultClusterRenderer.setMinClusterSize(2); // 최소 3개 이상 부터 클러스터링함

        GridBasedAlgorithm gridBasedAlgorithm = new GridBasedAlgorithm(); //구역을 나눠서 클러스터링
        NonHierarchicalDistanceBasedAlgorithm nonHierarchicalDistanceBasedAlgorithm = new NonHierarchicalDistanceBasedAlgorithm(); //거리 기반 클러스터링

        mClusterManager.setRenderer(defaultClusterRenderer);
        mClusterManager.setAlgorithm(nonHierarchicalDistanceBasedAlgorithm);
        mClusterManager.setAnimation(false);

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
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
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

        MobileAds.initialize(this, "ca-app-pub-4893372814309338~7615626175");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new MainActivityAdapter(this));

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

/*

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });

        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
*/

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
        slidingRootNav.getLayout().findViewById(R.id.darwer_text3).setOnClickListener(this);
        slidingRootNav.getLayout().findViewById(R.id.darwer_text4).setOnClickListener(this);
        slidingRootNav.getLayout().findViewById(R.id.darwer_text5).setOnClickListener(this);

/*
        TMapView tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey("df15431c-c688-49f4-b53a-6e5f56f0ed90");
        view.addView(tmapview);*/

/*        constraintSet1 = new ConstraintSet();
        constraintSet1.clone(constraintLayout);
        constraintSet2 = new ConstraintSet();
        constraintSet2.clone(this, R.layout.activity_around_store);

        transition = new AutoTransition();
        transition.setDuration(400);*/

        bottomState = false;

        /*recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(new StoreTypeRecyclerViewAdapter(this, list));
        recyclerView1.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        backButton.setOnClickListener(this);
        filterButton.setOnClickListener(this);*/
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
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MainActivity.this, (View) mainSearch, "main_search_transition");
                    startActivity(intent, options.toBundle());
                } else {
                    intent = new Intent(MainActivity.this, SearchActivity.class);
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

                        /*intent = new Intent(MainActivity.this, SignActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*/

                    }
                });
                break;
            case R.id.darwer_text2:
                intent = new Intent(MainActivity.this, BookMarkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.darwer_text3:
                intent = new Intent(MainActivity.this, EventActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.darwer_text4:
                intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.darwer_text5:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }

    private class CustomClusterManager<ClusterNode extends ClusterItem> extends ClusterManager<ClusterNode> {
        CameraPosition mPreviousCameraPosition;
        List<NodeData.Data> result;

        public double lng_u, lng_d, lat_l, lat_r;

        public CustomClusterManager(Context context, GoogleMap map) {
            super(context, map);
        }

        public void requestServer(ServerConnectService service) {
            Call<NodeData> request = service.getNodeDataFromServer("mapTest.php", lat_l, lat_r, lng_d, lng_u);
            request.enqueue(new Callback<NodeData>() {
                @Override
                public void onResponse(Call<NodeData> call, Response<NodeData> response) {
                    NodeData data = response.body();
                    result = data.result;
                    for (int i = 0; i < result.size(); i++) { // 데이터 받아온 만큼 마커표시(클러스터링)
                        double lat = result.get(i).lat;
                        double lng = result.get(i).lng;
                        mClusterManager.addItem(new com.example.kyuyeol.onedaydrink.MainActivity.MapData.ClusterNode(new LatLng(lat, lng), "Node" + i));
                    }
                    CustomClusterManager.super.cluster();
                }

                @Override
                public void onFailure(Call<NodeData> call, Throwable t) {
                }
            });
        }

        private OkHttpClient createOkHttpClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
            return builder.build();
        }

        @Override
        public void onCameraIdle() {

            lng_u = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.longitude; // 북동 경도
            lat_r = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude; // 북동 위도
            lng_d = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.longitude; // 남서 경도
            lat_l = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.latitude; // 남서 위도

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://stou2.cafe24.com/test/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createOkHttpClient())//http 로그 찍어주는 함수
                    .build();
            ServerConnectService service = retrofit.create(ServerConnectService.class);

            if (super.getRenderer() instanceof GoogleMap.OnCameraIdleListener) {
                ((GoogleMap.OnCameraIdleListener) super.getRenderer()).onCameraIdle();
            }

            CameraPosition position = mMap.getCameraPosition();
            if (this.mPreviousCameraPosition == null) { // 지도 실행했을때
                this.mPreviousCameraPosition = mMap.getCameraPosition();
                requestServer(service);
            } else if (this.mPreviousCameraPosition.zoom != position.zoom || this.mPreviousCameraPosition.target != position.target) {// 카메라 줌이 바뀌거나 움직였을때
                this.mPreviousCameraPosition = mMap.getCameraPosition();
                mClusterManager.clearItems();//이전 아이템 비움
                requestServer(service);
            }
        }
    }

    private class CustomClusterRenderer extends DefaultClusterRenderer<ClusterNode> {

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<ClusterNode> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(ClusterNode item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        }
    }

}
