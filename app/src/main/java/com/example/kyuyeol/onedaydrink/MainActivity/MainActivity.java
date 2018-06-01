package com.example.kyuyeol.onedaydrink.MainActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.kyuyeol.onedaydrink.FirebaseMessaging.mFirebaseInstanceIDService;
import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.StoreTypeRecyclerViewAdapter;
import com.example.kyuyeol.onedaydrink.R;
import com.skt.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragListener;
//import com.tsengvn.typekit.TypekitContextWrapper;

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
    MaterialMenuDrawable  materialMenu;
    ImageButton backButton;
    ImageButton filterButton;

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
        backButton = (ImageButton)findViewById(R.id.around_store_back);
        filterButton = (ImageButton)findViewById(R.id.around_store_filter);

        materialMenu = new MaterialMenuDrawable(this, Color.GRAY, MaterialMenuDrawable.Stroke.THIN);
        mainMenu.setImageDrawable(materialMenu);

        (new mFirebaseInstanceIDService()).onTokenRefresh();

        final SlidingRootNav slidingRootNav = new SlidingRootNavBuilder(this)
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