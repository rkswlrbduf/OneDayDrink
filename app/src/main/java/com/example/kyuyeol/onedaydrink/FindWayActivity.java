package com.example.kyuyeol.onedaydrink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.skt.Tmap.TMapView;

public class FindWayActivity extends AppCompatActivity {

    FrameLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_way);

        view = (FrameLayout)findViewById(R.id.Tmap);

        TMapView tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey("df15431c-c688-49f4-b53a-6e5f56f0ed90");
        view.addView(tmapview);

    }

}
