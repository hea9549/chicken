package com.nene.chicken.Presentation.Activity;

import java.io.Serializable;

/**
 * Created by YURIM on 2017-09-16.
 */

public class MarkInfo implements Serializable{
    private String pathJson;
    private int mapx;
    private int mapy;
    private int 
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
