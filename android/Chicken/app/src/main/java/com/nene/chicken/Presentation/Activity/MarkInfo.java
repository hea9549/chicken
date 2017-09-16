package com.nene.chicken.Presentation.Activity;

import com.nene.chicken.Model.TransPosition;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.maplib.NMapConverter;
import com.nhn.android.maps.overlay.NMapPathData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YURIM on 2017-09-16.
 */

public class MarkInfo implements Serializable{
    private String pathJson;
    private int mapx;
    private int mapy;
    public int inclineType;
    public int distance;
    public final static int INCLINE_DESCENT = 0;
    public final static int INCLINE_ASCENT = 1;
    public final static int INCLINE_FLAT = 2;
    public final static int INCLINE_HARD_DESCENT= 3;
    public final static int INCLINE_HARD_ASCENT = 4;

    public MarkInfo(int mapx, int mapy, String pathJson) {
        this.mapx = mapx;
        this.mapy = mapy;
        this.pathJson = pathJson;
    }

    public int getMapx() {
        return mapx;
    }

    public double getLat(){
        return NMapConverter.utmK2Grs(mapx,mapy).getLatitude();
    }

    public double getLng(){
        return NMapConverter.utmK2Grs(mapx,mapy).getLongitude();
    }

    public int getMapy() {
        return mapy;
    }

    public String getPathJson() {
        return pathJson;
    }

    public void setPathJson(String pathJson) {
        this.pathJson = pathJson;
    }

    public List<TransPosition> getPathPositions(){
        ArrayList<TransPosition> lists = new ArrayList<>();
        String pathJson = this.getPathJson();
        String[] coorJson = pathJson.split(" ");
        if (pathJson.isEmpty()) return null;
        if (coorJson[0].isEmpty()) return null;
        for (int j = 0; j < coorJson.length; j++) {
            NGeoPoint np = NMapConverter.utmK2Grs(Integer.valueOf(coorJson[j].split(",")[0]), Integer.valueOf(coorJson[j].split(",")[1]));
            lists.add(new TransPosition(np.getLatitude(),np.getLongitude()));
        }
        return lists;
    }
}
