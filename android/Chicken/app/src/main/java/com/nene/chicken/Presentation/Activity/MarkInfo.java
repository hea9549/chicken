package com.nene.chicken.Presentation.Activity;

import java.io.Serializable;

/**
 * Created by YURIM on 2017-09-16.
 */

public class MarkInfo implements Serializable{
    private String pathJson;
    private int mapx;
    private int mapy;
    public int inclineType;
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

    public int getMapy() {
        return mapy;
    }

    public String getPathJson() {
        return pathJson;
    }

    public void setPathJson(String pathJson) {
        this.pathJson = pathJson;
    }
}
