package com.nene.chicken.Presentation.Activity;

/**
 * Created by YURIM on 2017-09-16.
 */

public class SearchResultInfo {

    public SearchResultInfo(String title, String roadAddress, int mapx, int mapy) {
        this.title = title;
        this.roadAddress = roadAddress;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public int getMapx() {
        return mapx;
    }

    public int getMapy() {
        return mapy;
    }

    private String roadAddress;
    private int mapx;
    private int mapy;


}
