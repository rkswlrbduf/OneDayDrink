package com.example.kyuyeol.onedaydrink.MainActivity;

import android.content.Context;
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

import com.example.kyuyeol.onedaydrink.MainActivity.Adapter.StoreTypeRecyclerViewAdapter;
import com.example.kyuyeol.onedaydrink.R;
import com.skt.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;
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

    ArrayList<String> list = new ArrayList<String>(){{
        add("고깃집");
        add("초밥집");
        add("술집");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (FrameLayout)findViewById(R.id.Tmap);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraint);
        bottomBar = (View)findViewById(R.id.bottom_bar);
        recyclerView1 = (RecyclerView)findViewById(R.id.recyclerview);

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
                if(bottomState) constraintSet1.applyTo(constraintLayout); else constraintSet2.applyTo(constraintLayout);
                bottomState = !bottomState;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(new StoreTypeRecyclerViewAdapter(this, list));

    }

    @Override
    public void onBackPressed() {
        if(!bottomState) super.onBackPressed();
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