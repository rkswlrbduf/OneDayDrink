package com.example.kyuyeol.onedaydrink.MainActivity.MapData;

import java.util.List;

public class NodeData {
    public List<Data> result;
    public class Data{
        public String name;
        public double lat,lng;
        public String image;
        public int beer;
        public int soju;
        public int sansachun;
    }
}
