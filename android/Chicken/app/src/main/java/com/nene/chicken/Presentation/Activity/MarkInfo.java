package com.nene.chicken.Presentation.Activity;

import java.io.Serializable;

/**
 * Created by YURIM on 2017-09-16.
 */

public class MarkInfo implements Serializable{
    public MarkInfo(int mapx, int mapy) {
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public int getMapx() {
        return mapx;
    }

    public int getMapy() {
        return mapy;
    }

    private int mapx;
    private int mapy;
}
