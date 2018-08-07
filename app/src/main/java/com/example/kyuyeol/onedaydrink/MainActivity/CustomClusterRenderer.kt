package com.example.kyuyeol.onedaydrink.MainActivity

import android.content.Context
import com.example.kyuyeol.onedaydrink.MainActivity.MapData.ClusterNode
import com.example.kyuyeol.onedaydrink.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CustomClusterRenderer : DefaultClusterRenderer<ClusterNode> {

    constructor(context : Context, map: GoogleMap, clusterManager: ClusterManager<ClusterNode>) : super(context,map,clusterManager)

    override fun onBeforeClusterItemRendered(item: ClusterNode?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions?.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
    }

}