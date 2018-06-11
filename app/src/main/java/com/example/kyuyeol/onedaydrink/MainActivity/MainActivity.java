package com.example.kyuyeol.onedaydrink.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.kyuyeol.onedaydrink.FirebaseMessaging.mFirebaseInstanceIDService;
import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.StoreTypeRecyclerViewAdapter;
import com.example.kyuyeol.onedaydrink.R;
import com.example.kyuyeol.onedaydrink.SearchActivity.SearchActivity;
import com.example.kyuyeol.onedaydrink.SignActivity.SignActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.skt.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FrameLayout view;
    ConstraintLayout constraintLayout;
    View bottomBar;
    ConstraintSet constraintSet1;
    ConstraintSet constraintSet2;

    boolean bottomState;

    RecyclerView recyclerView1;
    AutoTransition transition;

    ArrayList<String> list = new ArrayList<String>() {{
        add("고깃집");
        add("초밥집");
        add("술집");
        add("감자집");
        add("치즈집");
        add("치킨집");
    }};

    ImageView mainMenu;
    MaterialMenuDrawable materialMenu;
    ImageButton backButton;
    ImageButton filterButton;

    String CLIENT_ID = "127279302599-ujilbih6vd4cqkclqqd2crp0iahusbpc.apps.googleusercontent.com";

    GoogleSignInClient mGoogleSignInAccount;
    GoogleSignInOptions gso;
    TextView mainSearchText;
    CardView mainSearch;

    SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (FrameLayout) findViewById(R.id.Tmap);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint);
        bottomBar = (View) findViewById(R.id.bottom_bar);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview);

        mainMenu = (ImageView) findViewById(R.id.main_menu);
        backButton = (ImageButton) findViewById(R.id.around_store_back);
        filterButton = (ImageButton) findViewById(R.id.around_store_filter);

        mainSearchText = (TextView) findViewById(R.id.main_search);
        mainSearch = (CardView) findViewById(R.id.main_search_container_cardview);

        materialMenu = new MaterialMenuDrawable(this, Color.GRAY, MaterialMenuDrawable.Stroke.THIN);
        mainMenu.setImageDrawable(materialMenu);

        (new mFirebaseInstanceIDService()).onTokenRefresh();

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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CLIENT_ID)
                .requestEmail()
                .build();
        mGoogleSignInAccount = GoogleSignIn.getClient(MainActivity.this, gso);

        slidingRootNav.getLayout().findViewById(R.id.darwer_text4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        FirebaseAuth.getInstance().signOut();
                        googleSignOut(); //google
                        LoginManager.getInstance().logOut();
                        Intent intent = new Intent(MainActivity.this, SignActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
            }
        });

        TMapView tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey("df15431c-c688-49f4-b53a-6e5f56f0ed90");
        view.addView(tmapview);

        constraintSet1 = new ConstraintSet();
        constraintSet1.clone(constraintLayout);
        constraintSet2 = new ConstraintSet();
        constraintSet2.clone(this, R.layout.activity_around_store);

        transition = new AutoTransition();
        transition.setDuration(400);

        bottomState = false;
        bottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(constraintLayout, transition);
                if (bottomState) constraintSet1.applyTo(constraintLayout);
                else constraintSet2.applyTo(constraintLayout);
                bottomState = !bottomState;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(new StoreTypeRecyclerViewAdapter(this, list));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mainSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MainActivity.this, (View) mainSearch, "main_search_transition");
                    startActivity(intent, options.toBundle());
                } else {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!slidingRootNav.isMenuOpened()) {
                    slidingRootNav.openMenu();
                } else {
                    slidingRootNav.closeMenu();
                }
            }
        });
    }

    public void googleSignOut() {
        mGoogleSignInAccount.signOut().addOnCompleteListener(MainActivity.this, null);
    }

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

}