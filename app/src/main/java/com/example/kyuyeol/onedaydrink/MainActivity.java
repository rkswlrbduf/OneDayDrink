package com.example.kyuyeol.onedaydrink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapView;

public class MainActivity extends AppCompatActivity {

    FrameLayout view;
    ConstraintLayout constraintLayout;
    View bottomBar;
    ConstraintSet constraintSet1;
    ConstraintSet constraintSet2;

    boolean bottomState;

    RecyclerView recyclerView1;

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
        constraintSet2.clone(this, R.layout.activity_main2);

        bottomState = false;
        bottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "TAGGGGGG");
                TransitionManager.beginDelayedTransition(constraintLayout);
                if(bottomState) constraintSet1.applyTo(constraintLayout); else constraintSet2.applyTo(constraintLayout);
                bottomState = !bottomState;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(new RecyclerViewAdapter(this));

    }

    @Override
    public void onBackPressed() {
        if(!bottomState) super.onBackPressed();
        else {
            TransitionManager.beginDelayedTransition(constraintLayout);
            constraintSet1.applyTo(constraintLayout);
            bottomState = !bottomState;
        }
    }
}
