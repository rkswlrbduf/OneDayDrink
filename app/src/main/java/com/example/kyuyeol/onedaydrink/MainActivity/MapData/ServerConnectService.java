package com.example.kyuyeol.onedaydrink.MainActivity.MapData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerConnectService {
//    @GET("{user}")
//    Call<NodeData> getGet(@Path("user") String userName);

    @GET("{user}")
    Call<NodeData> getNodeDataFromServer(
            @Path("user") String userName,
            @Query("lat_l") double lat_l, @Query("lat_r") double lat_r,
            @Query("lng_d") double lng_d, @Query("lng_u") double lng_u
    );
}
